package com.miu.onlinemarketplace.utils;

import com.miu.onlinemarketplace.config.AppProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class MailSenderUtil {

    private static JavaMailSender javaMailSender;

    private MailSenderUtil() {
    }

    public static JavaMailSender getInstance() {
        if (javaMailSender == null) {
            javaMailSender = javaMailSender();
        }
        return javaMailSender;
    }

    private static JavaMailSender javaMailSender() {
        AppProperties.Mail mail = new AppProperties().getMail();
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mail.getHost());
        mailSender.setPort(mail.getPort());
        mailSender.setUsername(mail.getUsername());
        mailSender.setPassword(mail.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        return mailSender;
    }
}
