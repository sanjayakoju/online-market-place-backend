package com.miu.onlinemarketplace.config;

import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasperReportConfiguration {

    @Bean
    JasperPrint jasperPrint() {
        return new JasperPrint();
    }
}
