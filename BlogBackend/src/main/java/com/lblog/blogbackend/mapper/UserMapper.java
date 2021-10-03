package com.lblog.blogbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lblog.blogbackend.model.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

    /**
     * 根据ID删除用户
     *
     * @param userId 用户ID
     * @return 影响行数
     */
    int deleteById(Integer userId);

    /**
     * 添加用户
     *
     * @param user 用户
     * @return 影响行数
     */
    int insert(UserEntity user);

    /**
     * 根据ID查询
     *
     * @param userId 用户ID
     * @return 用户
     */
    UserEntity getUserById(Integer userId);

    /**
     * 更新
     *
     * @param user 用户
     * @return 影响行数
     */
    int update(UserEntity user);

    /**
     * 获得用户列表
     *
     * @return  用户列表
     */
    List<UserEntity> listUser();

    /**
     * 根据用户名或Email获得用户
     *
     * @param str 用户名或Email
     * @return 用户
     */
    UserEntity getUserByNameOrEmail(String str);

    /**
     * 根据用户名查用户
     *
     * @param name 用户名
     * @return 用户
     */
    UserEntity getUserByName(String name);

    /**
     * 根据Email查询用户
     *
     * @param email 邮箱
     * @return 用户
     */
    UserEntity getUserByEmail(String email);

    /**
     * 根据用户名和电话号码查询用户
     *
     * @param str 用户名或Tele
     * @return 用户
     */
    UserEntity getUserByNameOrTele(String str);

    /**
     * 根据电话号码查询用户
     *
     * @param tele Tele
     * @return 用户
     */
    UserEntity getUserByTele(String tele);

}
