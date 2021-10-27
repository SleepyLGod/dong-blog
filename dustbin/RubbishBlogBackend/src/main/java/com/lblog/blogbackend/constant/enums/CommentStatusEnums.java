package com.lblog.blogbackend.constant.enums;

public enum CommentStatusEnums {

    ALLOW(1, "允许"),

    NOT_ALLOWED(0, "不允许");

    CommentStatusEnums(Integer value, String message) {
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
