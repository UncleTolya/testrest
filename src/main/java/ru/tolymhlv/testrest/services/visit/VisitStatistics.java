package ru.tolymhlv.testrest.services.visit;

import lombok.Data;

@Data
public class VisitStatistics {
    private final long visits;
    private final long uniqueUsers;
}
