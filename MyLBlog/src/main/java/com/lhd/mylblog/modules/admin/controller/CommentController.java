package com.lhd.mylblog.modules.admin.controller;


import cn.hutool.http.HtmlUtil;
import com.lhd.mylblog.common.api.CommonResult;
import com.lhd.mylblog.common.api.ResultCode;
import com.lhd.mylblog.common.enums.ArticleRole;
import com.lhd.mylblog.common.enums.ArticleStatus;
import com.lhd.mylblog.common.enums.UserRole;
import com.lhd.mylblog.modules.admin.mapper.UserMapper;
import com.lhd.mylblog.modules.admin.model.Article;
import com.lhd.mylblog.modules.admin.model.Comment;
import com.lhd.mylblog.modules.admin.model.User;
import com.lhd.mylblog.modules.admin.service.ArticleService;
import com.lhd.mylblog.modules.admin.service.CommentService;
import com.lhd.mylblog.utils.HttpUtils;
import com.lhd.mylblog.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author generator
 * @since 2021-10-28
 */
@RestController
@Api
@RequestMapping("/admin/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    JwtUtils jwtUtils;

    /**
     * 添加评论
     *
     * @param request
     * @param comment
     */
    @ApiOperation(value = "添加评论", tags = {"comment"}, notes = "post")
    @PostMapping(value = "/insert", produces = {"text/plain;charset=UTF-8"})
    public void insertComment(HttpServletRequest request, @ApiParam(name = "comment", required = true) @RequestBody Comment comment) {
        User user = userMapper.selectById(jwtUtils.getUserIdFromToken(request));
        Article article = articleService.getArticleByStatusAndId(null, comment.getCommentArticleId());
        if (article == null) {
            return;
        }
        //添加评论
        comment.setCommentUserId(user.getId());
        comment.setCommentIp(HttpUtils.getIpAddr(request));
        comment.setCommentCreateTime(new Date());
        commentService.insertComment(comment);
        //更新文章的评论数
        articleService.updateCommentCount(article.getArticleId());
    }

    /**
     * 删除评论
     *
     * @param id
     * @param request
     */
    @ApiOperation(value = "删除评论", tags = {"comment"}, notes = "post")
    @PostMapping(value = "/delete/{id}")
    public void deleteComment(@ApiParam(name = "commentId", required = true) @PathVariable("id") Long id, HttpServletRequest request) {
        Comment comment = commentService.getCommentById(id);
        User user = userMapper.selectById(jwtUtils.getUserIdFromToken(request));
        // 如果不是管理员，访问其他用户的数据，没有权限
        if (!Objects.equals(user.getUserRole(), UserRole.ADMIN.getValue()) && !Objects.equals(comment.getCommentUserId(), user.getId())) {
            return;
        }
        //删除评论
        commentService.deleteComment(id);
        //删除其子评论
        List<Comment> childCommentList = commentService.listChildComment(id);
        for (int i = 0; i < childCommentList.size(); ++i) {
            commentService.deleteComment(childCommentList.get(i).getCommentId());
        }
        //更新文章的评论数
        Article article = articleService.getArticleByStatusAndId(null, comment.getCommentArticleId());
        articleService.updateCommentCount(article.getArticleId());
    }

    /**
     * 编辑评论提交
     *
     * @param comment
     * @param request
     * @return
     */
    @ApiOperation(value = "编辑评论提交", tags = {"comment"}, notes = "post")
    @PostMapping(value = "/edit")
    public CommonResult editCommentSubmit(@ApiParam(name = "comment", required = true) @RequestBody Comment comment, HttpServletRequest request) {
        User user = userMapper.selectById(jwtUtils.getUserIdFromToken(request));
        /*
        if (!Objects.equals(user.getUserRole(), UserRole.ADMIN.getValue())) { // 没有权限操作,只有管理员可以操作
            return CommonResult.failed(ResultCode.FORBIDDEN);
        }
        */
        commentService.updateComment(comment);
        return CommonResult.success(ResultCode.SUCCESS);
    }

    /**
     * 回复评论提交
     *
     * @param comment
     * @param request
     * @return
     */
    @ApiOperation(value = "回复评论提交", tags = {"comment"}, notes = "post")
    @PostMapping(value = "/reply")
    public CommonResult replyCommentSubmit(@ApiParam(name = "comment", required = true) @RequestBody Comment comment, HttpServletRequest request) {
        //文章评论数+1
        Article article = articleService.getArticleByStatusAndId(ArticleStatus.PUBLISHED.getValue(), comment.getCommentArticleId());
        if (article == null) {
            return CommonResult.failed(ResultCode.ARTICLE_NOT_PUBLISHED);
        }
        User user = userMapper.selectById(jwtUtils.getUserIdFromToken(request));
        //过滤字符，防止XSS攻击
        comment.setCommentContent(HtmlUtil.escape(comment.getCommentContent()));
        comment.setCommentAuthorName(user.getNickname());
        comment.setCommentAuthorEmail(user.getEmail());
        article.setArticleCommentCount(article.getArticleCommentCount() + 1);
        articleService.updateArticle(article);
        //添加评论
        comment.setCommentCreateTime(new Date());
        comment.setCommentIp(HttpUtils.getIpAddr(request));
        if (Objects.equals(user.getId(), article.getArticleUserId())) {
            comment.setCommentRole(ArticleRole.OWNER.getValue());
        } else {
            comment.setCommentRole(ArticleRole.VISITOR.getValue());
        }
        commentService.insertComment(comment);
        return CommonResult.success(ResultCode.SUCCESS);
    }

}

