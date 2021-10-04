package com.lblog.blogbackend.Controller.Home;

import com.lblog.blogbackend.constant.enums.ArticleStatusEnums;
import com.lblog.blogbackend.model.entity.ArticleEntity;
import com.lblog.blogbackend.model.entity.CategoryEntity;
import com.lblog.blogbackend.model.entity.TagEntity;
import com.lblog.blogbackend.service.ArticleService;
import com.lblog.blogbackend.service.CategoryService;
import com.lblog.blogbackend.service.TagService;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private TagService tagService;

    /**
     * 根据分类查询文章
     *
     * @param cateId 分类ID
     * @return 模板
     */
    @RequestMapping("/category/{cateId}")
    public String getArticleListByCategory(@PathVariable("cateId") Integer cateId, Model model) {

        //该分类信息
        CategoryEntity category = categoryService.getCategoryById(cateId);
        if (category == null) {
            return "redirect:/404";
        }
        model.addAttribute("category", category);

        //文章列表
        HashMap<String, Object> criteria = new HashMap<>(2);
        criteria.put("categoryId", cateId);
        criteria.put("status", ArticleStatusEnums.PUBLISHED.getValue());

        //侧边栏
        //标签列表显示
        List<TagEntity> allTagList = tagService.listTag();
        model.addAttribute("allTagList", allTagList);
        //获得随机文章
        List<ArticleEntity> randomArticleList = articleService.listRandomArticle(8);
        model.addAttribute("randomArticleList", randomArticleList);
        //获得热评文章
        List<ArticleEntity> mostCommentArticleList = articleService.listArticleByCommentCount(8);
        model.addAttribute("mostCommentArticleList", mostCommentArticleList);
        model.addAttribute("pageUrlPrefix", "/category/"+cateId+"?pageIndex");
        return "Home/Page/articleListByCategory";
    }


}