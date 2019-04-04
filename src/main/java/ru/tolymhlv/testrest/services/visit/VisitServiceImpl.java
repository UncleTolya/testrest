package ru.tolymhlv.testrest.services.visit;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.tolymhlv.testrest.domains.Visit;
import ru.tolymhlv.testrest.repos.VisitRepo;
import ru.tolymhlv.testrest.services.DateAndTimeUtils;
import ru.tolymhlv.testrest.services.visit.requests.GetStatisticsRequest;
import ru.tolymhlv.testrest.services.visit.requests.VisitCreateRequest;
import ru.tolymhlv.testrest.services.visit.responses.FullVisitStatistics;
import ru.tolymhlv.testrest.services.visit.responses.VisitStatistics;

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
    public VisitStatistics create(@NonNull final VisitCreateRequest request) {
        addVisit(request.getUserId(), request.getPageId());
        final List<Visit> visitsToday = getVisitsFromStartDay();
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

    private Visit addVisit(@NonNull final String userId, @NonNull final String pageId) {
        final Visit visit = new Visit(userId, pageId, dateAndTimeUtils.now());
        return visitRepo.save(visit);
    }

    private List<Visit> getVisitsBetweenDates(@NonNull final LocalDateTime from, @NonNull final LocalDateTime to) {
        return visitRepo.findAllByDateBetween(from, to);
    }

    private List<Visit> getVisitsFromStartDay() {
        final LocalDateTime now = dateAndTimeUtils.now();
        final LocalDateTime startOfDay = dateAndTimeUtils.startOfDay(now);
        return getVisitsBetweenDates(startOfDay, now);
    }


}
