package com.lblog.blogbackend.model.DTO;

import lombok.Data;

@Data
public class JsonReturnDTO<T> {

    public Integer getIsFailed() {
        return isFailed;
    }

    public void setIsFailed(Integer isFailed) {
        this.isFailed = isFailed;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private Integer isFailed;

    private String tip;

    private T data;

    public JsonReturnDTO(Integer isFailed, String tip, T data) {
        this.isFailed = isFailed;
        this.tip = tip;
        this.data = data;
    }

    public JsonReturnDTO() {}

    public JsonReturnDTO fail() {
        return new JsonReturnDTO(1, "操作失败", null);
    }

    public JsonReturnDTO fail(String tip) {
        return new JsonReturnDTO(1, tip, null);
    }

    public JsonReturnDTO success() {
        return new JsonReturnDTO(0, "操作成功", null);
    }

    public JsonReturnDTO success(T data) {
        return new JsonReturnDTO(0, "操作成功", data);
    }

}
