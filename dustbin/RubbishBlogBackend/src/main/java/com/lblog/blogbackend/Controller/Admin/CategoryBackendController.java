package com.lblog.blogbackend.Controller.Admin;

import com.lblog.blogbackend.model.DTO.JsonReturnDTO;
import com.lblog.blogbackend.model.entity.CategoryEntity;
import com.lblog.blogbackend.service.ArticleService;
import com.lblog.blogbackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin/category")
public class CategoryBackendController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    /*
    // 分类列表显示
    @RequestMapping(value = "")
    public ModelAndView categoryList() {
        ModelAndView modelandview = new ModelAndView();
        List<CategoryEntity> categoryList = categoryService.listCategoryWithCount();
        modelandview.addObject("categoryList",categoryList);
        modelandview.setViewName("Admin/Category/index");
        return modelandview;
    }
     */

    // 添加分类
    @RequestMapping(value = "/insertSubmit",method = RequestMethod.POST)
    public JsonReturnDTO insertCategorySubmit(CategoryEntity category)  {
        categoryService.insertCategory(category);
        List<CategoryEntity> categoryList = categoryService.listCategoryWithCount();
        String result = categoryList.toString();
        System.out.println(result);
        return new JsonReturnDTO().success();
        // return "redirect:/admin/category";
    }

    // 删除分类
    @RequestMapping(value = "/delete/{id}")
    public JsonReturnDTO deleteCategory(@PathVariable("id") Integer id) {
        //禁止删除有文章的分类
        int count = articleService.countArticleByCategoryId(id);
        if (count == 0) {
            categoryService.deleteCategory(id);
        }
        return new JsonReturnDTO().success();
        // return "redirect:/admin/category";
    }

    /*
    // 编辑分类
    @RequestMapping(value = "/edit/{id}")
    public ModelAndView editCategoryView(@PathVariable("id") Integer id)  {
        ModelAndView modelAndView = new ModelAndView();
        CategoryEntity category =  categoryService.getCategoryById(id);
        modelAndView.addObject("category",category);
        List<CategoryEntity> categoryList = categoryService.listCategoryWithCount();
        modelAndView.addObject("categoryList",categoryList);
        modelAndView.setViewName("Admin/Category/edit");
        return modelAndView;
    }
    */

    // 编辑分类提交
    @RequestMapping(value = "/editSubmit",method = RequestMethod.POST)
    public JsonReturnDTO editCategorySubmit(CategoryEntity category)  {
        categoryService.updateCategory(category);
        return new JsonReturnDTO().success();
        // return "redirect:/admin/category";
    }
}
