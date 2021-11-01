package com.lhd.mylblog.modules.admin.mapper;

import com.lhd.mylblog.modules.admin.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

import javax.management.MXBean;
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
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据ID删除用户
     *
     * @param userId 用户ID
     * @return 影响行数
     */
    int deleteById(Long userId);

    /**
     * 添加用户
     *
     * @param user 用户
     * @return 影响行数
     */
    @Override
    int insert(User user);

    /**
     * 根据ID查询
     *
     * @param userId 用户ID
     * @return 用户
     */
    User getUserById(Long userId);

    /**
     * 更新
     *
     * @param user 用户
     * @return 影响行数
     */
    int update(User user);

    /**
     * 获得用户列表
     *
     * @return  用户列表
     */
    List<User> listUser();

    /**
     * 根据用户名或Email获得用户
     *
     * @param str 用户名或Email
     * @return 用户
     */
    User getUserByNameOrEmail(String str);

    /**
     * 根据用户名查用户
     *
     * @param name 用户名
     * @return 用户
     */
    User getUserByName(String name);

    /**
     * 根据Email查询用户
     *
     * @param email 邮箱
     * @return 用户
     */
    User getUserByEmail(String email);

    /**
     * 根据用户名和电话号码查询用户
     *
     * @param str 用户名或Tele
     * @return 用户
     */
    User getUserByNameOrTele(String str);

    /**
     * 根据电话号码查询用户
     *
     * @param tele Tele
     * @return 用户
     */
    User getUserByTele(String tele);
}
