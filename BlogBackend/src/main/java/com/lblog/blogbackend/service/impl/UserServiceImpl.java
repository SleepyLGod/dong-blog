package com.lblog.blogbackend.service.impl;

import com.lblog.blogbackend.entity.UserEntity;
import com.lblog.blogbackend.mapper.ArticleMapper;
import com.lblog.blogbackend.mapper.CommentMapper;
import com.lblog.blogbackend.mapper.UserMapper;
import com.lblog.blogbackend.service.ArticleService;
import com.lblog.blogbackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<UserEntity> ListUser() {
        List<UserEntity> userEntities = userMapper.listUser();
        return null;
    }

    @Override
    public UserEntity getUserById(Integer id) {
        return null;
    }

    @Override
    public void updateUser(UserEntity user) {

    }

    @Override
    public void deleteUser(Integer id) {

    }

    @Override
    public UserEntity insetUser(UserEntity user) {
        return null;
    }

    @Override
    public UserEntity getUserByNameOrEmail(String str) {
        return null;
    }

    @Override
    public UserEntity getUserByNameOrTele(String str) {
        return null;
    }

    @Override
    public UserEntity getUserByName(String name) {
        return null;
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return null;
    }

    @Override
    public UserEntity getUserByTele(String tele) {
        return null;
    }
}
