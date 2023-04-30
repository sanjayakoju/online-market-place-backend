package com.miu.onlinemarketplace.service.email.emailtemplate;

import com.miu.onlinemarketplace.common.enums.MailType;
import com.miu.onlinemarketplace.entities.EmailTemplate;
import com.miu.onlinemarketplace.exception.DataNotFoundException;
import com.miu.onlinemarketplace.repository.EmailTemplateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class EmailTemplateServiceImpl implements EmailTemplateService {

    private final EmailTemplateRepository emailTemplateRepository;

    public EmailTemplateServiceImpl(EmailTemplateRepository emailTemplateRepository) {
        this.emailTemplateRepository = emailTemplateRepository;
    }

    @Override
    public EmailTemplate getTemplateByMailType(MailType mailType) throws DataNotFoundException {
        Optional<EmailTemplate> emailTemplateOptional = emailTemplateRepository.findByMailType(mailType);
        if (emailTemplateOptional.isEmpty()) {
            log.error("Email Template with mail Type: " + mailType.name() + " doesn't exists.");
        }
        return emailTemplateOptional.orElseThrow(() -> new DataNotFoundException("Email Template for mail type: " + mailType.name() + " not found!!"));
    }
}
