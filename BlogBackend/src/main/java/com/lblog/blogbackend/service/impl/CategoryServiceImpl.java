package com.lblog.blogbackend.service.impl;

import com.lblog.blogbackend.entity.CategoryEntity;
import com.lblog.blogbackend.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Override
    public Integer countCategory() {
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
