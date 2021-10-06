package com.lblog.blogbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lblog.blogbackend.model.entity.CategoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CategoryMapper extends BaseMapper<CategoryEntity> {

    /**
     * 添加
     *
     * @param category 分类
     * @return 影响行数
     */
    int insert(CategoryEntity category);


    /**
     * 更新
     *
     * @param category 分类
     * @return 影响行数
     */
    int update(CategoryEntity category);

    /**
     * 根据分类id获得分类信息
     *
     * @param id ID
     * @return 分类
     */
    CategoryEntity getCategoryById(Integer id);


    /**
     * 删除分类
     *
     * @param id 文章ID
     */
    int deleteCategory(Integer id);

    /**
     * 查询分类总数
     *
     * @return 数量
     */
    Integer countCategory();

    /**
     * 获得分类列表
     *
     * @return 列表
     */
    List<CategoryEntity> listCategory();

    /**
     * 根据父分类找子分类
     *
     * @param id 分类ID
     * @return 列表
     */
    List<CategoryEntity> findChildCategory(@Param(value = "id") Integer id);

    /**
     * 根据标签名获取标签
     *
     * @param name 名称
     * @return 分类
     */
    CategoryEntity getCategoryByName(String name);
}
