package ru.tolymhlv.testrest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.tolymhlv.testrest.domains.Visit;
import ru.tolymhlv.testrest.services.VisitService;
import ru.tolymhlv.testrest.services.TimeUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MainController {

    private final VisitService visitService;
    private final TimeUtils timeUtils;

    @Autowired
    public MainController(VisitService visitService, TimeUtils timeUtils) {
        this.visitService = visitService;
        this.timeUtils = timeUtils;
    }

    @PostMapping("/visit")
    public String makeVisitEventAndGetCommonStatistic(
            final @RequestParam String userId,
            final @RequestParam String pageId,
            final Model model) {

        visitService.addVisit(userId, pageId);

        final List<Visit> visitsToday = visitService.getVisitsFromStartDay();
        final Integer counterVisitsToday = visitsToday.size();
        final Long counterUniqUsersToday = visitsToday
                .stream()
                .map(Visit::getUserId)
                .distinct()
                .count();

        return "main";
    }

    @GetMapping("/visits/{from}-{to}")
    public String getStatisticByDate(
            final @PathVariable String from,
            final @PathVariable String to,
            final Model model) {

        final LocalDateTime fromAsTime = timeUtils.stringToTime(from);
        final LocalDateTime toAsTime = timeUtils.stringToTime(to);

        final List<Visit> visitsByDate = visitService.getVisitsBetweenDates(fromAsTime, toAsTime);
        final Integer counterVisitsByDate = visitsByDate.size();
        final Long counterUniqUsersByDate = visitsByDate
                .parallelStream()
                .map(Visit::getUserId)
                .distinct()
                .count();
        final Long counterUniqRegularUsersByDate = visitsByDate
                .parallelStream()
                .collect(Collectors.groupingByConcurrent(Visit::getUserId))
                .entrySet()
                .parallelStream()
                .map(Map.Entry::getValue)
                .filter(visits -> visits.size() >= 10)
                .count();

        return "main";
    }


}
