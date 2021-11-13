package com.lhd.mylblog.modules.admin.service;

import com.lhd.mylblog.modules.admin.model.Comment;
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
public interface CommentService extends IService<Comment> {
    /**
     * 添加评论
     *
     * @param comment 评论
     */
    void insertComment(Comment comment);

    /**
     * 根据文章id获取评论列表
     *
     * @param articleId 文章ID
     * @return 列表
     */
    List<Comment> listCommentByArticleId(Long articleId);

    /**
     * 根据id获取评论
     *
     * @param id
     * @return
     */
    Comment getCommentById(Long id);

    /**
     * 删除评论
     *
     * @param id ID
     */
    void deleteComment(Long id);

    /**
     * 修改评论
     *
     * @param comment 评论
     */
    void updateComment(Comment comment);

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
    List<Comment> listRecentComment(Long userId, Integer limit);

    /**
     * 获得评论的子评论
     *
     * @param id 评论ID
     * @return 列表
     */
    List<Comment> listChildComment(Long id);

}
