package com.lblog.blogbackend.Controller.Home;

import com.lblog.blogbackend.constant.enums.ArticleStatusEnums;
import com.lblog.blogbackend.model.entity.ArticleEntity;
import com.lblog.blogbackend.model.entity.TagEntity;
import com.lblog.blogbackend.service.ArticleService;
import com.lblog.blogbackend.service.TagService;
import com.lblog.blogbackend.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Controller
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private ArticleService articleService;

    /**
     * 根据标签查询文章
     *
     * @param tagId 标签ID
     * @return 模板
     */
    @RequestMapping("/tag/{tagId}")
    public String getArticleListByTag(@PathVariable("tagId") Integer tagId, Model model) {
        //该标签信息
        TagEntity tag = tagService.getTagById(tagId);
        if (tag == null) {
            return "redirect:/404";
        }
        model.addAttribute("tag", tag);

        //文章列表
        HashMap<String, Object> criteria = new HashMap<>(2);
        criteria.put("tagId", tagId);
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
        model.addAttribute("pageUrlPrefix", "/tag/"+tagId+"?pageIndex");

        return "Home/Page/articleListByTag";
    }

}