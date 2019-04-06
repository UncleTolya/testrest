package ru.tolymhlv.testrest.services.requests;

import lombok.Data;

import java.io.Serializable;

@Data
public class VisitCreateRequest implements Serializable {
    private final String userId;
    private final String pageId;
}
