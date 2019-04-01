package ru.tolymhlv.testrest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tolymhlv.testrest.domains.Visit;
import ru.tolymhlv.testrest.repos.VisitRepo;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VisitService {

    private final VisitRepo visitRepo;
    private final TimeUtils timeUtils;

    @Autowired
    public VisitService(final VisitRepo visitRepo, final TimeUtils timeUtils) {
        this.visitRepo = visitRepo;
        this.timeUtils = timeUtils;
    }

    public Visit addVisit(final String userId, final String pageId) {
        final Visit visit = new Visit(userId, pageId, timeUtils.now());
        return visitRepo.save(visit);
    }

    public List<Visit> getVisitsBetweenDates(final LocalDateTime from, final LocalDateTime to) {
        return visitRepo.findByDateBetween(from, to);
    }

    public List<Visit> getVisitsFromStartDay() {
        final LocalDateTime now = timeUtils.now();
        final LocalDateTime startOfDay = timeUtils.startOfDate(now);
        return getVisitsBetweenDates(startOfDay, now);
    }
}
