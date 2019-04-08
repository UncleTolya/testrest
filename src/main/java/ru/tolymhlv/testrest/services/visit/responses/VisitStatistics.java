package ru.tolymhlv.testrest.services.visit.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class VisitStatistics {
    private final int visits;
    private final long uniqueUsers;
}
