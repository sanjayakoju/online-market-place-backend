package com.miu.onlinemarketplace.config;

import com.miu.onlinemarketplace.security.CustomUserDetailsService;
import com.miu.onlinemarketplace.security.JwtTokenFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    private final AppProperties appProperties;

    public WebSecurityConfig(CustomUserDetailsService customUserDetailsService, AppProperties appProperties) {
        this.customUserDetailsService = customUserDetailsService;
        this.appProperties = appProperties;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

        return http.cors(Customizer.withDefaults())
                .csrf().disable().httpBasic().and()
                .authorizeHttpRequests(ar -> ar
//                                .requestMatchers("/**").permitAll().anyRequest().authenticated()
                                .requestMatchers("/", "/actuator/**", "/auth/**", "/public/**", "/s", "/invoice/**", "/order/pay/**", "/report/**")
                                .permitAll()
                                .anyRequest().authenticated()
                )
                .exceptionHandling(eh -> eh
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage()))
                )
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtTokenFilter(appProperties.getJwt().getSecretKey()), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
