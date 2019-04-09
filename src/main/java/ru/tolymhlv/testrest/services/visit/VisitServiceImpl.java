package ru.tolymhlv.testrest.services.visit;

import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.tolymhlv.testrest.domains.Visit;
import ru.tolymhlv.testrest.repos.VisitRepo;
import ru.tolymhlv.testrest.services.visit.requests.GetStatisticsParams;
import ru.tolymhlv.testrest.services.visit.requests.VisitCreateRequest;
import ru.tolymhlv.testrest.services.visit.responses.FullVisitStatistics;
import ru.tolymhlv.testrest.services.visit.responses.VisitStatistics;
import ru.tolymhlv.testrest.utils.DateAndTimeUtils;

import javax.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VisitServiceImpl implements VisitService {

    private final VisitRepo visitRepo;
    private final DateAndTimeUtils dateAndTimeUtils;

    @NotBlank
    @Value("#{new Integer('${ru.tolymhlv.testrest.quantityOfVisitedPagesToBeingRegularUser}')}")
    private Integer quantityOfVisitedPagesToBeingRegularUser;

    @Autowired
    public VisitServiceImpl(@NonNull final VisitRepo visitRepo, @NonNull final DateAndTimeUtils dateAndTimeUtils) {
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
    public FullVisitStatistics getStatistics(@NonNull final GetStatisticsParams request) throws IllegalArgumentException {

        final List<Visit> visitsByDate = getVisitsBetweenDates(request.getFrom(), request.getTo());

        final int quantityVisitsByDate = visitsByDate.size();
        final long quantityUniqUsersByDate = countUniqUsersByDate(visitsByDate);
        final long quantityUniqRegularUsersByDate = countUniqRegularUsersByDate(visitsByDate);

        return new FullVisitStatistics(quantityVisitsByDate, quantityUniqUsersByDate, quantityUniqRegularUsersByDate);
    }


    private void addVisit(@NonNull final VisitCreateRequest request) throws IllegalArgumentException {
        if (visitRequestIsNotValidate(request)) {
            throw new IllegalArgumentException("Wrong format of userId");
        }
        final Visit visit = new Visit(request.getUserId(), request.getPageId(), dateAndTimeUtils.now());
        visitRepo.save(visit);
    }

    private List<Visit> getVisitsBetweenDates(@NonNull final LocalDateTime from, @NonNull final LocalDateTime to) {
        return visitRepo.findAllByDateBetween(from, to);
    }

    private boolean visitRequestIsNotValidate(@NonNull VisitCreateRequest request) {
        final String userId = request.getUserId();
        final String pageId = request.getPageId();
        if (!StringUtils.isAlphanumeric(userId) || userId.length() > 255) {
            return true;
        }
        if (!StringUtils.isAlphanumeric(pageId) || pageId.length() > 255) {
            return true;
        }
        return false;

    }

    private long countUniqUsersByDate(@NonNull List<Visit> visits) {
        return visits.parallelStream()
                .map(Visit::getUserId)
                .distinct()
                .count();
    }

    private long countUniqRegularUsersByDate(@NonNull List<Visit> visits) {
        return visits.parallelStream()
                .collect(Collectors.groupingByConcurrent(Visit::getUserId))
                .entrySet()
                .parallelStream()
                .map(Map.Entry::getValue)
                .map(values -> values.stream().map(Visit::getPageId).distinct().collect(Collectors.toList()))
                .filter(values -> values.size() >= quantityOfVisitedPagesToBeingRegularUser)
                .count();
    }
}
