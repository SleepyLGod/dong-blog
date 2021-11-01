package com.lhd.mylblog.modules.admin.mapper;

import com.lhd.mylblog.modules.admin.model.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
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
public interface CommentMapper extends BaseMapper<Comment> {
    /**
     * 根据ID删除
     *
     * @param commentId 评论ID
     * @return 影响行数
     */
    int deleteById(Long commentId);

    /**
     * 添加
     *
     * @param comment 评论
     * @return 影响行数
     */
    int insert(Comment comment);

    /**
     * 根据ID查询
     *
     * @param commentId 评论ID
     * @return 评论
     */
    Comment getCommentById(Long commentId);

    /**
     * 更新
     *
     * @param comment 评论
     * @return 影响行数
     */
    int update(Comment comment);

    /**
     * 根据文章id获取评论列表
     *
     * @param id ID
     * @return 列表
     */
    List<Comment> listCommentByArticleId(@Param(value = "id") Long id);


    /**
     * 获得评论列表
     *
     * @return 列表
     */
    List<Comment> listComment(HashMap<String, Object> criteria);


    /**
     * 获得某个用户收到的评论
     *
     * @return 列表
     */
    List<Comment> getReceiveComment(List<Long> articleIds);


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
    List<Comment> listRecentComment(@Param(value = "userId") Long userId,
                                          @Param(value = "limit") Integer limit);

    /**
     * 获得评论的子评论
     *
     * @param id 评论ID
     * @return 列表
     */
    List<Comment> listChildComment(@Param(value = "id") Long id);


    /**
     * 根据用户ID删除
     *
     * @param userId 用户ID
     * @return 影响函数
     */
    Integer deleteByUserId(Long userId);


    /**
     * 根据文章ID删除
     *
     * @param articleId 文章ID
     * @return 影响函数
     */
    Integer deleteByArticleId(Long articleId);

}
