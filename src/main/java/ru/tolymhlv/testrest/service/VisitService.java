package ru.tolymhlv.testrest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tolymhlv.testrest.domain.User;
import ru.tolymhlv.testrest.domain.Visit;
import ru.tolymhlv.testrest.repo.VisitRepo;

import java.util.Date;

@Service
public class VisitService {

    private final VisitRepo visitRepo;

    @Autowired
    public VisitService(VisitRepo visitRepo) {
        this.visitRepo = visitRepo;
    }

    public Visit addVisit(final User user, final String fullUrl, final Date date) {
        final Visit visit = new Visit(user, fullUrl, date);
        return visitRepo.save(visit);
    }
}
