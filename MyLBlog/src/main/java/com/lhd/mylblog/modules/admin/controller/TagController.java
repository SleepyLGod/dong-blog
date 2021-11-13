package com.lhd.mylblog.modules.admin.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.lhd.mylblog.common.api.CommonResult;
import com.lhd.mylblog.common.api.ResultCode;
import com.lhd.mylblog.modules.admin.model.Tag;
import com.lhd.mylblog.modules.admin.service.ArticleService;
import com.lhd.mylblog.modules.admin.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/admin/tag")
public class TagController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private TagService tagService;

    /**
     * 添加标签
     *
     * @param tag
     * @return
     */
    @ApiOperation(value = "添加标签", tags = {"tag"})
    @PostMapping(value = "/insert")
    public CommonResult insertTagSubmit(Tag tag)  {
        tagService.insertTag(tag);
        return CommonResult.success(ResultCode.SUCCESS);
    }

    /**
     * 删除标签
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除标签", tags = {"tag"})
    @PostMapping(value = "/delete/{id}")
    public CommonResult deleteTag(@PathVariable("id") Long id)  {
        Long count = articleService.countArticleByTagId(id);
        if (count == 0) {
            tagService.deleteTag(id);
        }
        return CommonResult.success(ResultCode.SUCCESS);
    }

    /**
     * 编辑标签提交
     *
     * @param tag
     * @return
     */
    @ApiOperation(value = "编辑标签提交", tags = {"tag"})
    @PostMapping(value = "/edit")
    public CommonResult editTagSubmit(Tag tag)  {
        tagService.updateTag(tag);
        return CommonResult.success(ResultCode.SUCCESS);
    }

}

