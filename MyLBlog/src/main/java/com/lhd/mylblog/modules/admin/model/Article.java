package com.lhd.mylblog.modules.admin.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

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
@TableName("article")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 博文ID
     */
    @TableId(value = "article_id", type = IdType.AUTO)
    private Long articleId;

    /**
     * 发表用户ID
     */
    private Long articleUserId;

    /**
     * 博文标题
     */
    private String articleTitle;

    /**
     * 博文内容
     */
    private String articleContent;

    /**
     * 博文访问量
     */
    private Long articleViews;

    /**
     * 博文评论总数
     */
    private Long articleCommentCount;

    /**
     * 博文点赞数
     */
    private Long articleLikeCount;

    /**
     * 博文发表时间
     */
    private Date articleCreateTime;

    /**
     * 博文更新时间
     */
    private Date articleUpdateTime;

    /**
     * 是否允许评论
     */
    private Integer articleIsComment;

    /**
     * 缩略图
     */
    private String articleThumbnail;

    /**
     * 状态
     */
    private Integer articleStatus;

    /**
     * 排序值
     */
    private Integer articleOrder;

    /**
     * 博文删除时间
     */
    private Date articleDeletedTime;


    private User user;

    private List<Tag> tagList;

    private List<Category> categoryList;


}
