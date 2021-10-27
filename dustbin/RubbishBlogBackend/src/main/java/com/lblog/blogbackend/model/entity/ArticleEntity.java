package com.lblog.blogbackend.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
//@Builder
@TableName("article")
public class ArticleEntity implements Serializable{

    @Serial
    private static final long serialVersionUID = 5207865247400761539L;

    @TableId(value = "article_id", type = IdType.AUTO)
    @TableLogic
    private Integer articleId;

    @TableField(value = "article_user_id")
    @TableLogic
    private Integer articleUserId;

    @TableField(value = "article_title")
    @TableLogic
    private String articleTitle;

    @TableField(value = "article_views")
    @TableLogic
    private Integer articleViews;

    @TableField(value = "article_comment_count")
    @TableLogic
    private Integer articleCommentCount;

    @TableField(value = "article_like_count")
    @TableLogic
    private Integer articleLikeCount;

    @TableField(value = "article_create_time")
    @TableLogic
    private Date articleCreateTime;

    @TableField(value = "article_update_time")
    @TableLogic
    private Date articleUpdateTime;

    @TableField(value = "article_is_comment")
    @TableLogic
    private Integer articleIsComment;

    @TableField(value = "article_status")
    @TableLogic
    private Integer articleStatus;

    @TableField(value = "article_order")
    @TableLogic
    private Integer articleOrder;

    @TableField(value = "article_content")
    @TableLogic
    private String articleContent;

    @TableField(value = "article_thumbnail")
    @TableLogic
    private String articleThumbnail;

    @TableField(value = "article_deleted_time")
    @TableLogic
    private Date articleDeletedTime;

    @TableLogic
    private UserEntity user;

    @TableLogic
    private List<TagEntity> tagList;

    @TableLogic
    private List<CategoryEntity> categoryList;

}
