package ru.tolymhlv.testrest.services;

import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.tolymhlv.testrest.domains.Visit;
import ru.tolymhlv.testrest.repos.JpaVisitRepo;
import ru.tolymhlv.testrest.services.requests.GetStatisticsRequest;
import ru.tolymhlv.testrest.services.requests.VisitCreateRequest;
import ru.tolymhlv.testrest.services.responses.FullVisitStatistics;
import ru.tolymhlv.testrest.services.responses.VisitStatistics;

import javax.validation.constraints.NotBlank;
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
        final int counterVisitsToday = visitsToday.size();
        final long counterUniqUsersToday = visitsToday
                .stream()
                .map(Visit::getUserId)
                .distinct()
                .count();

        return new VisitStatistics(counterVisitsToday, counterUniqUsersToday);
    }

    @Override
    public FullVisitStatistics getStatistics(@NonNull final GetStatisticsRequest request) {
        final List<Visit> visitsByDate = getVisitsBetweenDates(request.getFrom(), request.getTo());
        final int counterVisitsByDate = visitsByDate.size();
        final long counterUniqUsersByDate = visitsByDate
                .parallelStream()
                .map(Visit::getUserId)
                .distinct()
                .count();
        final long counterUniqRegularUsersByDate = visitsByDate
                .parallelStream()
                .collect(Collectors.groupingByConcurrent(Visit::getUserId))
                .entrySet()
                .parallelStream()
                .map(Map.Entry::getValue)
                .map(visits -> visits.stream().map(Visit::getPageId).distinct().collect(Collectors.toList()))
                .filter(visits -> visits.size() >= quantityOfVisitedPagesToBeingRegularUser)
                .count();

        return new FullVisitStatistics(counterVisitsByDate, counterUniqUsersByDate, counterUniqRegularUsersByDate);
    }

    private void addVisit(@NonNull final VisitCreateRequest request) throws IllegalArgumentException{
        final String userId = request.getUserId();
        final String pageId = request.getPageId();
        if (!StringUtils.isAlphanumeric(userId) || userId.length() > 255 ) {
            throw new IllegalArgumentException("Wrong format of userId");
        }
        if (!StringUtils.isAlphanumeric(pageId) || pageId.length() > 255) {
            throw new IllegalArgumentException("Wrong format of pageId");
        }

        final Visit visit = new Visit(userId, pageId, dateAndTimeUtils.now());
        visitRepo.save(visit);
    }

    private List<Visit> getVisitsBetweenDates(@NonNull final LocalDateTime from, @NonNull final LocalDateTime to) {
        return visitRepo.findAllByDateBetween(from, to);
    }

}
