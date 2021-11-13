package com.lhd.mylblog.modules.admin.service.impl;

import com.lhd.mylblog.common.enums.ArticleStatus;
import com.lhd.mylblog.modules.admin.mapper.ArticleMapper;
import com.lhd.mylblog.modules.admin.mapper.TagMapper;
import com.lhd.mylblog.modules.admin.model.Article;
import com.lhd.mylblog.modules.admin.model.Comment;
import com.lhd.mylblog.modules.admin.mapper.CommentMapper;
import com.lhd.mylblog.modules.admin.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
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
@Slf4j
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TagMapper tagMapper;

    @Override
    public void insertComment(Comment comment) {
        try {
            commentMapper.insert(comment);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("评论创建失败：comment:{}, cause:{}", comment, e);
        }
    }

    @Override
    public List<Comment> listCommentByArticleId(Long articleId) {
        List<Comment> commentEntities = null;
        Article article = articleMapper.getArticleByStatusAndId(1,articleId);
        // 原始时间 2021-10-01 00:00:00
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        ParsePosition pos = new ParsePosition(0);
        Date date = sdf.parse( " 2021-10-01 00:00:00 " ,pos);
        if(article.getArticleDeletedTime().equals(date)) {
            try {
                commentEntities = commentMapper.listCommentByArticleId(articleId);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("根据文章ID获得评论列表失败，articleId:{},cause:{}", articleId, e);
            }
        }
        return commentEntities;
    }

    @Override
    public Comment getCommentById(Long id) {
        Comment comment = null;
        try {
            comment = commentMapper.getCommentById(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据评论ID获得评论，id:{}, cause:{}", id, e);
        }
        return comment;
    }

    @Override
    public void deleteComment(Long id) {
        try {
            commentMapper.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("评论删除失败, id:{}, cause:{}", id, e);
        }
    }

    @Override
    public void updateComment(Comment comment) {
        try {
            commentMapper.update(comment);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("更新评论，comment:{}, cause:{}", comment, e);
        }
    }

    @Override
    public Integer countComment() {
        Integer commentCnt = null;
        try {
            commentCnt = commentMapper.countComment();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("评论数统计失败, cause:{}", e);
        }
        return commentCnt;
    }

    @Override
    public List<Comment> listRecentComment(Long userId, Integer limit) {
        List<Comment> commentEntities = null;
        try {
            commentEntities = commentMapper.listRecentComment(userId, limit);
            for (int i = 0; i < commentEntities.size(); ++i) { // 草稿没有评论
                Article article = articleMapper.getArticleByStatusAndId(ArticleStatus.PUBLISHED.getValue(), commentEntities.get(i).getCommentArticleId());
                commentEntities.get(i).setArticle(article);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("最新评论获取失败, limit:{}, cause:{}", limit, e);
        }
        return commentEntities;
    }

    @Override
    public List<Comment> listChildComment(Long id) {
        List<Comment> childCommentEntities = null;
        try {
            childCommentEntities = commentMapper.listChildComment(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("子评论获取失败, id:{}, cause:{}", id, e);
        }
        return childCommentEntities;
    }
}
