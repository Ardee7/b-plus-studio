package com.api.b_plus_studio.Enums;

import java.util.HashMap;
import java.util.Map;

public enum UserStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    BANNED("BANNED");
    private String type;

    UserStatus(String type) {
        this.type = type;
    }
}