package com.lhd.mylblog.modules.admin.controller;


import com.lhd.mylblog.common.api.CommonResult;
import com.lhd.mylblog.common.api.ResultCode;
import com.lhd.mylblog.modules.admin.model.Category;
import com.lhd.mylblog.modules.admin.service.ArticleService;
import com.lhd.mylblog.modules.admin.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author generator
 * @since 2021-10-28
 */
@RestController
@Api
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 添加分类
     *
     * @param category
     * @return
     */
    @ApiOperation(value = "添加分类", tags = {"category"})
    @PostMapping(value = "/insert")
    public CommonResult insertCategorySubmit(Category category)  {
        categoryService.insertCategory(category);
        List<Category> categoryList = categoryService.listCategoryWithCount();
        String result = categoryList.toString();
        System.out.println(result);
        return CommonResult.success(ResultCode.SUCCESS);
    }

    /**
     * 删除分类
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除分类", tags = {"category"})
    @PostMapping(value = "/delete/{id}")
    public CommonResult deleteCategory(@PathVariable("id") Long id) {
        // 禁止删除有文章的分类
        Long count = articleService.countArticleByCategoryId(id);
        if (count == 0) {
            categoryService.deleteCategory(id);
        }
        return CommonResult.success(ResultCode.SUCCESS);
    }

    /**
     * 编辑分类提交
     *
     * @param category
     * @return
     */
    @ApiOperation(value = "编辑分类提交", tags = {"category"})
    @PostMapping(value = "/edit")
    public CommonResult editCategorySubmit(Category category)  {
        categoryService.updateCategory(category);
        return CommonResult.success(ResultCode.SUCCESS);
    }
}

