package ru.tolymhlv.testrest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.tolymhlv.testrest.domains.Visit;
import ru.tolymhlv.testrest.services.DateAndTimeUtils;
import ru.tolymhlv.testrest.services.visit.VisitCreateRequest;
import ru.tolymhlv.testrest.services.visit.VisitService;
import ru.tolymhlv.testrest.services.visit.VisitServiceImpl;

import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class VisitController {

    private final VisitService visitService;
    private final DateAndTimeUtils dateAndTimeUtils;

    @Value("${quantityOfVisitedPagesToBeingRegularUser")
    @Pattern(regexp = "[0-9]+")
    private int quantityOfVisitedPagesToBeingRegularUser;

    @Autowired
    public VisitController(VisitService visitService, DateAndTimeUtils dateAndTimeUtils) {
        this.visitService = visitService;
        this.dateAndTimeUtils = dateAndTimeUtils;
    }

    @PostMapping(name = "/visit", produces = "application/json")
    public String makeVisitEventAndGetCommonStatistic(
            final @RequestBody String userId,
            final @RequestBody String pageId) {
//            final Model model) {
        final VisitCreateRequest visitCreateRequest = new VisitCreateRequest(userId, pageId);
//        visit.addVisit(userId, pageId);
//
//        final List<Visit> visitsToday = visit.getVisitsFromStartDay();
//        final int counterVisitsToday = visitsToday.size();
//        final long counterUniqUsersToday = visitsToday
//                .stream()
//                .map(Visit::getUserId)
//                .distinct()
//                .count();
//        model.addAttribute("counterVisitsToday", counterVisitsToday);
//        model.addAttribute("counterUniqUsersToday", counterUniqUsersToday);
//
//        return "addVisitAndReturnStatisticJson";
        final VisitService visitStatistics = new VisitServiceImpl().create(visitCreateRequest);
        return visitStatistics.toString();

    }

    @GetMapping(value = "/visits/{from}/{to}")
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
                .distinct()
                .filter(visits -> visits.size() >= quantityOfVisitedPagesToBeingRegularUser)
                .count();

        model.addAttribute("counterVisitsByDate", counterVisitsByDate);
        model.addAttribute("counterUniqUsersByDate", counterUniqUsersByDate);
        model.addAttribute("counterUniqRegularUsersByDate", counterUniqRegularUsersByDate);

        return "returnStatisticByDateJson";
    }


}
