package com.miu.onlinemarketplace.service.email.emailtemplate;

import com.miu.onlinemarketplace.common.enums.MailType;
import com.miu.onlinemarketplace.entities.EmailTemplate;

public interface EmailTemplateService {
    EmailTemplate getTemplateByMailType(MailType mailType);
}
