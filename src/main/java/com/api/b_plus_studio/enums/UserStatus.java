package com.api.b_plus_studio.enums;

public enum UserStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    BANNED("BANNED");
    private String type;

    UserStatus(String type) {
        this.type = type;
    }
}