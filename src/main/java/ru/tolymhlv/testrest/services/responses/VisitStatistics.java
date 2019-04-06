package ru.tolymhlv.testrest.services.responses;

import lombok.Data;

@Data
public class VisitStatistics implements VisitResponse{
    private final long visits;
    private final long uniqueUsers;
}
