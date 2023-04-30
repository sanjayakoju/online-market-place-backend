package com.miu.onlinemarketplace.service.generic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {
    private String key;
    private Object value;
    private SearchOperation operation;

}
