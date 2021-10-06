package com.lblog.blogbackend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@TableName("article_category_ass")
public class ArticleCategoryAssEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -6809206515467725995L;

    @TableId(value = "article_id", type = IdType.AUTO)
    @TableLogic
    private Integer articleId;

    @TableId(value = "category_id", type = IdType.AUTO)
    @TableLogic
    private Integer categoryId;

    public ArticleCategoryAssEntity() {
    }

    public ArticleCategoryAssEntity(Integer articleId, Integer categoryId) {
        this.articleId = articleId;
        this.categoryId = categoryId;
    }
}