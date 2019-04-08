package ru.tolymhlv.testrest.services.visit.responses;

import lombok.Data;

@Data
public class FullVisitStatistics {
    private final long visits;
    private final long uniqueUsers;
    private final long regularUniqUsers;

}
