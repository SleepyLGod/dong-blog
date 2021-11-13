package com.lhd.mylblog.modules.admin.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
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
@TableName("article_tag_ass")
public class ArticleTagAss implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 博文ID
     */

    @MppMultiId
    @TableField(value = "articleId")
    private Long articleId;

    /**
     * 标签ID
     */
    @MppMultiId
    @TableField(value = "tagId")
    private Long tagId;

    public ArticleTagAss() {
    }

    public ArticleTagAss(Long articleId, Long tagId) {
        this.articleId = articleId;
        this.tagId = tagId;
    }
}
