package com.lhd.mylblog.modules.admin.service.impl;

import com.lhd.mylblog.modules.admin.mapper.ArticleCategoryAssMapper;
import com.lhd.mylblog.modules.admin.mapper.ArticleMapper;
import com.lhd.mylblog.modules.admin.model.Category;
import com.lhd.mylblog.modules.admin.mapper.CategoryMapper;
import com.lhd.mylblog.modules.admin.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author generator
 * @since 2021-10-28
 */
@Service
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

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
    public List<Category> listCategory() {
        List<Category> categoryList = null;
        List<Category> categoryList1= null;
        // 原始时间 2021-10-01 00:00:00
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        ParsePosition pos = new ParsePosition(0);
        Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
        try {
            categoryList = categoryMapper.listCategory();
            for(int i = 0; i < categoryList.size(); ++i) {
                if(categoryList.get(i).getCategoryDeletedTime().equals(date)) {
                    categoryList1.add(categoryList.get(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获得分类列表失败, cause:{}", e);
        }
        return categoryList1;
    }

    @Override
    public List<Category> listCategoryWithCount() {
        List<Category> categoryList = null;
        List<Category> categoryList1= null;
        // 原始时间 2021-10-01 00:00:00
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        ParsePosition pos = new ParsePosition(0);
        Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
        try {
            categoryList = categoryMapper.listCategory();
            for (int i = 0; i < categoryList.size(); ++i) {
                Category category = categoryList.get(i);
                if(category.getCategoryDeletedTime().equals(date)) {
                    Long cnt = articleCategoryAssMapper.countArticleByCategoryId(category.getCategoryId());
                    category.setArticleCount(cnt.intValue());
                    categoryList1.add(category);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获得分类列表失败, cause:{}", e);
        }
        return categoryList1;
    }

    @Override
    public void deleteCategory(Long id) {
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
    public Category getCategoryById(Long id) {
        Category category = null;
        // 原始时间 2021-10-01 00:00:00
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        ParsePosition pos = new ParsePosition(0);
        Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
        try {
            category = categoryMapper.getCategoryById(id);
            if(category.getCategoryDeletedTime().equals(date)) {
                category = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据分类ID获得分类, id:{}, cause:{}", id, e);
        }
        return category;
    }

    @Override
    public Category insertCategory(Category category) {
        try {
            categoryMapper.insert(category);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("创建失败, category:{}, cause:{}", category, e);
        }
        return category;
    }

    @Override
    public void updateCategory(Category category) {
        try {
            categoryMapper.update(category);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("更新失败, category:{}, cause:{}", category, e);
        }
    }

    @Override
    public Category getCategoryByName(String name) {
        Category category = null;
        // 原始时间 2021-10-01 00:00:00
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        ParsePosition pos = new ParsePosition(0);
        Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
        try {
            category = categoryMapper.getCategoryByName(name);
            if (category.getCategoryDeletedTime().equals(date)){
                category = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获得失败, category:{}, cause:{}", category, e);
        }
        return category;
    }

}
