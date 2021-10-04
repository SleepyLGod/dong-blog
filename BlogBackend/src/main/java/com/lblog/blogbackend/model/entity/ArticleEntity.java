package com.lblog.blogbackend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName("article")
public class ArticleEntity implements Serializable{

    @Serial
    private static final long serialVersionUID = 5207865247400761539L;

    @TableId(value = "article_id", type = IdType.AUTO)
    private Integer articleId;

    @TableField(value = "article_user_id")
    private Integer articleUserId;

    @TableField(value = "article_title")
    private String articleTitle;

    @TableField(value = "article_views")
    private Integer articleViews;

    @TableField(value = "article_comment_count")
    private Integer articleCommentCount;

    @TableField(value = "article_like_count")
    private Integer articleLikeCount;

    @TableField(value = "article_create_time")
    private Date articleCreateTime;

    @TableField(value = "article_update_time")
    private Date articleUpdateTime;

    @TableField(value = "article_is_comment")
    private Integer articleIsComment;

    @TableField(value = "article_status")
    private Integer articleStatus;

    @TableField(value = "article_order")
    private Integer articleOrder;

    @TableField(value = "article_content")
    private String articleContent;

    @TableField(value = "article_thumbnail")
    private String articleThumbnail;

    private UserEntity user;

    private List<TagEntity> tagList;

    private List<CategoryEntity> categoryList;

}
