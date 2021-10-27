package com.lblog.blogbackend.service;

import com.lblog.blogbackend.model.entity.CommentEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    /**
     * 添加评论
     *
     * @param comment 评论
     */
    void insertComment(CommentEntity comment);

    /**
     * 根据文章id获取评论列表
     *
     * @param articleId 文章ID
     * @return 列表
     */
    List<CommentEntity> listCommentByArticleId(Integer articleId);

    /**
     * 根据id获取评论
     *
     * @param id
     * @return
     */
    CommentEntity getCommentById(Integer id);

    /**
     * 删除评论
     *
     * @param id ID
     */
    void deleteComment(Integer id);

    /**
     * 修改评论
     *
     * @param comment 评论
     */
    void updateComment(CommentEntity comment);

    /**
     * 统计评论数
     *
     * @return 数量
     */
    Integer countComment();

    /**
     * 获得最近评论
     *
     * @param limit 查询数量
     * @return 列表
     */
    List<CommentEntity> listRecentComment(Integer userId, Integer limit);

    /**
     * 获得评论的子评论
     *
     * @param id 评论ID
     * @return 列表
     */
    List<CommentEntity> listChildComment(Integer id);
}
