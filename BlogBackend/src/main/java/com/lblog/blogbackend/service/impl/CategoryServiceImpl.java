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
        try {
            categoryEntityList = categoryMapper.listCategory();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据文章获得分类列表失败, cause:{}", e);
        }
        return categoryEntityList;
    }

    @Override
    public List<CategoryEntity> listCategoryWithCount() {
        List<CategoryEntity> categoryEntityList = null;
        try {
            categoryEntityList = categoryMapper.listCategory();
            for (int i = 0; i < categoryEntityList.size(); ++i) {
                Integer cnt = articleCategoryAssMapper.countArticleByCategoryId(categoryEntityList.get(i).getCategoryId());
                categoryEntityList.get(i).setArticleCount(cnt);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据文章获得分类列表失败, cause:{}", e);
        }
        return categoryEntityList;
    }

    @Override
    public void deleteCategory(Integer id) {
        try { // 删除自己和关联博文
            categoryMapper.deleteCategory(id);
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
        try {
            categoryEntity = categoryMapper.getCategoryById(id);
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
        try {
            categoryEntity = categoryMapper.getCategoryByName(name);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获得失败, category:{}, cause:{}", categoryEntity, e);
        }
        return categoryEntity;
    }

}
