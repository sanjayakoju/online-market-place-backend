package com.miu.onlinemarketplace.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class AppProperties {

    private String domainUrl;

    private final Cors cors = new Cors();
    private final Jwt jwt = new Jwt();
    private final Mail mail = new Mail();

    @Getter
    @Setter
    public static class Cors {
        private List<String> allowedOrigins = new ArrayList<>();
    }

    @Getter
    @Setter
    public static class Jwt {
        private String secretKey;
        private Long expireSeconds;
    }

    @Getter
    @Setter
    public static class Mail {
        private String host;
        private Integer port;
        private String username;
        private String password;
        // In seconds, 10minutes=600, or 3days=259200
        private long verificationCodeExpirationSeconds = 259200;
    }
}
