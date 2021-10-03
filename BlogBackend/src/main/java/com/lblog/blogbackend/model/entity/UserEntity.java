package com.lblog.blogbackend.model.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@TableName("user")
public class UserEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -4415517704211731385L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer userId;

    @TableField(value = "name")
    private String userName;

    @TableField(value = "ip")
    private String userIp;

    @TableField(value = "password")
    private String userPass;

    @TableField(value = "nickname")
    private String userNickname;

    @TableField(value = "email")
    private String userEmail;

    @TableField(value = "url")
    private String userUrl;

    @TableField(value = "avatar")
    private String userAvatar;

    @TableField(value = "register_time")
    private Date userRegisterTime;

    @TableField(value = "last_login_time")
    private Date userLastLoginTime;

    @TableField(value = "status")
    private Integer userStatus;

    /**
     * 用户角色：admin/user
     */
    @TableField(value = "role")
    private String userRole;

    @TableField(value = "birth")
    private LocalDate userBirth;
    // LocalDate localDate=now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    @TableField(value = "age")
    private Integer userAge;

    @TableField(value = "tele")
    private String userTele;

    /**
     * 文章数量（不是数据库字段）
     */
    private Integer articleCount;
}
