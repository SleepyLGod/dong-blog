package com.lblog.blogbackend.service.impl;

import com.lblog.blogbackend.entity.*;
import com.lblog.blogbackend.mapper.*;
import com.lblog.blogbackend.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

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

    @Override
    public Integer countArticle(Integer status) {
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
        try {
            cnt = articleCategoryAssMapper.countArticleByCategoryId(categoryId);
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("根据分类统计文章数量失败!");
        }
        return cnt;
    }

    @Override
    public Integer countArticleByTagId(Integer tagId) {
        Integer cnt = 0;
        try {
            cnt = articleTagAssMapper.countArticleByTagId(tagId);
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("根据标签统计文章数量失败！");
        }
        return cnt;
    }

    @Override
    public List<ArticleEntity> listArticle(HashMap<String, Object> criteria) {
        return articleMapper.findAll(criteria);
    }

    @Override
    public List<ArticleEntity> listRecentArticle(Integer userId, Integer limit) {
        return articleMapper.listArticleByLimit(userId, limit);
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
    public void deleteArticleBatch(List<Integer> ids) {
        articleMapper.deleteBatch(ids);
    }

    @Override
    public void deleteArticle(Integer id) {
        // 删除文章id
        articleMapper.deleteById(id);
        // 删除评论
        commentMapper.deleteByArticleId(id);
        // 删除分类关联
        articleCategoryAssMapper.deleteByArticleId(id);
        // 删除标签关联
        articleTagAssMapper.deleteByArticleId(id);
    }

    @Override
    public ArticleEntity getArticleByStatusAndId(Integer status, Integer id) {
        ArticleEntity article = articleMapper.getArticleByStatusAndId(status,id);
        if(article != null) {
            List<CategoryEntity> categoryEntityList = articleCategoryAssMapper.listCategoryByArticleId(article.getArticleId());
            List<TagEntity> tagEntityList = articleTagAssMapper.listTagByArticleId(article.getArticleId());
            article.setCategoryList(categoryEntityList);
            article.setTagList(tagEntityList);
        }
        return article;
    }

    @Override
    public List<ArticleEntity> listArticleByViewCount(Integer limit) {
        return null;
    }

    @Override
    public ArticleEntity getAfterArticle(Integer id) {
        return null;
    }

    @Override
    public ArticleEntity getPreArticle(Integer id) {
        return null;
    }

    @Override
    public List<ArticleEntity> listRandomArticle(Integer limit) {
        return null;
    }

    @Override
    public List<ArticleEntity> listArticleByCommentCount(Integer limit) {
        return null;
    }

    @Override
    public void insertArticle(ArticleEntity article) {

    }

    @Override
    public void updateCommentCount(Integer articleId) {

    }

    @Override
    public ArticleEntity getLastUpdateArticle() {
        return null;
    }

    @Override
    public List<ArticleEntity> listArticleByCategoryId(Integer cateId, Integer limit) {
        return null;
    }

    @Override
    public List<ArticleEntity> listArticleByCategoryIds(List<Integer> cateIds, Integer limit) {
        return null;
    }

    @Override
    public List<Integer> listCategoryIdByArticleId(Integer articleId) {
        return null;
    }

    @Override
    public List<ArticleEntity> listAllNotWithContent() {
        return null;
    }
}
