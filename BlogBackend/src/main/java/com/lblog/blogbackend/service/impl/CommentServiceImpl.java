package com.lblog.blogbackend.service.impl;

import com.lblog.blogbackend.constant.enums.ArticleStatusEnums;
import com.lblog.blogbackend.mapper.ArticleMapper;
import com.lblog.blogbackend.mapper.CommentMapper;
import com.lblog.blogbackend.mapper.TagMapper;
import com.lblog.blogbackend.model.entity.ArticleEntity;
import com.lblog.blogbackend.model.entity.CommentEntity;
import com.lblog.blogbackend.model.entity.TagEntity;
import com.lblog.blogbackend.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TagMapper tagMapper;

    @Override
    public void insertComment(CommentEntity comment) {
        try {
            commentMapper.insert(comment);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("评论创建失败：comment:{}, cause:{}", comment, e);
        }
    }

    @Override
    public List<CommentEntity> listCommentByArticleId(Integer articleId) {
        List<CommentEntity> commentEntities = null;
        try {
            commentEntities = commentMapper.listCommentByArticleId(articleId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据文章ID获得评论列表失败，articleId:{},cause:{}", articleId, e);
        }
        return commentEntities;
    }

    @Override
    public CommentEntity getCommentById(Integer id) {
        CommentEntity comment = null;
        try {
            comment = commentMapper.getCommentById(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据评论ID获得评论，id:{}, cause:{}", id, e);
        }
        return comment;
    }

    @Override
    public void deleteComment(Integer id) {
        try {
            commentMapper.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("评论删除失败, id:{}, cause:{}", id, e);
        }
    }

    @Override
    public void updateComment(CommentEntity comment) {
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
    public List<CommentEntity> listRecentComment(Integer userId, Integer limit) {
        List<CommentEntity> commentEntities = null;
        try {
            commentEntities = commentMapper.listRecentComment(userId, limit);
            for (int i = 0; i < commentEntities.size(); ++i) { // 草稿没有评论
                ArticleEntity article = articleMapper.getArticleByStatusAndId(ArticleStatusEnums.PUBLISHED.getValue(), commentEntities.get(i).getCommentArticleId());
                commentEntities.get(i).setArticle(article);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("最新评论获取失败, limit:{}, cause:{}", limit, e);
        }
        return commentEntities;
    }

    @Override
    public List<CommentEntity> listChildComment(Integer id) {
        List<CommentEntity> childCommentEntities = null;
        try {
            childCommentEntities = commentMapper.listChildComment(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("子评论获取失败, id:{}, cause:{}", id, e);
        }
        return childCommentEntities;
    }
}
