package com.miu.onlinemarketplace.security.models;

public enum EnumRole {
    ROLE_USER("ROLE_USER"), ROLE_VENDOR("ROLE_VENDOR"), ROLE_ADMIN("ROLE_ADMIN"), ROLE_CLIENT("ROLE_CLIENT");
    private String value;

    EnumRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
