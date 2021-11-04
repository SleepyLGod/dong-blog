package com.lhd.mylblog.common.enums;

public enum ArticleStatus {
    PUBLISHED(1, "已发布"),

    DRAFT(0, "草稿");

    ArticleStatus(Integer value, String message) {
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
