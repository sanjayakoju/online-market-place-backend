package com.miu.onlinemarketplace.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EmailHistoryDto {
    private String mailType;
    private String message;
    private String subject;
    private String fromEmail;
    private String toEmail;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime mailSendDateTime;
}
