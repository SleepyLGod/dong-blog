package com.lblog.blogbackend.model.DTO;

import lombok.Data;

import java.util.List;

@Data
public class ArticleValueDTO {

    private Integer articleId;

    private String articleTitle;

    private String articleContent;

    private Integer articleParentCategoryId;

    private Integer articleChildCategoryId;

    private Integer articleOrder;

    private Integer articleStatus;

    private String articleThumbnail;

    private List<Integer> articleTagIds;
}
