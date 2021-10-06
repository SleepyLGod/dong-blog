package com.lblog.blogbackend.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@TableName("comment")
public class CommentEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -1038897351672911219L;

    @TableId(value = "comment_id", type = IdType.AUTO)
    @TableLogic
    private Integer commentId;

    @TableField(value = "comment_pid")
    @TableLogic
    private Integer commentPid;

    @TableField(value = "comment_pname")
    @TableLogic
    private String commentPname;

    @TableField(value = "comment_article_id")
    @TableLogic
    private Integer commentArticleId;

    @TableField(value = "comment_author_name")
    @TableLogic
    private String commentAuthorName;

    @TableField(value = "comment_author_email")
    @TableLogic
    private String commentAuthorEmail;

    @TableField(value = "comment_author_url")
    @TableLogic
    private String commentAuthorUrl;

    @TableField(value = "comment_author_avatar")
    @TableLogic
    private String commentAuthorAvatar;

    @TableField(value = "comment_content")
    @TableLogic
    private String commentContent;

    @TableField(value = "comment_agent")
    @TableLogic
    private String commentAgent;

    @TableField(value = "comment_IP")
    @TableLogic
    private String commentIp;

    @TableField(value = "comment_create_time")
    @TableLogic
    private Date commentCreateTime;

    /**
     * 角色(管理员1，访客0)
     */
    @TableField(value = "comment_role")
    @TableLogic
    private Integer commentRole;

    /**
     * 评论用户ID
     */
    @TableField(value = "comment_user_id")
    @TableLogic
    private Integer commentUserId;

    /**
     * 非数据库字段
     */
    @TableLogic
    private ArticleEntity article;

    @TableField(value = "comment_deleted_time")
    @TableLogic
    private Date commentDeletedTime;
}
