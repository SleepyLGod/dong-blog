package com.lhd.mylblog.modules.admin.service.impl;

import com.lhd.mylblog.common.enums.CommentStatus;
import com.lhd.mylblog.modules.admin.mapper.*;
import com.lhd.mylblog.modules.admin.model.*;
import com.lhd.mylblog.modules.admin.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

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
    public Long countArticleComment() {
        Long cnt = 0L;
        try {
            cnt = articleMapper.countArticleComment();
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("统计文章评论数失败!");
        }
        return cnt;
    }

    @Override
    public Long countArticleView() {
        Long cnt = 0L;
        try {
            cnt = articleMapper.countArticleView();
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("统计文章访问量失败!");
        }
        return cnt;
    }

    @Override
    public Long countArticleByCategoryId(Long categoryId) {
        Long cnt = 0L;
        Category category = categoryMapper.getCategoryById(categoryId);
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
    public Long countArticleByTagId(Long tagId) {
        Long cnt = 0L;
        Tag tag = tagMapper.getTagById(tagId);
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
    public List<Article> listArticle(HashMap<String, Object> criteria) {
        List<Article> articleList = articleMapper.findAll(criteria);
        List<Article> articleList1 = null;
        for (int i = 0;i < articleList.size();++i) {
            Article article = articleList.get(i);
            // 原始时间 2021-10-01 00:00:00
            SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
            ParsePosition pos = new ParsePosition(0);
            Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
            if(article.getArticleDeletedTime().equals(date)) {
                articleList1.add(article);
            }
        }
        return articleList1;
    }

    @Override
    public List<Article> listRecentArticle(Long userId, Integer limit) {
        List<Article> articleList = articleMapper.listArticleByLimit(userId, limit);
        List<Article> articleList1 = null;
        for (int i = 0;i < articleList.size();++i) {
            Article article = articleList.get(i);
            // 原始时间 2021-10-01 00:00:00
            SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
            ParsePosition pos = new ParsePosition(0);
            Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
            if(article.getArticleDeletedTime().equals(date)) {
                articleList1.add(article);
            }
        }
        return articleList1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateArticleDetail(Article article) {
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
                ArticleCategoryAss articleCategoryAss = new ArticleCategoryAss(article.getArticleId(), article.getCategoryList().get(i).getCategoryId());
                articleCategoryAssMapper.insert(articleCategoryAss);
            }
        }
        // 更新标签
        if(article.getTagList() != null) {
            // 删除关联
            articleTagAssMapper.deleteByArticleId(article.getArticleId());
            // 建立关联
            for (int i = 0; i < article.getTagList().size(); ++i) {
                ArticleTagAss articleTagAss = new ArticleTagAss(article.getArticleId(), article.getTagList().get(i).getTagId());
                articleTagAssMapper.insert(articleTagAss);
            }
        }
    }

    @Override
    public void updateArticle(Article article) {
        articleMapper.update(article);
    }

    @Override
    public void deleteArticleBatch(List<Long> ids) { // 批量删除文章
        Date deleteDate = new Date();
        for(int i = 0; i < ids.size(); ++i) {
            Long id = ids.get(i);
            deleteArticle(id);
        }
    }

    @Override
    public void deleteArticle(Long id) {
        // 删除文章id
        Article article = articleMapper.getArticleByStatusAndId(1,id);
        article.setArticleDeletedTime(new Date());
        // 删除评论
        commentMapper.deleteByArticleId(id);
        // 删除分类关联  关联不需要逻辑删除
        articleCategoryAssMapper.deleteByArticleId(id);
        // 删除标签关联  关联不需要逻辑删除
        articleTagAssMapper.deleteByArticleId(id);
    }

    @Override
    public Article getArticleByStatusAndId(Integer status, Long id) {
        Article article = articleMapper.getArticleByStatusAndId(status,id);
        // 原始时间 2021-10-01 00:00:00
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        ParsePosition pos = new ParsePosition(0);
        Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
        if((article != null) && (article.getArticleDeletedTime().equals(date))) {
            List<Category> categoryList = articleCategoryAssMapper.listCategoryByArticleId(article.getArticleId());
            List<Tag> tagList = articleTagAssMapper.listTagByArticleId(article.getArticleId());
            article.setCategoryList(categoryList);
            article.setTagList(tagList);
        }else{
            article = null;
        }
        return article;
    }

    @Override
    public List<Article> listArticleByViewCount(Integer limit) {
        return articleMapper.listArticleByViewCount(limit);
    }

    // 递归函数
    @Override
    public Article getAfterArticle(Long id) {
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
    public Article getPreArticle(Long id) {
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
    public List<Article> listRandomArticle(Integer limit) {
        return articleMapper.listRandomArticle(limit);
    }

    @Override
    public List<Article> listArticleByCommentCount(Integer limit) {
        return articleMapper.listArticleByCommentCount(limit);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertArticle(Article article) {
        article.setArticleIsComment(CommentStatus.ALLOW.getValue());
        article.setArticleCommentCount(0L);
        article.setArticleViews(0L);
        article.setArticleLikeCount(0L);
        article.setArticleOrder(1);
        article.setArticleCreateTime(new Date());
        article.setArticleUpdateTime(new Date());
        articleMapper.insert(article);
        for (int i = 0; i<article.getCategoryList().size(); ++i) {
            ArticleCategoryAss articleCategoryAss = new ArticleCategoryAss(article.getArticleId(),article.getCategoryList().get(i).getCategoryId());
            articleCategoryAssMapper.insert(articleCategoryAss);
        }
        for(int j = 0; j < article.getTagList().size(); ++j) {
            ArticleTagAss articleTagAss = new ArticleTagAss(article.getArticleId(),article.getTagList().get(j).getTagId());
            articleTagAssMapper.insert(articleTagAss);
        }
    }

    @Override
    public void updateCommentCount(Long articleId) {
        articleMapper.updateCommentCount(articleId);
    }

    @Override
    public Article getLastUpdateArticle() {
        return articleMapper.getLastUpdateArticle();
    }

    @Override
    public List<Article> listArticleByCategoryId(Long cateId, Integer limit) {
        List<Article> articleList = articleMapper.findArticleByCategoryId(cateId,limit);
        List<Article> articleList1 =null;
        for(int i = 0; i < articleList.size(); ++i) {
            Article article = articleList.get(i);
            // 原始时间 2021-10-01 00:00:00
            SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
            ParsePosition pos = new ParsePosition(0);
            Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
            if(article.getArticleDeletedTime().equals(date)) {
                articleList1.add(article);
            }
        }
        return articleList1;
    }

    @Override
    public List<Article> listArticleByCategoryIds(List<Long> cateIds, Integer limit) {
        if(cateIds == null || cateIds.size() == 0) {
            return null;
        }
        else {
            List<Article> articleList = articleMapper.findArticleByCategoryIds(cateIds, limit);
            List<Article> articleList1 =null;
            for(int i = 0; i < articleList.size(); ++i) {
                Article article = articleList.get(i);
                // 原始时间 2021-10-01 00:00:00
                SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
                ParsePosition pos = new ParsePosition(0);
                Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
                if(article.getArticleDeletedTime().equals(date)) {
                    articleList1.add(article);
                }
            }
            return articleList1;
        }
    }

    @Override
    public List<Long> listCategoryIdByArticleId(Long articleId) {
        List<Long> categoryIdList = articleCategoryAssMapper.selectCategoryIdByArticleId(articleId);
        List<Long> categoryIdList1= null;
        for( int i = 0; i < categoryIdList.size();++i) {
            Category category = categoryMapper.getCategoryById(categoryIdList.get(i));
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
    public List<Article> listAllNotWithContent() {
        return articleMapper.listAllNotWithContent();
    }
}
