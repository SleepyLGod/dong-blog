package com.lhd.mylblog.modules.admin.model;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author generator
 * @since 2021-10-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user")
@Api(value = "user-entity",tags = {"user"})
@ApiModel(value = "userEntity", description = "用户实体，存储用户相关字段")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "user id", dataType = "Long")
    private Long id;

    /**
     * 上传登录IP
     */
    @ApiModelProperty(value = "user ip", dataType = "String")
    private String ip;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "user name", dataType = "String")
    private String userName;

    /**
     * 用户密码
     */
    @ApiModelProperty(value = "user password", dataType = "String")
    private String password;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "nickname", dataType = "String")
    private String nickname;

    /**
     * 用户邮箱
     */
    @ApiModelProperty(value = "usere mail", dataType = "String")
    private String email;

    /**
     * 用户头像
     */
    @ApiModelProperty(value = "user avatar", dataType = "String")
    private String avatar;

    /**
     * 注册时间
     */
    @ApiModelProperty(value = "user register time", dataType = "Date")
    private Date registerTime;

    /**
     * 上传登录时间
     */
    @ApiModelProperty(value = "user last login time", dataType = "Date")
    private Date lastLoginTime;

    /**
     * 状态
     */
    @ApiModelProperty(value = "user status", dataType = "Integar")
    private Integer status;

    /**
     * 角色
     */
    @ApiModelProperty(value = "user role：admin or user", dataType = "String")
    private String userRole;

    /**
     * 用户生日
     */
    @ApiModelProperty(value = "user birth", dataType = "Date")
    private Date birth;

    /**
     * 用户年龄
     */
    @ApiModelProperty(value = "user age", dataType = "Integar")
    private Integer age;

    /**
     * 用户手机号
     */
    @ApiModelProperty(value = "user tele", dataType = "String")
    private String tele;

    /**
     * 注销时间
     */
    @ApiModelProperty(value = "user deleted time (for logic deleting)", dataType = "Date", hidden = true)
    private Date userDeletedTime;

    /**
     * 文章数量（不是数据库字段）
     */
    @ApiModelProperty(value = "user's article count", dataType = "Integar", hidden = true)
    private Integer articleCount;

//    /**
//     * 本次登陆时间（不是数据库字段）
//     */
//    private Date loginTime;
}
