package com.vallet.ms_user.enums;

public enum CreatorType {
    USER("USER"),
    ADMIN("ADMIN"),
    SYSTEM("SYSTEM");

    private final String type;

    CreatorType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type;
    }

}
