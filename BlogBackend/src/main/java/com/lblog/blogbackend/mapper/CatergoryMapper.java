package com.lblog.blogbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lblog.blogbackend.entity.CategoryEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CatergoryMapper extends BaseMapper<CategoryEntity> {
}
