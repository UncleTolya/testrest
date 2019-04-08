package ru.tolymhlv.testrest.services.visit;

import ru.tolymhlv.testrest.services.visit.responses.FullVisitStatistics;
import ru.tolymhlv.testrest.services.visit.requests.GetStatisticsParams;
import ru.tolymhlv.testrest.services.visit.requests.VisitCreateRequest;
import ru.tolymhlv.testrest.services.visit.responses.VisitStatistics;

public interface VisitService {

    VisitStatistics create(VisitCreateRequest request);

    FullVisitStatistics getStatistics(GetStatisticsParams request);
}
