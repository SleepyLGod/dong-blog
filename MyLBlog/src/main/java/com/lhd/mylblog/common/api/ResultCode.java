package com.lhd.mylblog.common.api;


public enum ResultCode implements ErrorCode{

    /**
     *
     */
    SUCCESS(1, "操作成功"),
    FAILED(-1, "操作失败"),
    VALIDATE_FAILED(101, "参数检验失败"),
    UNAUTHORIZED(102, "暂未登录或token已经过期"),
    FORBIDDEN(103, "没有相关权限"),
    TOKEN_MISSING(104, "未检测到token"),
    USER_NOT_EXISTS(105, "用户不存在"),
    USER_ALREADY_EXISTS(106, "用户已存在"),
    EMAIL_ALREADY_EXISTS(107,"电子邮箱已存在"),
    TELE_ALREADY_EXISTS(108,"电话号码已存在"),
    CODE_INVALID(109,"密码错误"),
    USER_INVALID(110,"用户被封号"),
    ARTICLE_NOT_PUBLISHED(111, "草稿未发表");

    private final long code;
    private final String message;

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}