package com.lhd.mylblog.modules.admin.service;

import com.lhd.mylblog.modules.admin.model.User;
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
public interface UserService extends IService<User> {

    /**
     * 获得用户列表
     *
     * @return 用户列表
     */
    List<User> ListUser();

    /**
     * 根据id查询用户信息
     *
     * @param id 用户ID
     * @return 用户
     */
    User getUserById(Long id);

    /**
     * 修改用户信息
     *
     * @param user 用户
     */
    void updateUser(User user);

    /**
     * 删除用户
     *
     * @param id 用户ID
     */
    void deleteUser(Long id);

    /**
     * 添加用户
     *
     * @param user 用户
     * @return 用户
     */
    User insertUser(User user);

    /**
     * 根据用户名和邮箱查询用户
     *
     * @param str 用户名或Email
     * @return 用户
     */
    User getUserByNameOrEmail(String str);

    /**
     * 根据用户名和电话号码查询用户
     *
     * @param str 用户名或Tele
     * @return 用户
     */
    User getUserByNameOrTele(String str);

    /**
     * 根据用户名查询用户
     *
     * @param name 用户名
     * @return 用户
     */
    User getUserByName(String name);

    /**
     * 根据邮箱查询用户
     *
     * @param email Email
     * @return 用户
     */
    User getUserByEmail(String email);

    /**
     * 根据电话号码查询用户
     *
     * @param tele Tele
     * @return 用户
     */
    User getUserByTele(String tele);
}
