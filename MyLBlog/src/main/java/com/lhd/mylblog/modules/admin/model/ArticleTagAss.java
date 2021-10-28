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
@TableName("article_tag_ass")
public class ArticleTagAss implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 博文ID
     */
    private Long articleId;

    /**
     * 标签ID
     */
    private Long tagId;


}
