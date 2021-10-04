package com.lblog.blogbackend.Controller.Admin;

import com.lblog.blogbackend.model.entity.TagEntity;
import com.lblog.blogbackend.service.ArticleService;
import com.lblog.blogbackend.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin/tag")
public class TagBackendController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private TagService tagService;

    /**
     * 后台标签列表显示
     * @return
     */
    @RequestMapping(value = "")
    public ModelAndView index()  {
        ModelAndView modelandview = new ModelAndView();
        List<TagEntity> tagList = tagService.listTagWithCount();
        modelandview.addObject("tagList",tagList);

        modelandview.setViewName("Admin/Tag/index");
        return modelandview;

    }


    /**
     * 后台添加分类页面显示
     *
     * @param tag
     * @return
     */
    @RequestMapping(value = "/insertSubmit",method = RequestMethod.POST)
    public String insertTagSubmit(TagEntity tag)  {
        tagService.insertTag(tag);
        return "redirect:/admin/tag";
    }

    /**
     * 删除标签
     *
     * @param id 标签ID
     * @return
     */
    @RequestMapping(value = "/delete/{id}")
    public String deleteTag(@PathVariable("id") Integer id)  {
        Integer count = articleService.countArticleByTagId(id);
        if (count == 0) {
            tagService.deleteTag(id);
        }
        return "redirect:/admin/tag";
    }

    /**
     * 编辑标签页面显示
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/edit/{id}")
    public ModelAndView editTagView(@PathVariable("id") Integer id)  {
        ModelAndView modelAndView = new ModelAndView();

        TagEntity tag =  tagService.getTagById(id);
        modelAndView.addObject("tag",tag);

        List<TagEntity> tagList = tagService.listTagWithCount();
        modelAndView.addObject("tagList",tagList);

        modelAndView.setViewName("Admin/Tag/edit");
        return modelAndView;
    }


    /**
     * 编辑标签提交
     *
     * @param tag
     * @return
     */
    @RequestMapping(value = "/editSubmit",method = RequestMethod.POST)
    public String editTagSubmit(TagEntity tag)  {
        tagService.updateTag(tag);
        return "redirect:/admin/tag";
    }
}
