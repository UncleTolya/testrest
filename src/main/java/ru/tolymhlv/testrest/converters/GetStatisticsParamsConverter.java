package ru.tolymhlv.testrest.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tolymhlv.testrest.services.visit.requests.GetStatisticsParams;
import ru.tolymhlv.testrest.utils.DateAndTimeUtils;
import ru.tolymhlv.testrest.views.GetStatisticsRequestView;

import java.time.LocalDateTime;

@Component
public class GetStatisticsParamsConverter implements Converter<GetStatisticsParams, GetStatisticsRequestView> {

    private final DateAndTimeUtils dateAndTimeUtils;

    @Autowired
    public GetStatisticsParamsConverter(DateAndTimeUtils dateAndTimeUtils) {
        this.dateAndTimeUtils = dateAndTimeUtils;
    }

    @Override
    public GetStatisticsRequestView getView(GetStatisticsParams model) {
        throw new UnsupportedOperationException();
    }

    @Override
    public GetStatisticsParams getModel(GetStatisticsRequestView view) {
        LocalDateTime fromAsTime;
        LocalDateTime toAsTime;
        try {
            fromAsTime = dateAndTimeUtils.stringToTime(view.getFrom());
            toAsTime = dateAndTimeUtils.stringToTime(view.getTo());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid dates format, use yyyy-MM-dd-HH-mm-ss");
        }
        if (fromAsTime.isAfter(toAsTime)) {
            throw new IllegalArgumentException("Invalid dates order");
        }
        return new GetStatisticsParams(fromAsTime, toAsTime);
    }
}
