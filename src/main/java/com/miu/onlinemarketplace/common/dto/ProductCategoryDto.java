package com.miu.onlinemarketplace.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryDto {

    private Long categoryId;
    private String category;
    @CreationTimestamp
    private LocalDateTime createdDate;

    private String fileUri;

}
