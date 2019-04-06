package ru.tolymhlv.testrest.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.tolymhlv.testrest.services.requests.VisitCreateRequest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class VisitServiceImplTest {

    @Autowired
    private VisitServiceImpl visitService;


    @Test
    public void creatGetEmptyValuesReturnNPE() {
        VisitCreateRequest request = new VisitCreateRequest(null, null);
        assertThrows(NullPointerException.class, () -> visitService.create(request));
    }

    @Test
    public void creatGetNullRequestReturnNPE() {
        VisitCreateRequest request = new VisitCreateRequest(null, null);
        assertThrows(NullPointerException.class, () -> visitService.create(request));
    }


}