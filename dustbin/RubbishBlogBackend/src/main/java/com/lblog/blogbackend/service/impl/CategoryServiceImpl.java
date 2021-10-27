package com.lblog.blogbackend.service.impl;

import com.lblog.blogbackend.mapper.ArticleCategoryAssMapper;
import com.lblog.blogbackend.mapper.ArticleMapper;
import com.lblog.blogbackend.mapper.CategoryMapper;
import com.lblog.blogbackend.model.entity.CategoryEntity;
import com.lblog.blogbackend.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.swing.*;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        try {
            cnt = categoryMapper.countCategory();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("分类统计失败, cause:{}", e);
            System.out.println("分类统计失败!");
        }
        return cnt;
    }

    @Override
    public List<CategoryEntity> listCategory() {
        List<CategoryEntity> categoryEntityList = null;
        List<CategoryEntity> categoryEntityList1= null;
        // 原始时间 2021-10-01 00:00:00
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        ParsePosition pos = new ParsePosition(0);
        Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
        try {
            categoryEntityList = categoryMapper.listCategory();
            for(int i = 0; i < categoryEntityList.size(); ++i) {
                if(categoryEntityList.get(i).getCategoryDeletedTime().equals(date)) {
                    categoryEntityList1.add(categoryEntityList.get(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获得分类列表失败, cause:{}", e);
        }
        return categoryEntityList1;
    }

    @Override
    public List<CategoryEntity> listCategoryWithCount() {
        List<CategoryEntity> categoryEntityList = null;
        List<CategoryEntity> categoryEntityList1= null;
        // 原始时间 2021-10-01 00:00:00
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        ParsePosition pos = new ParsePosition(0);
        Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
        try {
            categoryEntityList = categoryMapper.listCategory();
            for (int i = 0; i < categoryEntityList.size(); ++i) {
                CategoryEntity category = categoryEntityList.get(i);
                if(category.getCategoryDeletedTime().equals(date)) {
                    Integer cnt = articleCategoryAssMapper.countArticleByCategoryId(category.getCategoryId());
                    category.setArticleCount(cnt);
                    categoryEntityList1.add(category);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获得分类列表失败, cause:{}", e);
        }
        return categoryEntityList1;
    }

    @Override
    public void deleteCategory(Integer id) {
        try { // 删除自己和关联博文!!!!!
            // categoryMapper.deleteCategory(id);
            categoryMapper.getCategoryById(id).setCategoryDeletedTime(new Date());
            articleCategoryAssMapper.deleteByCategoryId(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除失败, id:{}, cause:{}", id, e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    @Override
    public CategoryEntity getCategoryById(Integer id) {
        CategoryEntity categoryEntity = null;
        // 原始时间 2021-10-01 00:00:00
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        ParsePosition pos = new ParsePosition(0);
        Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
        try {
            categoryEntity = categoryMapper.getCategoryById(id);
            if(categoryEntity.getCategoryDeletedTime().equals(date)) {
                categoryEntity = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据分类ID获得分类, id:{}, cause:{}", id, e);
        }
        return categoryEntity;
    }

    @Override
    public CategoryEntity insertCategory(CategoryEntity category) {
        try {
            categoryMapper.insert(category);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("创建失败, category:{}, cause:{}", category, e);
        }
        return category;
    }

    @Override
    public void updateCategory(CategoryEntity category) {
        try {
            categoryMapper.update(category);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("更新失败, category:{}, cause:{}", category, e);
        }
    }

    @Override
    public CategoryEntity getCategoryByName(String name) {
        CategoryEntity categoryEntity = null;
        // 原始时间 2021-10-01 00:00:00
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        ParsePosition pos = new ParsePosition(0);
        Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
        try {
            categoryEntity = categoryMapper.getCategoryByName(name);
            if (categoryEntity.getCategoryDeletedTime().equals(date)){
                categoryEntity = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获得失败, category:{}, cause:{}", categoryEntity, e);
        }
        return categoryEntity;
    }

}
