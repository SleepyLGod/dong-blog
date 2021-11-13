package com.lhd.mylblog.modules.admin.service;

import com.lhd.mylblog.modules.admin.model.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author generator
 * @since 2021-10-28
 */
public interface CategoryService extends IService<Category> {
    /**
     * 获得分类总数
     *
     * @return
     */
    Integer countCategory();

    /**
     * 获得分类列表
     *
     * @return 分类列表
     */
    List<Category> listCategory();

    /**
     * 获得分类列表
     *
     * @return 分类列表
     */
    List<Category> listCategoryWithCount();

    /**
     * 删除分类
     *
     * @param id ID
     */
    void deleteCategory(Long id);

    /**
     * 根据id查询分类信息
     *
     * @param id     ID
     * @return 分类
     */
    Category getCategoryById(Long id);

    /**
     * 添加分类
     *
     * @param category 分类
     * @return 分类
     */
    Category insertCategory(Category category);

    /**
     * 更新分类
     *
     * @param category 分类
     */
    void updateCategory(Category category);

    /**
     * 根据分类名获取分类
     *
     * @param name 名称
     * @return 分类
     */
    Category getCategoryByName(String name);

}
