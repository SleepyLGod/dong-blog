package com.lhd.mylblog.modules.admin.service;

import com.lhd.mylblog.modules.admin.model.Article;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author generator
 * @since 2021-10-28
 */
public interface ArticleService extends IService<Article> {
    /**
     * 获取文章总数
     *
     * @param status 状态
     * @return 数量
     */
    Integer countArticle(Integer status);

    /**
     * 获取评论总数
     *
     * @return 数量
     */
    Long countArticleComment();

    /**
     * 获得浏览量总数
     *
     * @return 数量
     */
    Long countArticleView();

    /**
     * 统计有这个分类的文章数
     *
     * @param categoryId 分类ID
     * @return 数量
     */
    Long countArticleByCategoryId(Long categoryId);

    /**
     * 统计有这个标签的文章数
     *
     * @param tagId 标签ID
     * @return 数量
     */
    Long countArticleByTagId(Long tagId);


    /**
     * 获得所有文章不分页
     *
     * @param criteria 查询条件
     * @return 列表
     */
    List<Article> listArticle(HashMap<String, Object> criteria);

    /**
     * 获得最新文章
     *
     * @param limit 查询数量
     * @return 列表
     */
    List<Article> listRecentArticle(Long userId, Integer limit);


    /**
     * 修改文章详细信息
     *
     * @param article 文章
     */
    void updateArticleDetail(Article article);

    /**
     * 修改文章简单信息
     *
     * @param article 文章
     */
    void updateArticle(Article article);

    /**
     * 批量删除文章
     *
     * @param ids 文章ID
     */
    void deleteArticleBatch(List<Long> ids);

    /**
     * 删除文章
     *
     * @param id 文章ID
     */
    void deleteArticle(Long id);

    /**
     * 文章详情页面显示
     *
     * @param status 状态
     * @param id     文章ID
     * @return 文章
     */
    Article getArticleByStatusAndId(Integer status, Long id);

    /**
     * 获取访问量较多的文章
     *
     * @param limit 查询数量
     * @return 列表
     */
    List<Article> listArticleByViewCount(Integer limit);

    /**
     * 获得上一篇文章
     *
     * @param id 文章ID
     * @return 文章
     */
    Article getAfterArticle(Long id);

    /**
     * 获得下一篇文章
     *
     * @param id 文章ID
     * @return 文章
     */
    Article getPreArticle(Long id);

    /**
     * 获得随机文章
     *
     * @param limit 查询数量
     * @return 列表
     */
    List<Article> listRandomArticle(Integer limit);

    /**
     * 获得评论数较多的文章
     *
     * @param limit 查询数量
     * @return 列表
     */
    List<Article> listArticleByCommentCount(Integer limit);

    /**
     * 添加文章
     *
     * @param article 文章
     */
    void insertArticle(Article article);


    /**
     * 更新文章的评论数
     *
     * @param articleId 文章ID
     */
    void updateCommentCount(Long articleId);

    /**
     * 获得最后更新记录
     *
     * @return 文章
     */
    Article getLastUpdateArticle();

    /**
     * 获得相关文章
     *
     * @param cateId 分类ID
     * @param limit  查询数量
     * @return 列表
     */
    List<Article> listArticleByCategoryId(Long categoryId, Integer limit);

    /**
     * 获得相关文章
     *
     * @param cateIds 分类ID集合
     * @param limit   数量
     * @return 列表
     */
    List<Article> listArticleByCategoryIds(List<Long> cateIds, Integer limit);


    /**
     * 根据文章ID获得分类ID列表
     *
     * @param articleId 文章Id
     * @return 列表
     */
    List<Long> listCategoryIdByArticleId(Long articleId);

    /**
     * 获得所有的文章
     *
     * @return 列表
     */
    List<Article> listAllNotWithContent();
}
