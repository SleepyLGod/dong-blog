package com.lblog.blogbackend.model.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
@TableName("user")
public class UserEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -4415517704211731385L;

    @TableId(value = "id", type = IdType.AUTO)
    @TableLogic
    private Integer userId;

    @TableField(value = "user_name")
    @TableLogic
    private String userName;

    @TableField(value = "ip")
    @TableLogic
    private String userIp;

    @TableField(value = "password")
    @TableLogic
    private String userPass;

    @TableField(value = "nickname")
    @TableLogic
    private String userNickname;

    @TableField(value = "email")
    @TableLogic
    private String userEmail;

    @TableField(value = "url")
    @TableLogic
    private String userUrl;

    @TableField(value = "avatar")
    @TableLogic
    private String userAvatar;

    @TableField(value = "register_time")
    @TableLogic
    private Date userRegisterTime;

    @TableField(value = "last_login_time")
    @TableLogic
    private Date userLastLoginTime;

    @TableField(value = "status")
    @TableLogic
    private Integer userStatus;

    /**
     * 用户角色：admin/user
     */
    @TableField(value = "user_role")
    @TableLogic
    private String userRole;

    @TableField(value = "birth")
    @TableLogic
    private LocalDate userBirth;
    // LocalDate localDate=now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    @TableField(value = "age")
    @TableLogic
    private Integer userAge;

    @TableField(value = "tele")
    @TableLogic
    private String userTele;

    /**
     * 文章数量（不是数据库字段）
     */
    @TableLogic
    private Integer articleCount;

}
