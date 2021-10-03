package com.lblog.blogbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lblog.blogbackend.model.entity.PersonalInfoEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PersonalInfoMapper extends BaseMapper<PersonalInfoEntity> {

    /**
     * 删除
     *
     * @param selfId 菜单ID
     * @return 影响行数
     */
    int deleteById(Integer selfId);

    /**
     * 添加
     * @param self 菜单
     * @return 影响行数
     */
    int insert(PersonalInfoEntity self);

    /**
     * 根据ID查询
     *
     * @param selfId 菜单ID
     * @return 菜单
     */
    PersonalInfoEntity getPersonalInfoById(Integer selfId);

    /**
     * 更新
     *
     * @param personalInfo 菜单
     * @return 影响行数
     */
    int update(PersonalInfoEntity personalInfo);

    /**
     * 获得菜单列表
     *
     * @return 列表
     */
    List<PersonalInfoEntity> listPersonalInfo() ;
}
