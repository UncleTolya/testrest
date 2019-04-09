package ru.tolymhlv.testrest.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.tolymhlv.testrest.services.visit.VisitService;
import ru.tolymhlv.testrest.services.visit.requests.VisitCreateRequest;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class VisitServiceTest {
    @Autowired
    private VisitService visitService;


    @Test
    public void creatGetNullValuesReturnNPE() {
        final VisitCreateRequest request = new VisitCreateRequest(null, null);
        assertThrows(IllegalArgumentException.class, () -> visitService.create(request));
    }

    @Test
    public void creatGetEmptyValuesReturnIAE() {
        final VisitCreateRequest request = new VisitCreateRequest("", "");
        assertThrows(IllegalArgumentException.class, () -> visitService.create(request));
    }

    @Test
    public void creatGetSpaceValuesReturnIAE() {
        final VisitCreateRequest request = new VisitCreateRequest(" ", " ");
        assertThrows(IllegalArgumentException.class, () -> visitService.create(request));
    }

    @Test
    public void creatGetSpacesValuesReturnIAE() {
        final VisitCreateRequest request = new VisitCreateRequest("   ", "   ");
        assertThrows(IllegalArgumentException.class, () -> visitService.create(request));
    }


//    //        yyyy-MM-dd-HH-mm-ss
//    @Test
//    public void getStatisticsNotTheDatesReturnIAE() {
//        final GetStatisticsParams request = new GetStatisticsParams("userId", "pageId");
//        assertThrows(IllegalArgumentException.class, () -> visitService.getStatistics(request));
//    }
//    @Test
//    public void getStatisticsGetIllegalFormatReturnIAE() {
//        final GetStatisticsParams request = new GetStatisticsParams("26-02-2019-15-00-00", "28-02-2019-15-00-00");
//        assertThrows(IllegalArgumentException.class, () -> visitService.getStatistics(request));
//    }
//
//    @Test
//    public void getStatisticsGetIllegalOrderReturnIAE() {
//        final GetStatisticsParams request = new GetStatisticsParams("26-02-2019-15-00-00", "28-02-2019-15-00-00");
//        assertThrows(IllegalArgumentException.class, () -> visitService.getStatistics(request));
//    }
}
