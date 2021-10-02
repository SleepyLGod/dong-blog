package com.lblog.blogbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    private Integer selfId;

    @TableField(value = "self_name")
    private String selfName;

    @TableField(value = "self_url")
    private String selfUrl;

    @TableField(value = "self_level")
    private Integer selfLevel;

    @TableField(value = "self_icon")
    private String selfIcon;

    @TableField(value = "self_order")
    private Integer selfOrder;

}
