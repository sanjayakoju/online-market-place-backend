package com.miu.onlinemarketplace.security;

import com.miu.onlinemarketplace.config.AppProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    private String secretKey;

    public JwtTokenFilter(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        log.info("Invoked OncePerRequest JwtTokenFilter");
        JwtTokenParser jwtTokenParser = new JwtTokenParser(secretKey);
        String token = jwtTokenParser.getTokenFromRequestHeader(request);
        if (StringUtils.hasText(token) && jwtTokenParser.validateToken(token, request)) {
            log.info("Jwt Token Filter, processing authenticated request");
            Authentication auth = jwtTokenParser.getAuthenticationFromTokenString(token, request);
            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
                log.info("Authentication Object set for the current request");
            } else {
                log.error("Couldn't process the Authentication for the current request");
            }
        }
        chain.doFilter(request, response);
    }
}