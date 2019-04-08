package ru.tolymhlv.testrest.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.tolymhlv.testrest.domains.Visit;
import ru.tolymhlv.testrest.repos.JpaVisitRepo;
import ru.tolymhlv.testrest.services.requests.GetStatisticsRequest;
import ru.tolymhlv.testrest.services.requests.VisitCreateRequest;
import ru.tolymhlv.testrest.services.responses.FullVisitStatistics;
import ru.tolymhlv.testrest.services.responses.VisitStatistics;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VisitServiceImpl implements VisitService {

    private final JpaVisitRepo visitRepo;
    private final DateAndTimeUtils dateAndTimeUtils;

    @NotBlank
    @Value("#{new Integer('${ru.tolymhlv.testrest.quantityOfVisitedPagesToBeingRegularUser}')}")
    private Integer quantityOfVisitedPagesToBeingRegularUser;

    @Autowired
    public VisitServiceImpl(@NonNull final JpaVisitRepo visitRepo, @NonNull final DateAndTimeUtils dateAndTimeUtils) {
        this.visitRepo = visitRepo;
        this.dateAndTimeUtils = dateAndTimeUtils;
    }

    @Override
    public VisitStatistics create(@NonNull final VisitCreateRequest request) throws IllegalArgumentException {

        addVisit(request);

        final LocalDateTime now = dateAndTimeUtils.now();
        final LocalDateTime startOfDay = dateAndTimeUtils.startOfDay(now);
        final List<Visit> visitsToday = getVisitsBetweenDates(startOfDay, now);

        final int quantityVisitsToday = visitsToday.size();
        final long quantityUniqUsersToday = countUniqUsersByDate(visitsToday);

        return new VisitStatistics(quantityVisitsToday, quantityUniqUsersToday);
    }

    @Override
    public FullVisitStatistics getStatistics(@NonNull final GetStatisticsRequest request) throws IllegalArgumentException {

        final TimeInterval interval = getDatesFromGetStatisticsRequest(request);
        final List<Visit> visitsByDate = getVisitsBetweenDates(interval.getFrom(), interval.getTo());

        final int quantityVisitsByDate = visitsByDate.size();
        final long quantityUniqUsersByDate = countUniqUsersByDate(visitsByDate);
        final long quantityUniqRegularUsersByDate = countUniqRegularUsersByDate(visitsByDate);

        return new FullVisitStatistics(quantityVisitsByDate, quantityUniqUsersByDate, quantityUniqRegularUsersByDate);
    }


    private void addVisit(@NonNull final VisitCreateRequest request) throws IllegalArgumentException{
        if (visitRequestIsNotValidate(request)) {
            throw new IllegalArgumentException("Wrong format of userId");
        }
        final Visit visit = new Visit(request.getUserId(), request.getPageId(), dateAndTimeUtils.now());
        visitRepo.save(visit);
    }

    private List<Visit> getVisitsBetweenDates(@NonNull final LocalDateTime from, @NonNull final LocalDateTime to) {
        return visitRepo.findAllByDateBetween(from, to);
    }

    private boolean visitRequestIsNotValidate(@NotNull VisitCreateRequest request){
        final String userId = request.getUserId();
        final String pageId = request.getPageId();
        if (!StringUtils.isAlphanumeric(userId) || userId.length() > 255 ) {
            return true;
        }
        if (!StringUtils.isAlphanumeric(pageId) || pageId.length() > 255) {
            return true;
        }
        return false;

    }

    private TimeInterval getDatesFromGetStatisticsRequest(@NotNull GetStatisticsRequest request) throws IllegalArgumentException {
        LocalDateTime fromAsTime;
        LocalDateTime toAsTime;
        try {
            fromAsTime = dateAndTimeUtils.stringToTime(request.getFrom());
            toAsTime = dateAndTimeUtils.stringToTime(request.getTo());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid dates format, use yyyy-MM-dd-HH-mm-ss");
        }
        if (fromAsTime.isAfter(toAsTime)) {
            throw new IllegalArgumentException("Invalid dates order");
        }
        return new TimeInterval(fromAsTime, toAsTime);
    }

    private long countUniqUsersByDate(@NotNull List<Visit> visits) {
        return visits.parallelStream()
                .map(Visit::getUserId)
                .distinct()
                .count();
    }

    private long countUniqRegularUsersByDate(@NotNull List<Visit> visits) {
        return visits.parallelStream()
                .collect(Collectors.groupingByConcurrent(Visit::getUserId))
                .entrySet()
                .parallelStream()
                .map(Map.Entry::getValue)
                .map(values -> values.stream().map(Visit::getPageId).distinct().collect(Collectors.toList()))
                .filter(values -> values.size() >= quantityOfVisitedPagesToBeingRegularUser)
                .count();
    }

    @Data
    @AllArgsConstructor
    private class TimeInterval {
        private LocalDateTime from;
        private LocalDateTime to;
    }

}
