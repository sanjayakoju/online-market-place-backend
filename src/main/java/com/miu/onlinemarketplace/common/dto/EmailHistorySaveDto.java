package com.miu.onlinemarketplace.common.dto;

import com.miu.onlinemarketplace.common.enums.MailType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailHistorySaveDto {
    private MailType mailType;
    private String message;
    private String subject;
    private String fromEmail;
    private String toEmail;
    private Boolean isSend = false;
}
