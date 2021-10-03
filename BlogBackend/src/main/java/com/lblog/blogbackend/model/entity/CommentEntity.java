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

@Data
@Builder
@TableName("comment")
public class CommentEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -1038897351672911219L;

    @TableId(value = "comment_id", type = IdType.AUTO)
    private Integer commentId;

    @TableField(value = "comment_pid")
    private Integer commentPid;

    @TableField(value = "comment_pname")
    private String commentPname;

    @TableField(value = "comment_article_id")
    private Integer commentArticleId;

    @TableField(value = "comment_author_name")
    private String commentAuthorName;

    @TableField(value = "comment_author_email")
    private String commentAuthorEmail;

    @TableField(value = "comment_author_url")
    private String commentAuthorUrl;

    @TableField(value = "comment_author_avatar")
    private String commentAuthorAvatar;

    @TableField(value = "comment_content")
    private String commentContent;

    @TableField(value = "comment_agent")
    private String commentAgent;

    @TableField(value = "")
    private String commentIp;

    @TableField(value = "comment_IP")
    private Date commentCreateTime;

    /**
     * 角色(管理员1，访客0)
     */
    @TableField(value = "comment_role")
    private Integer commentRole;

    /**
     * 评论用户ID
     */
    @TableField(value = "comment_user_id")
    private Integer commentUserId;

    /**
     * 非数据库字段
     */
    private ArticleEntity article;
}
