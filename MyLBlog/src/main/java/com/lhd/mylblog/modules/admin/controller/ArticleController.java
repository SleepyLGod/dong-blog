package com.lhd.mylblog.modules.admin.controller;


import com.lhd.mylblog.modules.admin.dto.ArticleValueDto;
import com.lhd.mylblog.modules.admin.service.ArticleService;
import com.lhd.mylblog.modules.admin.service.CategoryService;
import com.lhd.mylblog.modules.admin.service.TagService;
import io.micrometer.core.instrument.Tags;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author generator
 * @since 2021-10-28
 */
@RestController
@Api(value = "article-controller", tags = {"user"})
@RequestMapping("/admin/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "博文添加提交", tags = {"article"}, notes = "post请求")
    @PostMapping(value = "/insert")
    public CommentController insertArticle(@ApiParam(name = "articlevaluedto", required = true) @RequestBody ArticleValueDto articleValueDto) {

    }

}

