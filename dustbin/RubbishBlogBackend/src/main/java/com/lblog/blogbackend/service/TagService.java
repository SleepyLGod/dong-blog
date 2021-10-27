package com.lblog.blogbackend.service;

import com.lblog.blogbackend.model.entity.TagEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService {
    /**
     * 获得标签总数
     *
     * @return 数量
     */
    Integer countTag() ;

    /**
     * 获得标签列表
     *s
     * @return 标签列表
     */
    List<TagEntity> listTag() ;

    /**
     * 获得标签列表
     *
     * @return 标签列表
     */
    List<TagEntity> listTagWithCount() ;

    /**
     * 根据id获得标签信息
     *
     * @param id 标签ID
     * @return 标签
     */
    TagEntity getTagById(Integer id) ;

    /**
     * 添加标签
     *
     * @param tag 标签
     * @return 标签
     */
    TagEntity insertTag(TagEntity tag) ;

    /**
     * 修改标签
     *
     * @param tag 标签
     */
    void updateTag(TagEntity tag) ;

    /**
     * 删除标签
     *
     * @param id 标签iD
     */
    void deleteTag(Integer id) ;

    /**
     * 根据标签名获取标签
     *
     * @param name 标签名称
     * @return 标签
     */
    TagEntity getTagByName(String name) ;

    /**
     * 根据文章ID获得标签
     *
     * @param articleId 文章ID
     * @return 标签列表
     */
    List<TagEntity> listTagByArticleId(Integer articleId);
}
