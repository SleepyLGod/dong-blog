package com.lhd.mylblog.modules.admin.service;

import com.lhd.mylblog.modules.admin.model.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author generator
 * @since 2021-10-28
 */
public interface TagService extends IService<Tag> {
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
    List<Tag> listTag() ;

    /**
     * 获得标签列表
     *
     * @return 标签列表
     */
    List<Tag> listTagWithCount() ;

    /**
     * 根据id获得标签信息
     *
     * @param id 标签ID
     * @return 标签
     */
    Tag getTagById(Long id) ;

    /**
     * 添加标签
     *
     * @param tag 标签
     * @return 标签
     */
    Tag insertTag(Tag tag) ;

    /**
     * 修改标签
     *
     * @param tag 标签
     */
    void updateTag(Tag tag) ;

    /**
     * 删除标签
     *
     * @param id 标签iD
     */
    void deleteTag(Long id) ;

    /**
     * 根据标签名获取标签
     *
     * @param name 标签名称
     * @return 标签
     */
    Tag getTagByName(String name) ;

    /**
     * 根据文章ID获得标签
     *
     * @param articleId 文章ID
     * @return 标签列表
     */
    List<Tag> listTagByArticleId(Long articleId);

}
