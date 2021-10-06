package com.lblog.blogbackend.service.impl;

import com.lblog.blogbackend.model.entity.ArticleEntity;
import com.lblog.blogbackend.model.entity.CommentEntity;
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

import javax.annotation.Resource;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
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
        List<UserEntity> returnUserEntities = null;
        for(int i = 0; i < userEntities.size(); ++i) {
            UserEntity user = userEntities.get(i);
            // 原始时间 2021-10-01 00:00:00
            SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
            ParsePosition pos = new ParsePosition(0);
            Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
            if(user.getUserDeletedTime().equals(date)) {
                // 个人博文数目
                Integer articleCount = articleMapper.countArticleByUser(user.getUserId());
                user.setArticleCount(articleCount);
                returnUserEntities.add(user);
            }
        }
        return returnUserEntities;
    }

    @Override
    public UserEntity getUserById(Integer id) {
        UserEntity user = userMapper.getUserById(id);
        // 原始时间 2021-10-01 00:00:00
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        ParsePosition pos = new ParsePosition(0);
        Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
        if(!user.getUserDeletedTime().equals(date)) {
            user = null;
        }
        return user;
    }

    @Override
    public void updateUser(UserEntity user) {
        userMapper.update(user);
    }

    @Override
    public void deleteUser(Integer id) { // 分别删除用户信息，用户评论、用户博文
        Date userDeletedTimeNow = new Date();
        UserEntity user = userMapper.getUserById(id);
        user.setUserDeletedTime(userDeletedTimeNow);
        commentMapper.deleteByUserId(id); // 评论无需保存，物理删除
        if(articleMapper.listArticleIdsByUserId(id) != null &&
            articleMapper.listArticleIdsByUserId(id).size() >0) {
            for(Integer articleId : articleMapper.listArticleIdsByUserId(id)) { // 删掉草稿和正式文章
                ArticleEntity article = articleMapper.getArticleByStatusAndId(0,articleId);
                ArticleEntity article1= articleMapper.getArticleByStatusAndId(1,articleId);
                article.setArticleDeletedTime(userDeletedTimeNow);
                article1.setArticleDeletedTime(userDeletedTimeNow);
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
        UserEntity  user = userMapper.getUserByNameOrEmail(str);
        // 原始时间 2021-10-01 00:00:00
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        ParsePosition pos = new ParsePosition(0);
        Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
        if(!user.getUserDeletedTime().equals(date)) {
            user = null;
        }
        return user;
    }

    @Override
    public UserEntity getUserByNameOrTele(String str) {
        UserEntity user = userMapper.getUserByNameOrTele(str);
        // 原始时间 2021-10-01 00:00:00
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        ParsePosition pos = new ParsePosition(0);
        Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
        if(!user.getUserDeletedTime().equals(date)) {
            user = null;
        }
        return user;
    }

    @Override
    public UserEntity getUserByName(String name) {
        UserEntity user = userMapper.getUserByName(name);
        // 原始时间 2021-10-01 00:00:00
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        ParsePosition pos = new ParsePosition(0);
        Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
        if(!user.getUserDeletedTime().equals(date)) {
            user = null;
        }
        return user;
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        UserEntity user = userMapper.getUserByEmail(email);
        // 原始时间 2021-10-01 00:00:00
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        ParsePosition pos = new ParsePosition(0);
        Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
        if(!user.getUserDeletedTime().equals(date)) {
            user = null;
        }
        return user;
    }

    @Override
    public UserEntity getUserByTele(String tele) {
        UserEntity user = userMapper.getUserByTele(tele);
        // 原始时间 2021-10-01 00:00:00
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        ParsePosition pos = new ParsePosition(0);
        Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
        if(!user.getUserDeletedTime().equals(date)) {
            user = null;
        }
        return user;
    }

}
