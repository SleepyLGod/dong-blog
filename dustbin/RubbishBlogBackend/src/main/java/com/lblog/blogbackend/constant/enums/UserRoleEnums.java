package com.lblog.blogbackend.constant.enums;

import java.security.SecureRandom;

public enum UserRoleEnums {

    USER("user", "普通用户"),

    ADMIN("admin", "管理员");

    UserRoleEnums(String value, String massage) {
        this.value = value;
        this.message = massage;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String value;

    private String message;

}
