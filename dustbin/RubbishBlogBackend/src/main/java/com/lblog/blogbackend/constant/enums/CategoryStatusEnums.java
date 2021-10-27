package com.lblog.blogbackend.constant.enums;

public enum CategoryStatusEnums {

    NORMAL(1, "正常"),

    HIDDEN(0, "隐藏");

    CategoryStatusEnums(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private Integer value;

    private String message;
}
