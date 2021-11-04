package com.lhd.mylblog.modules.admin.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Api
@ApiModel(value = "userLoginDto", description = "用户登录所需上传数据")
public class UserLoginDto {

    @ApiModelProperty(value = "username", required = false, dataType = "String")
    private String username;

    @NotBlank
    @ApiModelProperty(value = "userpassword", required = true, dataType = "String")
    private String password;

    @ApiModelProperty(value = "useremail", required = false, dataType = "String")
    private String email;

    @ApiModelProperty(value = "usertele", required = false, dataType = "String")
    private String tele;

}
