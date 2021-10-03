package com.lblog.blogbackend.service;

import com.lblog.blogbackend.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    /**
     * 获得用户列表
     *
     * @return 用户列表
     */
    List<UserEntity> ListUser();

    /**
     * 根据id查询用户信息
     *
     * @param id 用户ID
     * @return 用户
     */
    UserEntity getUserById(Integer id);

    /**
     * 修改用户信息
     *
     * @param user 用户
     */
    void updateUser(UserEntity user);

    /**
     * 删除用户
     *
     * @param id 用户ID
     */
    void deleteUser(Integer id);

    /**
     * 添加用户
     *
     * @param user 用户
     * @return 用户
     */
    UserEntity insertUser(UserEntity user);

    /**
     * 根据用户名和邮箱查询用户
     *
     * @param str 用户名或Email
     * @return 用户
     */
    UserEntity getUserByNameOrEmail(String str);

    /**
     * 根据用户名和电话号码查询用户
     *
     * @param str 用户名或Tele
     * @return 用户
     */
    UserEntity getUserByNameOrTele(String str);

    /**
     * 根据用户名查询用户
     *
     * @param name 用户名
     * @return 用户
     */
    UserEntity getUserByName(String name);

    /**
     * 根据邮箱查询用户
     *
     * @param email Email
     * @return 用户
     */
    UserEntity getUserByEmail(String email);

    /**
     * 根据电话号码查询用户
     *
     * @param tele Tele
     * @return 用户
     */
    UserEntity getUserByTele(String tele);
}

