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
@TableName("tag")
public class TagEntity implements Serializable{
    @Serial
    private static final long serialVersionUID = 605449151900057035L;

    @TableId(value = "tag_id", type = IdType.AUTO)
    private Integer tagId;

    @TableField(value = "tag_name")
    private String tagName;

    @TableField(value = "tag_description")
    private String tagDescription;

    /**
     * 文章数量(不是数据库字段)
     */
    private Integer articleCount;

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
