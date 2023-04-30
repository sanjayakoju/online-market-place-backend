package com.miu.onlinemarketplace;

import com.miu.onlinemarketplace.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties({AppProperties.class})
@EnableScheduling
public class OnlinemarketplaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlinemarketplaceApplication.class, args);
    }

}
