package com.miu.onlinemarketplace.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.miu.onlinemarketplace.config.AppProperties;
import com.miu.onlinemarketplace.security.models.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Autowired
    private AppProperties appProperties;

    public String createToken(Authentication authentication) throws JsonProcessingException {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Claims claims = Jwts.claims().setSubject(customUserDetails.getUsername());
        claims.put("userId", String.valueOf(customUserDetails.getId()));
        claims.put("username", customUserDetails.getUsername());
        claims.put("role", customUserDetails.getRole());
        AppProperties.Jwt jwt = appProperties.getJwt();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (jwt.getExpireSeconds() * 1000)))
                .signWith(SignatureAlgorithm.HS256,
                        Base64.getEncoder().encodeToString(jwt.getSecretKey().getBytes())
                )
                .compact();
    }

}
