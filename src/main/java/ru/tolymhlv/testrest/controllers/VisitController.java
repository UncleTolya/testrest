package ru.tolymhlv.testrest.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tolymhlv.testrest.services.DateAndTimeUtils;
import ru.tolymhlv.testrest.services.VisitServiceImpl;
import ru.tolymhlv.testrest.services.requests.GetStatisticsRequest;
import ru.tolymhlv.testrest.services.requests.VisitCreateRequest;
import ru.tolymhlv.testrest.services.responses.FullVisitStatistics;
import ru.tolymhlv.testrest.services.responses.VisitResponse;
import ru.tolymhlv.testrest.services.responses.VisitStatistics;

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

    @PostMapping(name = "/visit", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> createVisitAndGetVisitStatistics(
            final @RequestBody String requestJson) {

        final VisitCreateRequest visitCreateRequest = JSONObject.parseObject(requestJson, VisitCreateRequest.class);
        final VisitStatistics visitStatistics = visitService.create(visitCreateRequest);

        return getResponseAsJSON(visitStatistics);
    }

    @GetMapping(value = "/visits", produces = "application/json")
    public ResponseEntity<?> getFullVisitStatisticsByDate(
            final @RequestParam(name = "from") String from,
            final @RequestParam(name = "to") String to) {

        final LocalDateTime fromAsTime = dateAndTimeUtils.stringToTime(from);
        final LocalDateTime toAsTime = dateAndTimeUtils.stringToTime(to);
        if (fromAsTime.isAfter(toAsTime)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        final GetStatisticsRequest getStatisticsRequest = new GetStatisticsRequest(fromAsTime, toAsTime);
        final FullVisitStatistics fullVisitStatistics = visitService.getStatistics(getStatisticsRequest);

        return ResponseEntity.ok(JSON.toJSONString(fullVisitStatistics));
    }

    private ResponseEntity<Object> getResponseAsJSON(VisitResponse visitResponse) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            return ResponseEntity.ok(mapper.writeValueAsString(visitResponse));
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
