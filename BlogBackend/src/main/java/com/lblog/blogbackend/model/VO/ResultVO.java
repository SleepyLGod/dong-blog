package com.lblog.blogbackend.model.VO;

import lombok.Data;

@Data
public class ResultVO<T> {

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String tip;

    /**
     * 返回的具体内容
     */
    private T data;


    public ResultVO(Integer code, String tip, T data) {
        this.code = code;
        this.tip = tip;
        this.data = data;
    }

    public ResultVO() {
    }
}
