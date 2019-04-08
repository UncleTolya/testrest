package ru.tolymhlv.testrest.services.visit.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitCreateRequest {
    private String userId;
    private String pageId;
}
