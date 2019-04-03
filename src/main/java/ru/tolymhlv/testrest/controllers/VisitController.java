package ru.tolymhlv.testrest.controllers;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tolymhlv.testrest.services.DateAndTimeUtils;
import ru.tolymhlv.testrest.services.visit.*;

import java.time.LocalDateTime;

@RestController
public class VisitController {

    private final VisitServiceImpl visitService;
    private final DateAndTimeUtils dateAndTimeUtils;

    @Autowired
    public VisitController(@NonNull final VisitServiceImpl visitService, @NonNull final DateAndTimeUtils dateAndTimeUtils) {
        this.visitService = visitService;
        this.dateAndTimeUtils = dateAndTimeUtils;
    }


    @PostMapping(name = "/visit", consumes = "application/x-www-form-urlencoded", produces = "application/json")
    public ResponseEntity<?> makeVisitEventAndGetCommonStatistic(
            final @RequestBody String userId,
            final @RequestBody String pageId) {

        final VisitCreateRequest visitCreateRequest = new VisitCreateRequest(userId, pageId);
        final VisitStatistics visitStatistics = visitService.create(visitCreateRequest);

        return ResponseEntity.ok(visitStatistics.toString());
    }

    @GetMapping(value = "/visits", consumes = "application/x-www-form-urlencoded", produces = "application/json")
    public ResponseEntity<?> getStatisticByDate(
            final @RequestParam String from,
            final @RequestParam String to) {

        final LocalDateTime fromAsTime = dateAndTimeUtils.stringToTime(from);
        final LocalDateTime toAsTime = dateAndTimeUtils.stringToTime(to);

        if (fromAsTime.isAfter(toAsTime)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        final GetStatisticsRequest getStatisticsRequest = new GetStatisticsRequest(fromAsTime, toAsTime);
        final FullVisitStatistics fullVisitStatistics = visitService.getStatistics(getStatisticsRequest);

        return ResponseEntity.ok(fullVisitStatistics.toString());
    }


}
