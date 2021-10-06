package com.lblog.blogbackend.service.impl;

import com.lblog.blogbackend.constant.enums.CommentStatusEnums;
import com.lblog.blogbackend.model.entity.*;
import com.lblog.blogbackend.mapper.*;
import com.lblog.blogbackend.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;


import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    // 自动注入容器
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleCategoryAssMapper articleCategoryAssMapper;

    @Autowired
    private ArticleTagAssMapper articleTagAssMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private TagMapper tagMapper;

    @Override
    public Integer countArticle(Integer status) { // 草稿 or 正式文章
        Integer cnt = 0;
        try {
            cnt = articleMapper.countArticle(status);
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("统计文章数失败!");
        }
        return cnt;
    }

    @Override
    public Integer countArticleComment() {
        Integer cnt = 0;
        try {
            cnt = articleMapper.countArticleComment();
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("统计文章评论数失败!");
        }
        return cnt;
    }

    @Override
    public Integer countArticleView() {
        Integer cnt = 0;
        try {
            cnt = articleMapper.countArticleView();
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("统计文章访问量失败!");
        }
        return cnt;
    }

    @Override
    public Integer countArticleByCategoryId(Integer categoryId) {
        Integer cnt = 0;
        CategoryEntity category = categoryMapper.getCategoryById(categoryId);
        // 原始时间 2021-10-01 00:00:00
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        ParsePosition pos = new ParsePosition(0);
        Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
        if(category.getCategoryDeletedTime().equals(date)) { // 没有被删
        try {
            cnt = articleCategoryAssMapper.countArticleByCategoryId(categoryId);
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("根据分类统计文章数量失败!");
        }
        }
        return cnt;
    }

    @Override
    public Integer countArticleByTagId(Integer tagId) {
        Integer cnt = 0;
        TagEntity tag = tagMapper.getTagById(tagId);
        // 原始时间 2021-10-01 00:00:00
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        ParsePosition pos = new ParsePosition(0);
        Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
        if(tag.getTagDeletedTime().equals(date)){
            try {
                cnt = articleTagAssMapper.countArticleByTagId(tagId);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("根据标签统计文章数量失败！");
            }
        }
        return cnt;
    }

    @Override
    public List<ArticleEntity> listArticle(HashMap<String, Object> criteria) {
        List<ArticleEntity> articleEntityList = articleMapper.findAll(criteria);
        List<ArticleEntity> articleEntityList1 = null;
        for (int i = 0;i < articleEntityList.size();++i) {
            ArticleEntity article = articleEntityList.get(i);
            // 原始时间 2021-10-01 00:00:00
            SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
            ParsePosition pos = new ParsePosition(0);
            Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
            if(article.getArticleDeletedTime().equals(date)) {
                articleEntityList1.add(article);
            }
        }
        return articleEntityList1;
    }

    @Override
    public List<ArticleEntity> listRecentArticle(Integer userId, Integer limit) {
        List<ArticleEntity> articleEntityList = articleMapper.listArticleByLimit(userId, limit);
        List<ArticleEntity> articleEntityList1 = null;
        for (int i = 0;i < articleEntityList.size();++i) {
            ArticleEntity article = articleEntityList.get(i);
            // 原始时间 2021-10-01 00:00:00
            SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
            ParsePosition pos = new ParsePosition(0);
            Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
            if(article.getArticleDeletedTime().equals(date)) {
                articleEntityList1.add(article);
            }
        }
        return articleEntityList1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateArticleDetail(ArticleEntity article) {
        // 更新提交时间
        article.setArticleUpdateTime(new Date());
        // 更新文章
        articleMapper.update(article);
        // 更新分类
        if(article.getCategoryList() != null){
            // 删除关联
            articleCategoryAssMapper.deleteByArticleId(article.getArticleId());
            // 建立关联
            for(int i = 0; i < article.getCategoryList().size(); ++i) {
                ArticleCategoryAssEntity articleCategoryAssEntity = new ArticleCategoryAssEntity(article.getArticleId(), article.getCategoryList().get(i).getCategoryId());
                articleCategoryAssMapper.insert(articleCategoryAssEntity);
            }
        }
        // 更新标签
        if(article.getTagList() != null) {
            // 删除关联
            articleTagAssMapper.deleteByArticleId(article.getArticleId());
            // 建立关联
            for (int i = 0; i < article.getTagList().size(); ++i) {
                ArticleTagAssEntity articleTagAssEntity = new ArticleTagAssEntity(article.getArticleId(), article.getTagList().get(i).getTagId());
                articleTagAssMapper.insert(articleTagAssEntity);
            }
        }
    }

    @Override
    public void updateArticle(ArticleEntity article) {
        articleMapper.update(article);
    }

    @Override
    public void deleteArticleBatch(List<Integer> ids) { // 批量删除文章
        Date deleteDate = new Date();
        for(int i = 0; i < ids.size(); ++i) {
            Integer id = ids.get(i);
            deleteArticle(id);
        }
    }

    @Override
    public void deleteArticle(Integer id) {
        // 删除文章id
        ArticleEntity article = articleMapper.getArticleByStatusAndId(1,id);
        article.setArticleDeletedTime(new Date());
        // 删除评论
        commentMapper.deleteByArticleId(id);
        // 删除分类关联  关联不需要逻辑删除
        articleCategoryAssMapper.deleteByArticleId(id);
        // 删除标签关联  关联不需要逻辑删除
        articleTagAssMapper.deleteByArticleId(id);
    }

    @Override
    public ArticleEntity getArticleByStatusAndId(Integer status, Integer id) {
        ArticleEntity article = articleMapper.getArticleByStatusAndId(status,id);
        // 原始时间 2021-10-01 00:00:00
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        ParsePosition pos = new ParsePosition(0);
        Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
        if((article != null) && (article.getArticleDeletedTime().equals(date))) {
            List<CategoryEntity> categoryEntityList = articleCategoryAssMapper.listCategoryByArticleId(article.getArticleId());
            List<TagEntity> tagEntityList = articleTagAssMapper.listTagByArticleId(article.getArticleId());
            article.setCategoryList(categoryEntityList);
            article.setTagList(tagEntityList);
        }else{
            article = null;
        }
        return article;
    }

    @Override
    public List<ArticleEntity> listArticleByViewCount(Integer limit) {
        return articleMapper.listArticleByViewCount(limit);
    }

    // 递归函数
    @Override
    public ArticleEntity getAfterArticle(Integer id) {
        if(articleMapper.getArticleByStatusAndId(1,id) != null) {
            return null;
        }
        // 原始时间 2021-10-01 00:00:00
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        ParsePosition pos = new ParsePosition(0);
        Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
        if (articleMapper.getAfterArticle(id).getArticleDeletedTime().equals(date)) {
            return articleMapper.getAfterArticle(id);
        } else {
                return getAfterArticle(id + 1);
        }
    }

    // 递归函数
    @Override
    public ArticleEntity getPreArticle(Integer id) {
        if(articleMapper.getArticleByStatusAndId(1,id) != null) {
            return null;
        }
        // 原始时间 2021-10-01 00:00:00
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        ParsePosition pos = new ParsePosition(0);
        Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
        if(articleMapper.getPreArticle(id).getArticleDeletedTime().equals(date)) {
            return articleMapper.getPreArticle(id);
        } else {
            return articleMapper.getPreArticle(id - 1);
        }
    }

    @Override
    public List<ArticleEntity> listRandomArticle(Integer limit) {
        return articleMapper.listRandomArticle(limit);
    }

    @Override
    public List<ArticleEntity> listArticleByCommentCount(Integer limit) {
        return articleMapper.listArticleByCommentCount(limit);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertArticle(ArticleEntity article) {
        article.setArticleIsComment(CommentStatusEnums.ALLOW.getValue());
        article.setArticleCommentCount(0);
        article.setArticleViews(0);
        article.setArticleLikeCount(0);
        article.setArticleOrder(1);
        article.setArticleCreateTime(new Date());
        article.setArticleUpdateTime(new Date());
        articleMapper.insert(article);
        for (int i = 0; i<article.getCategoryList().size(); ++i) {
            ArticleCategoryAssEntity articleCategoryAssEntity = new ArticleCategoryAssEntity(article.getArticleId(),article.getCategoryList().get(i).getCategoryId());
            articleCategoryAssMapper.insert(articleCategoryAssEntity);
        }
        for(int j = 0; j < article.getTagList().size(); ++j) {
            ArticleTagAssEntity articleTagAssEntity = new ArticleTagAssEntity(article.getArticleId(),article.getTagList().get(j).getTagId());
            articleTagAssMapper.insert(articleTagAssEntity);
        }
    }

    @Override
    public void updateCommentCount(Integer articleId) {
        articleMapper.updateCommentCount(articleId);
    }

    @Override
    public ArticleEntity getLastUpdateArticle() {
        return articleMapper.getLastUpdateArticle();
    }

    @Override
    public List<ArticleEntity> listArticleByCategoryId(Integer cateId, Integer limit) {
        List<ArticleEntity> articleEntityList = articleMapper.findArticleByCategoryId(cateId,limit);
        List<ArticleEntity> articleEntityList1 =null;
        for(int i = 0; i < articleEntityList.size(); ++i) {
            ArticleEntity article = articleEntityList.get(i);
            // 原始时间 2021-10-01 00:00:00
            SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
            ParsePosition pos = new ParsePosition(0);
            Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
            if(article.getArticleDeletedTime().equals(date)) {
                articleEntityList1.add(article);
            }
        }
        return articleEntityList1;
    }

    @Override
    public List<ArticleEntity> listArticleByCategoryIds(List<Integer> cateIds, Integer limit) {
        if(cateIds == null || cateIds.size() == 0) {
            return null;
        }
        else {
            List<ArticleEntity> articleEntityList = articleMapper.findArticleByCategoryIds(cateIds, limit);
            List<ArticleEntity> articleEntityList1 =null;
            for(int i = 0; i < articleEntityList.size(); ++i) {
                ArticleEntity article = articleEntityList.get(i);
                // 原始时间 2021-10-01 00:00:00
                SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
                ParsePosition pos = new ParsePosition(0);
                Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
                if(article.getArticleDeletedTime().equals(date)) {
                    articleEntityList1.add(article);
                }
            }
            return articleEntityList1;
        }
    }

    @Override
    public List<Integer> listCategoryIdByArticleId(Integer articleId) {
        List<Integer> categoryIdList = articleCategoryAssMapper.selectCategoryIdByArticleId(articleId);
        List<Integer> categoryIdList1= null;
        for( int i = 0; i < categoryIdList.size();++i) {
            CategoryEntity category = categoryMapper.getCategoryById(categoryIdList.get(i));
            // 原始时间 2021-10-01 00:00:00
            SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
            ParsePosition pos = new ParsePosition(0);
            Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
            if(category.getCategoryDeletedTime().equals(date)) {
                categoryIdList1.add(category.getCategoryId());
            }
        }
        return categoryIdList1;
    }

    @Override
    public List<ArticleEntity> listAllNotWithContent() {
        return articleMapper.listAllNotWithContent();
    }
}
