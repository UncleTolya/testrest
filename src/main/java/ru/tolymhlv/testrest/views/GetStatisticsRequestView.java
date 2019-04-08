package ru.tolymhlv.testrest.views;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetStatisticsRequestView {
    private final String from;
    private final String to;
}
