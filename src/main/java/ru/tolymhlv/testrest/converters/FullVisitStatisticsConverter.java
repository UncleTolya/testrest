package ru.tolymhlv.testrest.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import ru.tolymhlv.testrest.services.visit.responses.FullVisitStatistics;

@Component
public class FullVisitStatisticsConverter implements Converter<FullVisitStatistics, String> {

    @Override
    public String getView(@NonNull final FullVisitStatistics model) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(model);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error in deserialization");
        }

    }

    @Override
    public FullVisitStatistics getModel(@NonNull final String response) {
        throw new UnsupportedOperationException();
    }
}
