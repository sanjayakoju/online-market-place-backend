package com.miu.onlinemarketplace.service.generic.dtos;

import lombok.Data;

@Data
public class GenericFilterRequestDTO<T> {

    private T dataFilter;
}
