package com.lblog.blogbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lblog.blogbackend.model.entity.TagEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper /*extends BaseMapper<TagEntity> */{
    /**
     * 根据ID删除
     *
     * @param tagId 标签ID
     * @return 影响行数
     */
    int deleteById(Integer tagId);

    /**
     * 添加
     *
     * @param tag 标签
     * @return 影响行数
     */
    int insert(TagEntity tag);

    /**
     * 根据ID查询
     *
     * @param tagId 标签ID
     * @return 标签
     */
    TagEntity getTagById(Integer tagId);

    /**
     * 更新
     * @param tag 标签
     * @return 影响行数
     */
    int update(TagEntity tag);

    /**
     * 获得标签总数
     *
     * @return 数量
     */
    Integer countTag() ;

    /**
     * 获得标签列表
     *
     * @return 列表
     */
    List<TagEntity> listTag() ;


    /**
     * 根据标签名获取标签
     *
     * @param name 名称
     * @return 标签
     */
    TagEntity  getTagByName(String name) ;
}
