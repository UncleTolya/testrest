package ru.tolymhlv.testrest.controllers;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.tolymhlv.testrest.domains.Visit;
import ru.tolymhlv.testrest.services.requests.VisitCreateRequest;
import ru.tolymhlv.testrest.services.responses.VisitStatistics;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class VisitControllerIT {
    @Autowired
    private VisitController visitController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createVisitAndGetVisitStatisticsCorrect() throws Exception {
        final String userId = RandomStringUtils.randomAlphanumeric(10);
        final String pageId = RandomStringUtils.randomAlphanumeric(10);


        ObjectMapper mapper = new ObjectMapper();
        final VisitCreateRequest request = new VisitCreateRequest(userId, pageId);
        final String requestJson = mapper.writeValueAsString(request);

        final VisitStatistics response = new VisitStatistics(1, 1);
        final String responseJson = mapper.writeValueAsString(response);

        this.mockMvc.perform(post("/visit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(responseJson))
                .andReturn();
    }
}
