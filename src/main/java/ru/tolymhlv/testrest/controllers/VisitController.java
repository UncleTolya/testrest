package ru.tolymhlv.testrest.controllers;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tolymhlv.testrest.converters.FullVisitStatisticsConverter;
import ru.tolymhlv.testrest.converters.GetStatisticsParamsConverter;
import ru.tolymhlv.testrest.converters.VisitCreateConverter;
import ru.tolymhlv.testrest.converters.VisitStatisticsConverter;
import ru.tolymhlv.testrest.services.visit.VisitServiceImpl;
import ru.tolymhlv.testrest.services.visit.requests.GetStatisticsParams;
import ru.tolymhlv.testrest.services.visit.requests.VisitCreateRequest;
import ru.tolymhlv.testrest.services.visit.responses.FullVisitStatistics;
import ru.tolymhlv.testrest.services.visit.responses.VisitStatistics;
import ru.tolymhlv.testrest.views.FullVisitStatisticsView;
import ru.tolymhlv.testrest.views.GetStatisticsView;
import ru.tolymhlv.testrest.views.VisitCreateView;
import ru.tolymhlv.testrest.views.VisitStatisticsView;

@RestController
public class VisitController {
    private final VisitCreateConverter visitCreateConverter;
    private final GetStatisticsParamsConverter getStatisticsParamsConverter;
    private final VisitStatisticsConverter visitStatisticsConverter;
    private final FullVisitStatisticsConverter fullVisitStatisticsConverter;
    private final VisitServiceImpl visitService;

    @Autowired
    public VisitController(@NonNull final VisitServiceImpl visitService, VisitCreateConverter visitCreateConverter, GetStatisticsParamsConverter getStatisticsParamsConverter, VisitStatisticsConverter visitStatisticsConverter, FullVisitStatisticsConverter fullVisitStatisticsConverter) {
        this.visitService = visitService;
        this.visitCreateConverter = visitCreateConverter;
        this.getStatisticsParamsConverter = getStatisticsParamsConverter;
        this.visitStatisticsConverter = visitStatisticsConverter;
        this.fullVisitStatisticsConverter = fullVisitStatisticsConverter;
    }

    @PostMapping(name = "/visit", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> createVisitAndGetVisitStatistics(
            final @RequestBody String requestJson) {

        final VisitCreateView visitCreateView = new VisitCreateView(requestJson);

        VisitCreateRequest visitCreateRequest;
        try {
            visitCreateRequest = visitCreateConverter.getModel(visitCreateView);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        VisitStatistics visitStatistics;
        try {
            visitStatistics = visitService.create(visitCreateRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        final VisitStatisticsView visitStatisticsView
                = new VisitStatisticsView(visitStatisticsConverter.getView(visitStatistics));

        return ResponseEntity.ok(visitStatisticsView.getResponseJson());
    }

    @GetMapping(value = "/visits", produces = "application/json")
    public ResponseEntity<String> getFullVisitStatisticsByDate(
            final @RequestParam(name = "from") String from,
            final @RequestParam(name = "to") String to) {

        final GetStatisticsView getStatisticsView = new GetStatisticsView(from, to);

        GetStatisticsParams getStatisticsParams;
        try {
            getStatisticsParams = getStatisticsParamsConverter.getModel(getStatisticsView);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        FullVisitStatistics fullVisitStatistics;
        try {
            fullVisitStatistics = visitService.getStatistics(getStatisticsParams);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        FullVisitStatisticsView fullVisitStatisticsView
                = new FullVisitStatisticsView(fullVisitStatisticsConverter.getView(fullVisitStatistics));
        return ResponseEntity.ok(fullVisitStatisticsView.getResponseJson());
    }
}
