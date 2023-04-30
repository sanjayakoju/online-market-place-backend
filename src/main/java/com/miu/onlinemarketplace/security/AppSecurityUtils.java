package com.miu.onlinemarketplace.security;

import com.miu.onlinemarketplace.security.models.CustomUserDetails;
import com.miu.onlinemarketplace.security.models.EnumRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class AppSecurityUtils {

    public static Set<EnumRole> convertStringRolesSetToEnumSet(Set<String> roles) {
        Set<EnumRole> roleSet = new HashSet<>();
        for (String role : roles) {
            roleSet.add(Enum.valueOf(EnumRole.class, role));
        }
        return roleSet;
    }

    public static CustomUserDetails getCurrentUserPrinciple() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails) {
                return ((CustomUserDetails) principal);
            }
        }
        return null;
    }

    public static Optional<Long> getCurrentUserId() {
        Optional<Long> optionalUserId = Optional.ofNullable(getCurrentUserPrinciple())
                .map(customUserDetails -> customUserDetails.getId());
        return optionalUserId;
    }

    public static Optional<EnumRole> getCurrentUserRole() {
        Optional<EnumRole> optionalUserRole = Optional.ofNullable(getCurrentUserPrinciple())
                .map(customUserDetails -> customUserDetails.getRole());
        return optionalUserRole;
    }

}
