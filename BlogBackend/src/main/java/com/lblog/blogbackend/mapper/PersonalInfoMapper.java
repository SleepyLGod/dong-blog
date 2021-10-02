package com.lblog.blogbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lblog.blogbackend.entity.PersonalInfoEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PersonalInfoMapper extends BaseMapper<PersonalInfoEntity> {
}
