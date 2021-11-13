package com.lhd.mylblog.modules.admin.mapper;

import com.lhd.mylblog.modules.admin.model.ArticleTagAss;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lhd.mylblog.modules.admin.model.Tag;
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
public interface ArticleTagAssMapper extends BaseMapper<ArticleTagAss> {
    /**
     * 添加文章和标签关联记录
     * @param record 关联对象
     * @return 影响行数
     */
    @Override
    int insert(ArticleTagAss record);

    /**
     * 根据标签ID删除记录
     * @param tagId 标签ID
     * @return 影响行数
     */
    int deleteByTagId(Long tagId);

    /**
     * 根据文章ID删除记录
     * @param articleId 文章ID
     * @return 影响行数
     */
    int deleteByArticleId(Long articleId);

    /**
     * 根据标签ID统计文章数
     * @param tagId 标签ID
     * @return 文章数量
     */
    Long countArticleByTagId(Long tagId);

    /**
     * 根据文章获得标签列表
     *
     * @param articleId 文章ID
     * @return 标签列表
     */
    List<Tag> listTagByArticleId(Long articleId);
}
