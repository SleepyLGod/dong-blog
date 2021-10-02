package com.lblog.blogbackend.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@TableName("article_tag_ass")
public class ArticleTagAssEntity implements Serializable{
    @Serial
    private static final long serialVersionUID = -5816783232020910492L;

    @TableId(value = "article_id", type = IdType.AUTO)
    private Integer articleId;

    @TableId(value = "tag_id", type = IdType.AUTO)
    private Integer tagId;

    public ArticleTagAssEntity() {
    }

    public ArticleTagAssEntity(Integer articleId, Integer tagId) {
        this.articleId = articleId;
        this.tagId = tagId;
    }
}
