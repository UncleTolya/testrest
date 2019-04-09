package ru.tolymhlv.testrest.services;

import lombok.NonNull;
import ru.tolymhlv.testrest.services.visit.VisitService;
import ru.tolymhlv.testrest.services.visit.requests.GetStatisticsParams;
import ru.tolymhlv.testrest.services.visit.requests.VisitCreateRequest;
import ru.tolymhlv.testrest.services.visit.responses.FullVisitStatistics;
import ru.tolymhlv.testrest.services.visit.responses.VisitStatistics;

import static org.junit.jupiter.api.Assertions.*;

public abstract class VisitServiceTestBase {

    abstract VisitService visitService();

    public void createNegativeException(@NonNull final VisitCreateRequest request,
                                        @NonNull final Class<? extends Throwable> exception) {
        assertThrows(exception, () -> visitService().create(request));
    }

    public void createPositive(@NonNull final VisitCreateRequest request,
                               @NonNull final VisitStatistics visitStatistics) {
        assertEquals(visitStatistics, visitService().create(request));
    }

    public void getStatisticsPositive(@NonNull final GetStatisticsParams request,
                                      @NonNull final FullVisitStatistics fullVisitStatistics) {
        assertEquals(fullVisitStatistics, visitService().getStatistics(request));
    }
}
