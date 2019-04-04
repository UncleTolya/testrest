package ru.tolymhlv.testrest.services.visit.requests;

import lombok.Data;

@Data
public class VisitCreateRequest {
    private final String userId;
    private final String pageId;
}
