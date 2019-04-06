package ru.tolymhlv.testrest.services.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetStatisticsRequest {
    private LocalDateTime from;
    private LocalDateTime to;
}
