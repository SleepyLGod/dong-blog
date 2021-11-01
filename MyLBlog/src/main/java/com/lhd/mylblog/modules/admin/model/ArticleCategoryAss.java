package com.lhd.mylblog.modules.admin.model;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("article_category_ass")
public class ArticleCategoryAss implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 博文ID
     */
    private Long articleId;

    /**
     * 分类ID
     */
    private Long categoryId;

    public ArticleCategoryAss() {
    }

    public ArticleCategoryAss(Long articleId, Long categoryId) {
        this.articleId = articleId;
        this.categoryId = categoryId;
    }

}
