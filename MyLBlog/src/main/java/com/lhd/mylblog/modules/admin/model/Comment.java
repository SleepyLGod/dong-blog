package com.lhd.mylblog.modules.admin.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author generator
 * @since 2021-10-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评论ID
     */
    @TableId(value = "comment_id", type = IdType.AUTO)
    private Long commentId;

    /**
     * 上级评论ID
     */
    private Long commentPid;

    /**
     * 上级评论名称
     */
    private String commentPname;

    /**
     * 文章ID
     */
    private Long commentArticleId;

    /**
     * 评论人名称
     */
    private String commentAuthorName;

    /**
     * 评论人邮箱
     */
    private String commentAuthorEmail;


    /**
     * 评论人头像
     */
    private String commentAuthorAvatar;

    /**
     * 内容
     */
    private String commentContent;

    /**
     * 浏览器信息
     */
    private String commentAgent;

    /**
     * IP
     */
    @TableField("comment_IP")
    private String commentIp;

    /**
     * 评论时间
     */
    private Date commentCreateTime;

    /**
     * 角色，是否管理员
     */
    private Integer commentRole;

    /**
     * 评论ID,可能为空
     */
    private Long commentUserId;

    /**
     * 非数据库字段
     */
    private Article article;

}
