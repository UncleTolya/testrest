package ru.tolymhlv.testrest.services.visit;

public interface VisitService {

    VisitStatistics create(VisitCreateRequest request);

    FullVisitStatistics getStatistics(GetStatisticsRequest request);
}
