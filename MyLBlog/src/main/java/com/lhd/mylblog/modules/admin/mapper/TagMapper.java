package com.lhd.mylblog.modules.admin.mapper;

import com.lhd.mylblog.modules.admin.model.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 根据ID删除
     *
     * @param tagId 标签ID
     * @return 影响行数
     */
    int deleteById(Long tagId);

    /**
     * 添加
     *
     * @param tag 标签
     * @return 影响行数
     */
    @Override
    int insert(Tag tag);

    /**
     * 根据ID查询
     *
     * @param tagId 标签ID
     * @return 标签
     */
    Tag getTagById(Long tagId);

    /**
     * 更新
     * @param tag 标签
     * @return 影响行数
     */
    int update(Tag tag);

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
    List<Tag> listTag() ;


    /**
     * 根据标签名获取标签
     *
     * @param name 名称
     * @return 标签
     */
    Tag  getTagByName(String name) ;
}
