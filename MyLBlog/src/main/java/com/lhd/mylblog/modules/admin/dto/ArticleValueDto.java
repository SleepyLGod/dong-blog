package com.lhd.mylblog.modules.admin.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Api
@ApiModel(value = "articleValueDto", description = "博文添加提交所需上传的数据")
public class ArticleValueDto {
    @ApiModelProperty(value = "articleid", required = true, dataType = "Long")
    private Long articleId;

    @ApiModelProperty(value = "articletitle", required = true, dataType = "String")
    private String articleTitle;

    @ApiModelProperty(value = "articlecontent", required = true, dataType = "String")
    private String articleContent;

    @ApiModelProperty(value = "articleParentCategoryId", required = false, dataType = "Long")
    private Long articleParentCategoryId;

    @ApiModelProperty(value = "articleChildCategoryId", required = false, dataType = "Long")
    private Long articleChildCategoryId;

    @ApiModelProperty(value = "articleOrder", required = false, dataType = "Integer")
    private Integer articleOrder;

    @ApiModelProperty(value = "articleStatus, published or draft?", required = true, dataType = "Integer")
    private Integer articleStatus;

    @ApiModelProperty(value = "缩略图", required = false, dataType = "String")
    private String articleThumbnail;

//    @ApiModelProperty(value = )
//    private String articleDeletedTime;

    @ApiModelProperty(value = "博文所关联的标签", required = false, dataType = "List<Long>")
    private List<Long> articleTagIds;
}
