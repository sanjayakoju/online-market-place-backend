//package com.miu.onlinemarketplace.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//@EnableWebMvc
//public class WebCorsConfig implements WebMvcConfigurer {
//
//    @Autowired
//    private AppProperties appProperties;
////    @Override
////    public void addCorsMappings(CorsRegistry registry) {
////        CorsConfiguration configuration = new CorsConfiguration();
////        configuration.setAllowedOrigins(appProperties.getCors().getAllowedOrigins());
////        registry.addMapping("/**")
////                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
////                .allowedHeaders("*")
////                .allowCredentials(true)
////                .maxAge(3600).combine(configuration);
////    }
//}
