package com.lblog.blogbackend.service.impl;

import com.lblog.blogbackend.mapper.ArticleCategoryAssMapper;
import com.lblog.blogbackend.mapper.ArticleMapper;
import com.lblog.blogbackend.mapper.CategoryMapper;
import com.lblog.blogbackend.model.entity.CategoryEntity;
import com.lblog.blogbackend.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ArticleCategoryAssMapper articleCategoryAssMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Integer countCategory() {
        Integer cnt = 0;
        return null;
    }

    @Override
    public List<CategoryEntity> listCategory() {
        return null;
    }

    @Override
    public List<CategoryEntity> listCategoryWithCount() {
        return null;
    }

    @Override
    public void deleteCategory(Integer id) {

    }

    @Override
    public CategoryEntity getCategoryById(Integer id) {
        return null;
    }

    @Override
    public CategoryEntity insertCategory(CategoryEntity category) {
        return null;
    }

    @Override
    public void updateCategory(CategoryEntity category) {

    }

    @Override
    public CategoryEntity getCategoryByName(String name) {
        return null;
    }
}
