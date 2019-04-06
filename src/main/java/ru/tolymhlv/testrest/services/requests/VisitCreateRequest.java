package ru.tolymhlv.testrest.services.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitCreateRequest {
    private String userId;
    private String pageId;
}
