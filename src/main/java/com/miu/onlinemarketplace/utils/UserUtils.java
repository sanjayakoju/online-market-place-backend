package com.miu.onlinemarketplace.utils;

import com.miu.onlinemarketplace.security.models.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtils {
    public static CustomUserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return (CustomUserDetails) principal;
        }
        return null;
    }

    public static String getCurrentUsername() {
        CustomUserDetails userDetails = getCurrentUser();
        if (userDetails == null) {
            return null;
        }
        return userDetails.getUsername();
    }

    public static Long getCurrentUserId() {
        CustomUserDetails userDetails = getCurrentUser();
        if (userDetails == null) {
            return null;
        }
        return userDetails.getId();
    }
}
