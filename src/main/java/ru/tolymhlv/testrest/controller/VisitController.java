package ru.tolymhlv.testrest.controller;

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

import javax.validation.constraints.NotNull;
import java.io.IOException;
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

        VisitCreateRequest visitCreateRequest;
        try {
            visitCreateRequest = readValueFromJson(requestJson, VisitCreateRequest.class);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in deserialization request");
        }

        VisitStatistics visitStatistics;
        try {
            visitStatistics = visitService.create(visitCreateRequest);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }

        return getResponseAsJSON(visitStatistics);
    }

    @GetMapping(value = "/visits", produces = "application/json")
    public ResponseEntity<Object> getFullVisitStatisticsByDate(
            final @RequestParam(name = "from") String from,
            final @RequestParam(name = "to") String to) {
        LocalDateTime fromAsTime;
        LocalDateTime toAsTime;
        try {
            fromAsTime = dateAndTimeUtils.stringToTime(from);
            toAsTime = dateAndTimeUtils.stringToTime(to);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid date format, please use yyyy-MM-dd-HH-mm-ss");
        }
        if (fromAsTime.isAfter(toAsTime)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid dates order");
        }

        final GetStatisticsRequest getStatisticsRequest = new GetStatisticsRequest(fromAsTime, toAsTime);
        final FullVisitStatistics fullVisitStatistics = visitService.getStatistics(getStatisticsRequest);

        return getResponseAsJSON(fullVisitStatistics);
    }

    private ResponseEntity<Object> getResponseAsJSON(@NotNull final VisitResponse visitResponse) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            return ResponseEntity.ok(mapper.writeValueAsString(visitResponse));
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in serialization response");
        }
    }

    private <T> T readValueFromJson(@NotNull final String jsonString, @NotNull final Class<T> clazz) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, clazz);
    }


}
