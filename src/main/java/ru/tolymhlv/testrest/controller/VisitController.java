package ru.tolymhlv.testrest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tolymhlv.testrest.services.VisitServiceImpl;
import ru.tolymhlv.testrest.services.requests.GetStatisticsRequest;
import ru.tolymhlv.testrest.services.requests.VisitCreateRequest;
import ru.tolymhlv.testrest.services.responses.FullVisitStatistics;
import ru.tolymhlv.testrest.services.responses.VisitResponse;
import ru.tolymhlv.testrest.services.responses.VisitStatistics;

import javax.validation.constraints.NotNull;
import java.io.IOException;

@RestController
public class VisitController {

    private final VisitServiceImpl visitService;

    @Autowired
    public VisitController(@NonNull final VisitServiceImpl visitService) {
        this.visitService = visitService;
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
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return getResponseAsJSON(visitStatistics);
    }

    @GetMapping(value = "/visits", produces = "application/json")
    public ResponseEntity<Object> getFullVisitStatisticsByDate(
            final @RequestParam(name = "from") String from,
            final @RequestParam(name = "to") String to) {
        GetStatisticsRequest getStatisticsRequest;
        try {
            getStatisticsRequest = new GetStatisticsRequest(from, to);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        FullVisitStatistics fullVisitStatistics;
        try {
            fullVisitStatistics = visitService.getStatistics(getStatisticsRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

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
