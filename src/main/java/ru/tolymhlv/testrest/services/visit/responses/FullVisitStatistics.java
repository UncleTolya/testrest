package ru.tolymhlv.testrest.services.visit.responses;

import lombok.Data;

@Data
public class FullVisitStatistics implements VisitResponse{
    private final long visits;
    private final long uniqueUsers;
    private final long regularUniqUsers;
}
