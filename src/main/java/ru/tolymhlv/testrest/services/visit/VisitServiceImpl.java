package ru.tolymhlv.testrest.services.visit;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tolymhlv.testrest.domains.Visit;
import ru.tolymhlv.testrest.repos.VisitRepo;
import ru.tolymhlv.testrest.services.DateAndTimeUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VisitServiceImpl implements VisitService {

    private final VisitRepo visitRepo;
    private final DateAndTimeUtils dateAndTimeUtils;

    @Autowired
    public VisitServiceImpl(@NonNull final VisitRepo visitRepo, @NonNull final DateAndTimeUtils dateAndTimeUtils) {
        this.visitRepo = visitRepo;
        this.dateAndTimeUtils = dateAndTimeUtils;
    }

    public Visit addVisit(@NonNull final String userId, @NonNull final String pageId) {
        final Visit visit = new Visit(userId, pageId, dateAndTimeUtils.now());
        return visitRepo.save(visit);
    }

    public List<Visit> getVisitsBetweenDates(@NonNull final LocalDateTime from, @NonNull final LocalDateTime to) {
        return visitRepo.findAllByDateBetween(from, to);
    }

    public List<Visit> getVisitsFromStartDay() {
        final LocalDateTime now = dateAndTimeUtils.now();
        final LocalDateTime startOfDay = dateAndTimeUtils.startOfDay(now);
        return getVisitsBetweenDates(startOfDay, now);
    }
}
