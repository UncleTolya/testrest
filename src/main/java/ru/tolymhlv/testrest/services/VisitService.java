package ru.tolymhlv.testrest.services;

import ru.tolymhlv.testrest.services.responses.FullVisitStatistics;
import ru.tolymhlv.testrest.services.requests.GetStatisticsRequest;
import ru.tolymhlv.testrest.services.requests.VisitCreateRequest;
import ru.tolymhlv.testrest.services.responses.VisitStatistics;

public interface VisitService {

    VisitStatistics create(VisitCreateRequest request);

    FullVisitStatistics getStatistics(GetStatisticsRequest request);
}
