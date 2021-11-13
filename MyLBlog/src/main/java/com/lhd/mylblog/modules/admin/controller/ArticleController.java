package com.lhd.mylblog.modules.admin.controller;


import com.lhd.mylblog.common.api.CommonResult;
import com.lhd.mylblog.common.api.ResultCode;
import com.lhd.mylblog.common.enums.ArticleStatus;
import com.lhd.mylblog.common.enums.UserRole;
import com.lhd.mylblog.modules.admin.dto.ArticleValueDto;
import com.lhd.mylblog.modules.admin.mapper.UserMapper;
import com.lhd.mylblog.modules.admin.model.Article;
import com.lhd.mylblog.modules.admin.model.Category;
import com.lhd.mylblog.modules.admin.model.Tag;
import com.lhd.mylblog.modules.admin.model.User;
import com.lhd.mylblog.modules.admin.service.ArticleService;
import com.lhd.mylblog.modules.admin.service.CategoryService;
import com.lhd.mylblog.modules.admin.service.TagService;
import com.lhd.mylblog.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserMapper userMapper;

    /**
     * 博文添加提交
     *
     * @param articleValueDto
     * @param request
     * @return
     */
    @ApiOperation(value = "博文添加提交", tags = {"article"}, notes = "post请求")
    @PostMapping(value = "/insert")
    public CommonResult insertArticle(@ApiParam(name = "articlevaluedto", required = true) @RequestBody ArticleValueDto articleValueDto,
                                      @ApiParam(name = "HttpServletRequest") HttpServletRequest request) {
        Article article = new Article();
        article.setArticleId(articleValueDto.getArticleId());
        article.setArticleTitle(articleValueDto.getArticleTitle());
        article.setArticleContent(articleValueDto.getArticleContent());
        article.setArticleThumbnail(articleValueDto.getArticleThumbnail());
        article.setArticleStatus(articleValueDto.getArticleStatus());
        article.setArticleOrder(articleValueDto.getArticleOrder());
        // userid
        Long id = jwtUtils.getUserIdFromToken(request);
        User user = userMapper.selectById(id);
        if(user != null) {
            article.setArticleUserId(id);
        }
        // 填充分类
        List<Category> categoryList = new ArrayList<>();
        if (articleValueDto.getArticleParentCategoryId() != null) {
            categoryList.add(new Category(articleValueDto.getArticleParentCategoryId()));
        }
        if (articleValueDto.getArticleChildCategoryId() != null) {
            categoryList.add(new Category(articleValueDto.getArticleChildCategoryId()));
        }
        article.setCategoryList(categoryList);
        // 填充标签
        List<Tag> tagList = new ArrayList<>();
        if(articleValueDto.getArticleTagIds() != null) {
            for (int i = 0; i < articleValueDto.getArticleTagIds().size(); ++i) {
                Tag tag = new Tag(articleValueDto.getArticleTagIds().get(i));
                tagList.add(tag);
            }
        }
        article.setTagList(tagList);
        articleService.insertArticle(article);
        return CommonResult.success(ResultCode.SUCCESS);
    }

    /**
     * 删除文章
     *
     * @param id
     * @param request
     */
    @ApiOperation(value = "删除文章", tags = {"article"}, notes = "post请求")
    @PostMapping(value = "/delete/{id}")
    public void deleteArticle(@ApiParam(name = "articleId", required = true) @PathVariable("id") Long id,
                              HttpServletRequest request) {
        Article dbArticle = articleService.getArticleByStatusAndId(null, id);
        if (dbArticle == null) {
            return;
        }
        User user = userMapper.selectById(jwtUtils.getUserIdFromToken(request));
        /*
        if (!Objects.equals(dbArticle.getArticleUserId(), user.getId())
            && !Objects.equals(user.getUserRole(), UserRole.ADMIN.getValue())) {
            // 如果不是管理员，访问其他用户的数据，则跳转403
            return;
        }
         */
        articleService.deleteArticle(id);
    }

    /**
     * 编辑文章提交
     *
     * @param articleValueDto
     * @param request
     * @return
     */
    @ApiOperation(value = "编辑文章提交", tags = {"article"}, notes = "post")
    @PostMapping(value = "/edit")
    public CommonResult editArticle(@ApiParam(name = "articleValueDto") @RequestBody ArticleValueDto articleValueDto,
                                    HttpServletRequest request) {
        Article dbArticle = articleService.getArticleByStatusAndId(null, articleValueDto.getArticleId());
        if (dbArticle == null) {
            return CommonResult.failed(ResultCode.VALIDATE_FAILED);
        }
        /*
        User user = userMapper.selectById(jwtUtils.getUserIdFromToken(request));
        if (!Objects.equals(dbArticle.getArticleUserId(), user.getId()) && !Objects.equals(user.getUserRole(), UserRole.ADMIN.getValue())) { // 如果不是管理员，访问其他用户的数据，则跳转403
            return CommonResult.failed(ResultCode.FORBIDDEN);
        }
         */
        Article article = new Article();
        article.setArticleThumbnail(articleValueDto.getArticleThumbnail());
        article.setArticleId(articleValueDto.getArticleId());
        article.setArticleTitle(articleValueDto.getArticleTitle());
        article.setArticleContent(articleValueDto.getArticleContent());
        article.setArticleStatus(articleValueDto.getArticleStatus());
        // 填充分类——方法：上找老下找小
        List<Category> categoryList = new ArrayList<>();
        if (articleValueDto.getArticleChildCategoryId() != null) {
            categoryList.add(new Category(articleValueDto.getArticleParentCategoryId()));
        }
        if (articleValueDto.getArticleChildCategoryId() != null) {
            categoryList.add(new Category(articleValueDto.getArticleChildCategoryId()));
        }
        article.setCategoryList(categoryList);
        // 填充标签——方法：便历标签表
        List<Tag> tagList = new ArrayList<>();
        if (articleValueDto.getArticleTagIds() != null) {
            for (int i = 0; i < articleValueDto.getArticleTagIds().size(); i++) {
                Tag tag = new Tag(articleValueDto.getArticleTagIds().get(i));
                tagList.add(tag);
            }
        }
        article.setTagList(tagList);
        articleService.updateArticleDetail(article);
        return CommonResult.success(ResultCode.SUCCESS);
    }

    /***
     * 点赞增加
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "点赞增加", tags = {"article"}, notes = "post")
    @PostMapping(value = "/article/like/{id}", produces = {"text/plain;charset=UTF-8"})
    public CommonResult increaseLikeCount(@PathVariable("id") Long id) {
        Article article = articleService.getArticleByStatusAndId(ArticleStatus.PUBLISHED.getValue(), id);
        Long articleCount = article.getArticleLikeCount() + 1;
        article.setArticleLikeCount(articleCount);
        articleService.updateArticle(article);
        return CommonResult.success(ResultCode.SUCCESS);
    }


    /**
     * 文章访问量数增加
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "文章访问量数增加", tags = {"article"}, notes = "post")
    @PostMapping(value = "/article/view/{id}", produces = {"text/plain;charset=UTF-8"})
    public CommonResult increaseViewCount(@PathVariable("id") Long id) {
        Article article = articleService.getArticleByStatusAndId(ArticleStatus.PUBLISHED.getValue(), id);
        Long articleCount = article.getArticleViews() + 1;
        article.setArticleViews(articleCount);
        articleService.updateArticle(article);
        return CommonResult.success(ResultCode.SUCCESS);
    }


}

