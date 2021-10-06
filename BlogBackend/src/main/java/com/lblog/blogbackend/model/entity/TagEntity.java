package com.lblog.blogbackend.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("tag")
public class TagEntity implements Serializable{
    @Serial
    private static final long serialVersionUID = 605449151900057035L;

    @TableId(value = "tag_id", type = IdType.AUTO)
    @TableLogic
    private Integer tagId;

    @TableField(value = "tag_name")
    @TableLogic
    private String tagName;

    @TableField(value = "tag_description")
    @TableLogic
    private String tagDescription;

    /**
     * 文章数量(不是数据库字段)
     */
    @TableLogic
    private Integer articleCount;

    @TableField(value = "tag_deleted_time")
    @TableLogic
    private Date tagDeletedTime;

    public TagEntity() {
    }

    public TagEntity(Integer tagId) {
        this.tagId = tagId;
    }

    public TagEntity(Integer tagId, String tagName, String tagDescription, Integer articleCount) {
        this.tagId = tagId;
        this.tagName = tagName;
        this.tagDescription = tagDescription;
        this.articleCount = articleCount;
    }
}
