package ru.tolymhlv.testrest.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import ru.tolymhlv.testrest.services.visit.responses.VisitStatistics;

@Component
public class VisitStatisticsConverter implements Converter<VisitStatistics, String> {

    @Override
    public String getView(VisitStatistics model) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(model);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error in deserialization");
        }

    }

    @Override
    public VisitStatistics getModel(String response) {
        throw new UnsupportedOperationException();
    }
}
