package ru.tolymhlv.testrest.services.visit.requests;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class GetStatisticsRequest {
    private final LocalDateTime from;
    private final LocalDateTime to;
}
