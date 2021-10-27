package com.lblog.blogbackend.Controller.Home;

import com.alibaba.fastjson.JSON;
import com.lblog.blogbackend.constant.enums.ArticleStatusEnums;
import com.lblog.blogbackend.model.entity.ArticleEntity;
import com.lblog.blogbackend.model.entity.CommentEntity;
import com.lblog.blogbackend.model.entity.TagEntity;
import com.lblog.blogbackend.model.entity.UserEntity;
import com.lblog.blogbackend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CategoryService categoryService;


    /*
    // 文章详情页显示
    @RequestMapping(value = "/article/{articleId}")
    public String getArticleDetailPage(@PathVariable("articleId") Integer articleId, Model model) {

        //文章信息，分类，标签，作者，评论
        ArticleEntity article = articleService.getArticleByStatusAndId(ArticleStatusEnums.PUBLISHED.getValue(), articleId);
        if (article == null) {
            return "Home/Error/404";
        }
        model.addAttribute("article", article);
        UserEntity user = userService.getUserById(article.getArticleUserId());
        article.setUser(user);
        List<CommentEntity> commentList = commentService.listCommentByArticleId(articleId);
        model.addAttribute("commentList", commentList);
        List<Integer> categoryIds = articleService.listCategoryIdByArticleId(articleId);
        List<ArticleEntity> similarArticleList = articleService.listArticleByCategoryIds(categoryIds, 5);
        model.addAttribute("similarArticleList", similarArticleList);
        //猜你喜欢
        List<ArticleEntity> mostViewArticleList = articleService.listArticleByViewCount(5);
        model.addAttribute("mostViewArticleList", mostViewArticleList);
        ArticleEntity afterArticle = articleService.getAfterArticle(articleId);
        model.addAttribute("afterArticle", afterArticle);
        ArticleEntity preArticle = articleService.getPreArticle(articleId);
        model.addAttribute("preArticle", preArticle);
        List<TagEntity> allTagList = tagService.listTag();
        model.addAttribute("allTagList", allTagList);
        //获得随机文章
        List<ArticleEntity> randomArticleList = articleService.listRandomArticle(8);
        model.addAttribute("randomArticleList", randomArticleList);
        //获得热评文章
        List<ArticleEntity> mostCommentArticleList = articleService.listArticleByCommentCount(8);
        model.addAttribute("mostCommentArticleList", mostCommentArticleList);
        return "Home/Page/articleDetail";
    }
     */

    // 点赞增加
    @RequestMapping(value = "/article/like/{id}", method = {RequestMethod.POST}, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String increaseLikeCount(@PathVariable("id") Integer id) {
        ArticleEntity article = articleService.getArticleByStatusAndId(ArticleStatusEnums.PUBLISHED.getValue(), id);
        Integer articleCount = article.getArticleLikeCount() + 1;
        article.setArticleLikeCount(articleCount);
        articleService.updateArticle(article);
        return JSON.toJSONString(articleCount);
    }

    // 文章访问量数增加
    @RequestMapping(value = "/article/view/{id}", method = {RequestMethod.POST}, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String increaseViewCount(@PathVariable("id") Integer id) {
        ArticleEntity article = articleService.getArticleByStatusAndId(ArticleStatusEnums.PUBLISHED.getValue(), id);
        Integer articleCount = article.getArticleViews() + 1;
        article.setArticleViews(articleCount);
        articleService.updateArticle(article);
        return JSON.toJSONString(articleCount);
    }


}