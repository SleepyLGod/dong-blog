package com.lblog.blogbackend.service.impl;

import com.lblog.blogbackend.model.entity.UserEntity;
import com.lblog.blogbackend.mapper.ArticleMapper;
import com.lblog.blogbackend.mapper.CommentMapper;
import com.lblog.blogbackend.mapper.UserMapper;
import com.lblog.blogbackend.service.ArticleService;
import com.lblog.blogbackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl  implements UserService {

    // 自动注入容器
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
        for(int i = 0; i < userEntities.size(); ++i) {
            // 个人博文数目
            Integer articleCount = articleMapper.countArticleByUser(userEntities.get(i).getUserId());
            userEntities.get(i).setArticleCount(articleCount);
        }
        return userEntities;
    }

    @Override
    public UserEntity getUserById(Integer id) {
        return userMapper.getUserById(id);
    }

    @Override
    public void updateUser(UserEntity user) {
        userMapper.update(user);
    }

    @Override
    public void deleteUser(Integer id) { // 分别删除用户信息，用户评论、用户博文
        userMapper.deleteById(id);
        commentMapper.deleteByUserId(id);
        if(articleMapper.listArticleIdsByUserId(id) != null &&
            articleMapper.listArticleIdsByUserId(id).size() >0) {
            for(Integer articleId : articleMapper.listArticleIdsByUserId(id)) {
                articleService.deleteArticle(articleId);
            }
        }
    }

    @Override
    public UserEntity insertUser(UserEntity user) {
        // 更新用户登录时间
        user.setUserRegisterTime(new Date());
        // 添加用户
        userMapper.insert(user);
        return user;
    }

    @Override
    public UserEntity getUserByNameOrEmail(String str) {
        return userMapper.getUserByNameOrEmail(str);
    }

    @Override
    public UserEntity getUserByNameOrTele(String str) {
        return userMapper.getUserByNameOrTele(str);
    }

    @Override
    public UserEntity getUserByName(String name) {
        return userMapper.getUserByName(name);
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    @Override
    public UserEntity getUserByTele(String tele) {
        return userMapper.getUserByTele(tele);
    }

}
