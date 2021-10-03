package com.lblog.blogbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lblog.blogbackend.model.entity.ArticleTagAssEntity;
import com.lblog.blogbackend.model.entity.TagEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleTagAssMapper extends BaseMapper<ArticleTagAssEntity> {

    /**
     * 添加文章和标签关联记录
     * @param record 关联对象
     * @return 影响行数
     */
    int insert(ArticleTagAssEntity record);

    /**
     * 根据标签ID删除记录
     * @param tagId 标签ID
     * @return 影响行数
     */
    int deleteByTagId(Integer tagId);

    /**
     * 根据文章ID删除记录
     * @param articleId 文章ID
     * @return 影响行数
     */
    int deleteByArticleId(Integer articleId);

    /**
     * 根据标签ID统计文章数
     * @param tagId 标签ID
     * @return 文章数量
     */
    int countArticleByTagId(Integer tagId);

    /**
     * 根据文章获得标签列表
     *
     * @param articleId 文章ID
     * @return 标签列表
     */
    List<TagEntity> listTagByArticleId(Integer articleId);
}
