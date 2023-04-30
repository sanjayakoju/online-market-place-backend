package com.miu.onlinemarketplace.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AllEmailHistoryPage {
    private List<EmailHistoryDto> emailHistory;
    private Integer totalPage;
    private Long totalItem;
}
