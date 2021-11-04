package com.lhd.mylblog.common.enums;

public enum CommentStatus {
    ALLOW(1, "允许"),

    NOT_ALLOWED(0, "不允许");

    CommentStatus(Integer value, String message) {
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
