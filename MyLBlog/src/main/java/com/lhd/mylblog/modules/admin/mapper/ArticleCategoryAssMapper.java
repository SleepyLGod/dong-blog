package com.lhd.mylblog.modules.admin.mapper;

import com.lhd.mylblog.modules.admin.model.ArticleCategoryAss;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lhd.mylblog.modules.admin.model.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author generator
 * @since 2021-10-28
 */
@Mapper
public interface ArticleCategoryAssMapper extends BaseMapper<ArticleCategoryAss> {

    /**
     * 添加文章和分类关联记录
     * @param record 关联对象
     * @return 影响行数
     */
    @Override
    int insert(ArticleCategoryAss record);

    /**
     * 根据分类ID删除记录
     * @param categoryId 分类ID
     * @return 影响行数
     */
    int deleteByCategoryId(Long categoryId);

    /**
     * 根据文章ID删除记录
     * @param articleId 文章ID
     * @return 影响行数
     */
    int deleteByArticleId(Long articleId);

    /**
     * 根据分类ID统计文章数
     * @param categoryId 分类ID
     * @return 文章数量
     */
    Long countArticleByCategoryId(Long categoryId);


    /**
     * 根据文章ID查询分类ID
     *
     * @param articleId 文章ID
     * @return 分类ID列表
     */
    List<Long> selectCategoryIdByArticleId(Long articleId);

    /**
     * 根据分类ID查询文章ID
     *
     * @param categoryId 分类ID
     * @return 文章ID列表
     */
    List<Long> selectArticleIdByCategoryId(Long categoryId);

    /**
     * 根据文章ID获得分类列表
     *
     * @param articleId 文章ID
     * @return 分类列表
     */
    List<Category> listCategoryByArticleId(Long articleId);
}
