package com.lblog.blogbackend.service.impl;
import com.lblog.blogbackend.entity.ArticleEntity;
import com.lblog.blogbackend.service.ArticleService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Override
    public Integer countArticle(Integer status) {
        return null;
    }

    @Override
    public Integer countArticleComment() {
        return null;
    }

    @Override
    public Integer countArticleView() {
        return null;
    }

    @Override
    public Integer countArticleByCategoryId(Integer categoryId) {
        return null;
    }

    @Override
    public Integer countArticleByTagId(Integer tagId) {
        return null;
    }

    @Override
    public List<ArticleEntity> listArticle(HashMap<String, Object> criteria) {
        return null;
    }

    @Override
    public List<ArticleEntity> listRecentArticle(Integer userId, Integer limit) {
        return null;
    }

    @Override
    public void updateArticleDetail(ArticleEntity article) {

    }

    @Override
    public void updateArticle(ArticleEntity article) {

    }

    @Override
    public void deleteArticleBatch(List<Integer> ids) {

    }

    @Override
    public void deleteArticle(Integer id) {

    }

    @Override
    public ArticleEntity getArticleByStatusAndId(Integer status, Integer id) {
        return null;
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
