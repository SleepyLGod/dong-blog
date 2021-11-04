package com.lhd.mylblog.modules.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.EncryptUtils;
import com.lhd.mylblog.common.api.ResultCode;
import com.lhd.mylblog.common.exception.Asserts;
import com.lhd.mylblog.modules.admin.mapper.ArticleMapper;
import com.lhd.mylblog.modules.admin.mapper.CommentMapper;
import com.lhd.mylblog.modules.admin.model.Article;
import com.lhd.mylblog.modules.admin.model.User;
import com.lhd.mylblog.modules.admin.mapper.UserMapper;
import com.lhd.mylblog.modules.admin.service.ArticleService;
import com.lhd.mylblog.modules.admin.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.stereotype.Service;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author generator
 * @since 2021-10-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<User> ListUser() {
        List<User> userEntities = userMapper.listUser();
        List<User> returnUserEntities = null;
        for(int i = 0; i < userEntities.size(); ++i) {
            User user = userEntities.get(i);
            // 原始时间 2021-10-01 00:00:00
            SimpleDateFormat sdf = new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
            ParsePosition pos = new ParsePosition(0);
            Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
            if(user.getUserDeletedTime().equals(date)) {
                // 个人博文数目
                Integer articleCount = articleMapper.countArticleByUser(user.getId());
                user.setArticleCount(articleCount);
                returnUserEntities.add(user);
            }
        }
        return returnUserEntities;
    }

    @Override
    public User getUserById(Long id) {
        User user = userMapper.getUserById(id);
        // 原始时间 2021-10-01 00:00:00
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        ParsePosition pos = new  ParsePosition(0);
        Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
        if(!user.getUserDeletedTime().equals(date)) {
            user = null;
        }
        return user;
    }

    @Override
    public void updateUser(User user) {
        userMapper.update(user);
    }

    @Override
    public void deleteUser(Long id) {
        Date userDeletedTimeNow = new Date();
        User user = userMapper.getUserById(id);
        user.setUserDeletedTime(userDeletedTimeNow);
        // 评论无需保存，物理删除
        commentMapper.deleteByUserId(id);
        if(articleMapper.listArticleIdsByUserId(id) != null &&
                articleMapper.listArticleIdsByUserId(id).size() >0) {
            for(Long articleId : articleMapper.listArticleIdsByUserId(id)) {
                // 删掉草稿和正式文章
                Article article = articleMapper.getArticleByStatusAndId(0,articleId);
                Article article1= articleMapper.getArticleByStatusAndId(1,articleId);
                article.setArticleDeletedTime(userDeletedTimeNow);
                article1.setArticleDeletedTime(userDeletedTimeNow);
            }
        }
    }

    @Override
    public User insertUser(User user) {
        // 添加用户
        // userMapper.insert(user);
        if (save(user)) {
            return user;
        } else {
            Asserts.fail(ResultCode.FAILED);
            return null;
        }
    }

    @Override
    public User getUserByNameOrEmail(String str) {
        User user = userMapper.getUserByNameOrEmail(str);
        // 原始时间 2021-10-01 00:00:00
        SimpleDateFormat sdf = new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        ParsePosition pos = new ParsePosition(0);
        Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
        if(!user.getUserDeletedTime().equals(date)) {
            user = null;
        }
        return user;
    }

    @Override
    public User getUserByNameOrTele(String str) {
        User user = userMapper.getUserByNameOrTele(str);
        // 原始时间 2021-10-01 00:00:00
        SimpleDateFormat sdf = new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        ParsePosition pos = new ParsePosition(0);
        Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
        if(!user.getUserDeletedTime().equals(date)) {
            user = null;
        }
        return user;
    }

    @Override
    public User getUserByName(String name) {
        User user = userMapper.getUserByName(name);
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
    public User getUserByEmail(String email) {
        User user = userMapper.getUserByEmail(email);
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
    public User getUserByTele(String tele) {
        User user = userMapper.getUserByTele(tele);
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
