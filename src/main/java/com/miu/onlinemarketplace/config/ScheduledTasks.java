package com.miu.onlinemarketplace.config;

import com.miu.onlinemarketplace.service.accountcommission.AccountCommissionService;
import com.miu.onlinemarketplace.service.email.emailhistory.EmailHistoryService;
import com.miu.onlinemarketplace.service.email.emailsender.EmailSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScheduledTasks {

    private final AccountCommissionService accountCommissionService;
    private final EmailHistoryService emailHistoryService;

    private final EmailSenderService emailSenderService;

    public ScheduledTasks(AccountCommissionService accountCommissionService, EmailHistoryService emailHistoryService, EmailSenderService emailSenderService) {
        this.accountCommissionService = accountCommissionService;
        this.emailHistoryService = emailHistoryService;
        this.emailSenderService = emailSenderService;
    }

//    @Scheduled(cron = "0 0 12 * * ?") // At 12:00 p.m. (noon) every day
@Scheduled(fixedRate = 5000) // At every 5 seconds
public void saveCommission() {
    accountCommissionService.saveCommission();
    log.info("Schedule Start ......");
}

    @Scheduled(initialDelay = 60000, fixedDelay = 60000)
    public void resendUnsuccessfulMail() {
        log.info("Email resend scheduler started");
        emailHistoryService.getAllEmailUnsuccessfulMail(10).forEach(emailSenderService::resendMail);
        log.info("Email resend scheduler ended");
    }
}
