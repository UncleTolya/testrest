package ru.tolymhlv.testrest.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import ru.tolymhlv.testrest.services.visit.requests.VisitCreateRequest;
import ru.tolymhlv.testrest.views.VisitCreateRequestView;

import java.io.IOException;

@Component
public class VisitCreateConverter implements Converter<VisitCreateRequest, VisitCreateRequestView> {

    @Override
    public VisitCreateRequestView getView(VisitCreateRequest model) {
        throw new UnsupportedOperationException();
    }

    @Override
    public VisitCreateRequest getModel(VisitCreateRequestView view) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(view.getRequestBody(), VisitCreateRequest.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error in deserialization view " + view.getRequestBody());
        }
    }
}
