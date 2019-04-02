package ru.tolymhlv.testrest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.tolymhlv.testrest.domains.Visit;
import ru.tolymhlv.testrest.services.VisitService;
import ru.tolymhlv.testrest.services.DateAndTimeUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MainController {

    private final VisitService visitService;
    private final DateAndTimeUtils dateAndTimeUtils;

    @Autowired
    public MainController(VisitService visitService, DateAndTimeUtils dateAndTimeUtils) {
        this.visitService = visitService;
        this.dateAndTimeUtils = dateAndTimeUtils;
    }

    @PostMapping(name = "/visit", produces = "application/json; charset=UTF-8")
    public String makeVisitEventAndGetCommonStatistic(
            final @RequestParam String userId,
            final @RequestParam String pageId,
            final Model model) {

        visitService.addVisit(userId, pageId);

        final List<Visit> visitsToday = visitService.getVisitsFromStartDay();
        final Integer counterVisitsToday = visitsToday.size();
        final Long counterUniqUsersToday = visitsToday
                .stream()
                .map(Visit::getUserId) // got the List<UserId>
                .distinct() // delete duplicate UserId from the list
                .count(); // only uniq UserId
        model.addAttribute("counterVisitsToday", counterVisitsToday);
        model.addAttribute("counterUniqUsersToday", counterUniqUsersToday);

        return "addVisitAndReturnStatisticJson";
    }

    @GetMapping(name = "/visits/{from}-{to}", produces = "application/json; charset=UTF-8")
    public String getStatisticByDate(
            final @PathVariable String from,
            final @PathVariable String to,
            final Model model) {

        final LocalDateTime fromAsTime = dateAndTimeUtils.stringToTime(from);
        final LocalDateTime toAsTime = dateAndTimeUtils.stringToTime(to);

        final List<Visit> visitsByDate = visitService.getVisitsBetweenDates(fromAsTime, toAsTime);
        final Integer counterVisitsByDate = visitsByDate.size();
        final Long counterUniqUsersByDate = visitsByDate
                .parallelStream()
                .map(Visit::getUserId)
                .distinct()
                .count();
        final Long counterUniqRegularUsersByDate = visitsByDate
                .parallelStream()
                .collect(Collectors.groupingByConcurrent(Visit::getUserId)) // got ConcurrentMap<UserId, List<Visit>>
                .entrySet()
                .parallelStream()
                .map(Map.Entry::getValue) //got List<List<Visit>>, each one belong to uniq user
                .filter(visits -> visits.size() >= 10) // filter visits by quantity condition from the task
                .count();
        model.addAttribute("counterVisitsByDate", counterVisitsByDate);
        model.addAttribute("counterUniqUsersByDate", counterUniqUsersByDate);
        model.addAttribute("counterUniqRegularUsersByDate", counterUniqRegularUsersByDate);

        return "returnStatisticByDateJson";
    }


}
