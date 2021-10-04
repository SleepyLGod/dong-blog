package com.lblog.blogbackend.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@TableName("personal_info")
public class PersonalInfoEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 489914127235951698L;

    @TableId(value = "self_id", type = IdType.AUTO)
    @TableLogic
    private Integer selfId;

    @TableField(value = "self_name")
    @TableLogic
    private String selfName;

    @TableField(value = "self_url")
    @TableLogic
    private String selfUrl;

    @TableField(value = "self_level")
    @TableLogic
    private Integer selfLevel;

    @TableField(value = "self_icon")
    @TableLogic
    private String selfIcon;

    @TableField(value = "self_order")
    @TableLogic
    private Integer selfOrder;

}
