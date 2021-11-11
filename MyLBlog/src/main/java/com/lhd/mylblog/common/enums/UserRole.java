package com.lhd.mylblog.common.enums;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserRole {

    USER("user", "普通用户"),
    ADMIN("admin", "管理员");

    UserRole(String value, String massage) {
        this.value = value;
        this.message = massage;
    }

    @EnumValue
    @JsonValue
    private String value;

    private String message;

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

    public static UserRole getByValue(final String value) {
        for(final UserRole p : UserRole.values()) {
            if(p.value == value) {
                return p;
            }
        }
        return null;
    }
}
