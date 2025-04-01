package com.api.b_plus_studio.enums;

public enum Role {
    ADMIN("ADMIN"),
    SUPER_ADMIN("SUPER_ADMIN"),
    PARTNER("PARTNER"),
    USER("USER"),
    GUEST("GUEST");
    private String type;

    Role(String type) {
        this.type = type;
    }
}
