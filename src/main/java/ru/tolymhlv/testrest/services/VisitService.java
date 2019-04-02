package ru.tolymhlv.testrest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tolymhlv.testrest.domains.Visit;
import ru.tolymhlv.testrest.repos.VisitRepo;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VisitService {

    private final VisitRepo visitRepo;
    private final DateAndTimeUtils dateAndTimeUtils;

    @Autowired
    public VisitService(final VisitRepo visitRepo, final DateAndTimeUtils dateAndTimeUtils) {
        this.visitRepo = visitRepo;
        this.dateAndTimeUtils = dateAndTimeUtils;
    }

    public Visit addVisit(final String userId, final String pageId) {
        final Visit visit = new Visit(userId, pageId, dateAndTimeUtils.now());
        return visitRepo.save(visit);
    }

    public List<Visit> getVisitsBetweenDates(final LocalDateTime from, final LocalDateTime to) {
//        return visitRepo.findByDateBetween(Date.valueOf(from.toLocalDate()), Date.valueOf(to.toLocalDate()));
        return visitRepo.findAllByDateBetween(from, to);
    }

    public List<Visit> getVisitsFromStartDay() {
        final LocalDateTime now = dateAndTimeUtils.now();
        final LocalDateTime startOfDay = dateAndTimeUtils.startOfDate(now);
        return getVisitsBetweenDates(startOfDay, now);
    }
}
