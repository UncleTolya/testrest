package ru.tolymhlv.testrest.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import ru.tolymhlv.testrest.services.visit.requests.VisitCreateRequest;
import ru.tolymhlv.testrest.views.VisitCreateView;

import java.io.IOException;

@Component
public class VisitCreateConverter implements Converter<VisitCreateRequest, VisitCreateView> {

    @Override
    public VisitCreateView getView(@NonNull final VisitCreateRequest model) {
        throw new UnsupportedOperationException();
    }

    @Override
    public VisitCreateRequest getModel(@NonNull final VisitCreateView view) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(view.getRequestBody(), VisitCreateRequest.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error in deserialization view " + view.getRequestBody());
        }
    }
}
