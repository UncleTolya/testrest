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
public class VisitController {

    private final VisitService visitService;
    private final DateAndTimeUtils dateAndTimeUtils;

    @Autowired
    public VisitController(VisitService visitService, DateAndTimeUtils dateAndTimeUtils) {
        this.visitService = visitService;
        this.dateAndTimeUtils = dateAndTimeUtils;
    }

    @PostMapping(name = "/visit")
    public String makeVisitEventAndGetCommonStatistic(
            final @RequestParam(name = "userId") String userId,
            final @RequestParam(name = "pageId") String pageId,
            final Model model) {

        visitService.addVisit(userId, pageId);

        final List<Visit> visitsToday = visitService.getVisitsFromStartDay();
        final int counterVisitsToday = visitsToday.size();
        final long counterUniqUsersToday = visitsToday
                .stream()
                .map(Visit::getUserId) // got the List<UserId>
                .distinct() // delete duplicate UserId from the list
                .count(); // only uniq UserId
        model.addAttribute("counterVisitsToday", counterVisitsToday);
        model.addAttribute("counterUniqUsersToday", counterUniqUsersToday);

        return "addVisitAndReturnStatisticJson";
    }

    @RequestMapping(value = "/visits/{from}/{to}", method = RequestMethod.GET)
    public String getStatisticByDate(
            final @PathVariable(name = "from") String from,
            final @PathVariable(name = "to") String to,
            final Model model) {

        final LocalDateTime fromAsTime = dateAndTimeUtils.stringToTime(from);
        final LocalDateTime toAsTime = dateAndTimeUtils.stringToTime(to);

        final List<Visit> visitsByDate = visitService.getVisitsBetweenDates(fromAsTime, toAsTime);
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
                .filter(visits -> visits.size() >= 10)
                .count();

        model.addAttribute("counterVisitsByDate", counterVisitsByDate);
        model.addAttribute("counterUniqUsersByDate", counterUniqUsersByDate);
        model.addAttribute("counterUniqRegularUsersByDate", counterUniqRegularUsersByDate);

        return "returnStatisticByDateJson";
    }


}
