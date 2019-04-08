package ru.tolymhlv.testrest.services.visit.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetStatisticsParams {
    private LocalDateTime from;
    private LocalDateTime to;
}
