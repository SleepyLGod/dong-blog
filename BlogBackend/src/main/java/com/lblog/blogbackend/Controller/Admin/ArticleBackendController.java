package com.lblog.blogbackend.Controller.Admin;

import cn.hutool.http.HtmlUtil;

import com.lblog.blogbackend.constant.enums.UserRoleEnums;
import com.lblog.blogbackend.model.DTO.ArticleValueDTO;
import com.lblog.blogbackend.model.DTO.JsonReturnDTO;
import com.lblog.blogbackend.model.entity.ArticleEntity;
import com.lblog.blogbackend.model.entity.CategoryEntity;
import com.lblog.blogbackend.model.entity.TagEntity;
import com.lblog.blogbackend.model.entity.UserEntity;
import com.lblog.blogbackend.service.ArticleService;
import com.lblog.blogbackend.service.CategoryService;
import com.lblog.blogbackend.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin/article")
public class ArticleBackendController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CategoryService categoryService;

/*
    // 后台文章列表显示
    @RequestMapping(value = "")
    public String index(@RequestParam(required = false) String status, Model model, HttpSession session) {
        HashMap<String, Object> criteria = new HashMap<>(1);
        UserEntity user = (UserEntity) session.getAttribute("user");
        if (!UserRoleEnums.ADMIN.getValue().equals(user.getUserRole())) {
            // 用户查询自己的文章, 管理员查询所有的
            criteria.put("userId", user.getUserId());
        }
        return "Admin/Article/index";
    }

    // 后台添加文章页面显示
    @RequestMapping(value = "/insert")
    public String insertArticleView(Model model) {
        List<CategoryEntity> categoryList = categoryService.listCategory();
        List<TagEntity> tagList = tagService.listTag();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("tagList", tagList);
        return "Admin/Article/insert";
    }
*/

    // 后台添加文章提交操作
    @RequestMapping(value = "/insertSubmit", method = RequestMethod.POST)
    public JsonReturnDTO insertArticleSubmit(HttpSession session, ArticleValueDTO articleParam) {
        ArticleEntity article = new ArticleEntity();
        //用户ID
        UserEntity user = (UserEntity) session.getAttribute("user");
        if (user != null) {
            article.setArticleUserId(user.getUserId());
        }
        article.setArticleTitle(articleParam.getArticleTitle());
        article.setArticleThumbnail(articleParam.getArticleThumbnail());
        article.setArticleContent(articleParam.getArticleContent());
        article.setArticleStatus(articleParam.getArticleStatus());
        //填充分类
        List<CategoryEntity> categoryList = new ArrayList<>();
        if (articleParam.getArticleChildCategoryId() != null) {
            categoryList.add(new CategoryEntity(articleParam.getArticleParentCategoryId()));
        }
        if (articleParam.getArticleChildCategoryId() != null) {
            categoryList.add(new CategoryEntity(articleParam.getArticleChildCategoryId()));
        }
        article.setCategoryList(categoryList);
        //填充标签
        List<TagEntity> tagList = new ArrayList<>();
        if (articleParam.getArticleTagIds() != null) { // 用户查询自己的文章, 管理员查询所有的
            for (int i = 0; i < articleParam.getArticleTagIds().size(); i++) {
                TagEntity tag = new TagEntity(articleParam.getArticleTagIds().get(i));
                tagList.add(tag);
            }
        }
        article.setTagList(tagList);
        articleService.insertArticle(article);
        return new JsonReturnDTO().success("添加文章");
        // return "redirect:/admin/article";
    }

    // 后台添加草稿提交操作
    @RequestMapping(value = "/insertDraftSubmit", method = RequestMethod.POST)
    public JsonReturnDTO insertDraftSubmit(HttpSession session, ArticleValueDTO articleParam) {
        ArticleEntity article = new ArticleEntity();
        //用户ID
        UserEntity user = (UserEntity) session.getAttribute("user");
        if (user != null) {
            article.setArticleUserId(user.getUserId());
        }
        article.setArticleTitle(articleParam.getArticleTitle());
        article.setArticleThumbnail(articleParam.getArticleThumbnail());
        article.setArticleContent(articleParam.getArticleContent());
        article.setArticleStatus(articleParam.getArticleStatus());
        //填充分类
        List<CategoryEntity> categoryList = new ArrayList<>();
        if (articleParam.getArticleChildCategoryId() != null) {
            categoryList.add(new CategoryEntity(articleParam.getArticleParentCategoryId()));
        }
        if (articleParam.getArticleChildCategoryId() != null) {
            categoryList.add(new CategoryEntity(articleParam.getArticleChildCategoryId()));
        }
        article.setCategoryList(categoryList);
        //填充标签
        List<TagEntity> tagList = new ArrayList<>();
        if (articleParam.getArticleTagIds() != null) {
            for (int i = 0; i < articleParam.getArticleTagIds().size(); i++) {
                TagEntity tag = new TagEntity(articleParam.getArticleTagIds().get(i));
                tagList.add(tag);
            }
        }
        article.setTagList(tagList);
        articleService.insertArticle(article);
        return new JsonReturnDTO().success("添加草稿");
        // return "redirect:/admin";
    }

    // 删除文章
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public void deleteArticle(@PathVariable("id") Integer id, HttpSession session) {
        ArticleEntity dbArticle = articleService.getArticleByStatusAndId(null, id);
        if (dbArticle == null) {
            return;
        }
        UserEntity user = (UserEntity) session.getAttribute("user");
        if (!Objects.equals(dbArticle.getArticleUserId(), user.getUserId()) && !Objects.equals(user.getUserRole(), UserRoleEnums.ADMIN.getValue())) { // 如果不是管理员，访问其他用户的数据，则跳转403
            return;
        }
        articleService.deleteArticle(id);
    }

    // 编辑文章页面显示
    @RequestMapping(value = "/edit/{id}")
    public JsonReturnDTO editArticleView(@PathVariable("id") Integer id, Model model, HttpSession session) {
        ArticleEntity article = articleService.getArticleByStatusAndId(null, id);
        if (article == null) {
            return new JsonReturnDTO().fail("404");
            // return "redirect:/404";
        }
        UserEntity user = (UserEntity) session.getAttribute("user");
        if (!Objects.equals(article.getArticleUserId(), user.getUserId()) && !Objects.equals(user.getUserRole(), UserRoleEnums.ADMIN.getValue())) { // 如果不是管理员，访问其他用户的数据，则跳转403
            return new JsonReturnDTO().fail("403");
            // return "redirect:/403";
        }
        model.addAttribute("article", article);
        List<CategoryEntity> categoryList = categoryService.listCategory();
        model.addAttribute("categoryList", categoryList);
        List<TagEntity> tagList = tagService.listTag();
        model.addAttribute("tagList", tagList);
        return new JsonReturnDTO().success();
        // return "Admin/Article/edit";
    }

    // 编辑文章提交
    @RequestMapping(value = "/editSubmit", method = RequestMethod.POST)
    public JsonReturnDTO editArticleSubmit(ArticleValueDTO articleParam, HttpSession session) {
        ArticleEntity dbArticle = articleService.getArticleByStatusAndId(null, articleParam.getArticleId());
        if (dbArticle == null) {
            return new JsonReturnDTO().fail("404");
            // return "redirect:/404";
        }
        UserEntity user = (UserEntity) session.getAttribute("user");
        if (!Objects.equals(dbArticle.getArticleUserId(), user.getUserId()) && !Objects.equals(user.getUserRole(), UserRoleEnums.ADMIN.getValue())) { // 如果不是管理员，访问其他用户的数据，则跳转403
            return new JsonReturnDTO().fail("403");
            // return "redirect:/403";
        }
        ArticleEntity article = new ArticleEntity();
        article.setArticleThumbnail(articleParam.getArticleThumbnail());
        article.setArticleId(articleParam.getArticleId());
        article.setArticleTitle(articleParam.getArticleTitle());
        article.setArticleContent(articleParam.getArticleContent());
        article.setArticleStatus(articleParam.getArticleStatus());
        // 填充分类——方法：上找老下找小
        List<CategoryEntity> categoryList = new ArrayList<>();
        if (articleParam.getArticleChildCategoryId() != null) {
            categoryList.add(new CategoryEntity(articleParam.getArticleParentCategoryId()));
        }
        if (articleParam.getArticleChildCategoryId() != null) {
            categoryList.add(new CategoryEntity(articleParam.getArticleChildCategoryId()));
        }
        article.setCategoryList(categoryList);
        //填充标签——方法：便历标签表
        List<TagEntity> tagList = new ArrayList<>();
        if (articleParam.getArticleTagIds() != null) {
            for (int i = 0; i < articleParam.getArticleTagIds().size(); i++) {
                TagEntity tag = new TagEntity(articleParam.getArticleTagIds().get(i));
                tagList.add(tag);
            }
        }
        article.setTagList(tagList);
        articleService.updateArticleDetail(article);
        return new JsonReturnDTO().success();
        // return "redirect:/admin/article";
    }

}

