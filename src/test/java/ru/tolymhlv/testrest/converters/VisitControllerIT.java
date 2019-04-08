package ru.tolymhlv.testrest.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import ru.tolymhlv.testrest.services.visit.requests.VisitCreateRequest;
import ru.tolymhlv.testrest.services.visit.responses.FullVisitStatistics;
import ru.tolymhlv.testrest.services.visit.responses.VisitStatistics;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = "/create-visits-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/create-visits-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class VisitControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FullVisitStatisticsConverter fullVisitStatisticsConverter;

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

    @Test
    public void getFullVisitStatisticsByDateCorrect() throws Exception {
        final String fromString = "2019-04-04-00-00-00";
        final String toString = "2019-04-06-23-59-59";

        final FullVisitStatistics response = new FullVisitStatistics(28, 3, 1);
        final String responseJson = fullVisitStatisticsConverter.getView(response);

        this.mockMvc.perform(get("/visits")
                .param("from", fromString)
                .param("to", toString))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(responseJson))
                .andReturn();
    }
}
