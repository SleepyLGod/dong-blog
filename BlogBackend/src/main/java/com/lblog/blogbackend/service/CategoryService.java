package com.lblog.blogbackend.service;

import com.lblog.blogbackend.model.entity.CategoryEntity;

import java.util.List;

public interface CategoryService {

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
        List<CategoryEntity> listCategory();

        /**
         * 获得分类列表
         *
         * @return 分类列表
         */
        List<CategoryEntity> listCategoryWithCount();

        /**
         * 删除分类
         *
         * @param id ID
         */
        void deleteCategory(Integer id);

        /**
         * 根据id查询分类信息
         *
         * @param id     ID
         * @return 分类
         */
        CategoryEntity getCategoryById(Integer id);

        /**
         * 添加分类
         *
         * @param category 分类
         * @return 分类
         */
        CategoryEntity insertCategory(CategoryEntity category);

        /**
         * 更新分类
         *
         * @param category 分类
         */
        void updateCategory(CategoryEntity category);

        /**
         * 根据分类名获取分类
         *
         * @param name 名称
         * @return 分类
         */
        CategoryEntity getCategoryByName(String name);
}
