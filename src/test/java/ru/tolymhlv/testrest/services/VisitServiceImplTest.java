package ru.tolymhlv.testrest.services;

import lombok.NonNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import ru.tolymhlv.testrest.services.visit.VisitService;
import ru.tolymhlv.testrest.services.visit.VisitServiceImpl;
import ru.tolymhlv.testrest.services.visit.requests.GetStatisticsParams;
import ru.tolymhlv.testrest.services.visit.requests.VisitCreateRequest;
import ru.tolymhlv.testrest.services.visit.responses.FullVisitStatistics;
import ru.tolymhlv.testrest.services.visit.responses.VisitStatistics;
import ru.tolymhlv.testrest.utils.DateAndTimeUtils;

import java.time.LocalDateTime;
import java.util.stream.Stream;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@Transactional
class VisitServiceImplTest extends VisitServiceTestBase {

    private final VisitServiceImpl visitService;
    private final DateAndTimeUtils dateAndTimeUtils;

    @Autowired
    public VisitServiceImplTest(VisitServiceImpl visitService, DateAndTimeUtils dateAndTimeUtils) {
        this.visitService = visitService;
        this.dateAndTimeUtils = dateAndTimeUtils;
    }

    @Override
    VisitService visitService() {
        return visitService;
    }

    @ParameterizedTest
    @MethodSource("provideVisitCreateRequest")
    public void createPositive(@NonNull final VisitCreateRequest request) {
        final VisitStatistics visitStatistics = new VisitStatistics(1, 1);
        super.createPositive(request, visitStatistics);
    }

    private static Stream<VisitCreateRequest> provideVisitCreateRequest() {
        return Stream.of(
                new VisitCreateRequest("userId", "pageId"),
                new VisitCreateRequest("u1", "p1"),
                new VisitCreateRequest("selector157", "188888883"),
                new VisitCreateRequest("2525", "idididid"),
                new VisitCreateRequest("0", "0")
        );
    }

    @ParameterizedTest
    @MethodSource("provideVisitCreateWrongRequest")
    public void createNegativeTestIllegalArgument(@NonNull final VisitCreateRequest request) {
        super.createNegativeException(request, IllegalArgumentException.class);
    }

    private static Stream<VisitCreateRequest> provideVisitCreateWrongRequest() {
        return Stream.of(
                new VisitCreateRequest("", ""),
                new VisitCreateRequest(" ", " "),
                new VisitCreateRequest("  ", "  "),
                new VisitCreateRequest("!", "pageId"),
                new VisitCreateRequest("23", "_")
        );
    }

    @Test
    public void getStatisticsPositiveTest() {
        makeOneVisit("userId", "pageId");

        final LocalDateTime now = dateAndTimeUtils.now();
        final LocalDateTime exactlyFiveMinutesAgo = now.minusMinutes(5);
        final GetStatisticsParams params = new GetStatisticsParams(exactlyFiveMinutesAgo, now);
        final FullVisitStatistics fullVisitStatistics = new FullVisitStatistics(1, 1, 0);

        super.getStatisticsPositive(params, fullVisitStatistics);
    }

    private void makeOneVisit(@NonNull final String userId, @NonNull final String pageId) {
        final VisitCreateRequest request = new VisitCreateRequest(userId, pageId);
        visitService().create(request);
    }

}