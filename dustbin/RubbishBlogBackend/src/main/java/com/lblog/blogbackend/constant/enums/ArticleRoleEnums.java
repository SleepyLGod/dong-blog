package com.lblog.blogbackend.constant.enums;

public enum ArticleRoleEnums {

    OWNER(1, "博主"),
    VISITOR(0, "其他用户");

    private Integer value;

    private String message;

    ArticleRoleEnums(Integer value, String message) {
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

}
