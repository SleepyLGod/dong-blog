package com.lblog.blogbackend.Controller.Admin;

import cn.hutool.http.HtmlUtil;
import com.lblog.blogbackend.constant.enums.ArticleRoleEnums;
import com.lblog.blogbackend.constant.enums.ArticleStatusEnums;
import com.lblog.blogbackend.constant.enums.UserRoleEnums;
import com.lblog.blogbackend.model.DTO.JsonReturnDTO;
import com.lblog.blogbackend.model.entity.ArticleEntity;
import com.lblog.blogbackend.model.entity.CommentEntity;
import com.lblog.blogbackend.model.entity.UserEntity;
import com.lblog.blogbackend.service.ArticleService;
import com.lblog.blogbackend.service.CommentService;
import com.lblog.blogbackend.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;

@Controller
@RequestMapping("/admin/comment")
public class CommentBackendController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleService articleService;

    /*
    // 发送的评论
    @RequestMapping(value = "")
    public String commentList(HttpSession session, Model model) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        HashMap<String, Object> criteria = new HashMap<>();
        if (!UserRoleEnums.ADMIN.getValue().equals(user.getUserRole())) {
            // 用户查询自己的文章, 管理员查询所有的
            criteria.put("userId", user.getUserId());
        }
        return "Admin/Comment/index";
    }

    //收到的评论
    @RequestMapping(value = "/receive")
    public String myReceiveComment(HttpSession session, Model model) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        return "Admin/Comment/index";
    }
    */

    // 添加评论
    @RequestMapping(value = "/insert", method = {RequestMethod.POST}, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public void insertComment(HttpServletRequest request, CommentEntity comment, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        ArticleEntity article = articleService.getArticleByStatusAndId(null, comment.getCommentArticleId());
        if (article == null) {
            return;
        }
        //添加评论
        comment.setCommentUserId(user.getUserId());
        comment.setCommentIp(HttpUtils.getIpAddr(request));
        comment.setCommentCreateTime(new Date());
        commentService.insertComment(comment);
        //更新文章的评论数
        articleService.updateCommentCount(article.getArticleId());
    }

    // 删除评论
    @RequestMapping(value = "/delete/{id}")
    public void deleteComment(@PathVariable("id") Integer id, HttpSession session) {
        CommentEntity comment = commentService.getCommentById(id);
        UserEntity user = (UserEntity) session.getAttribute("user");
        // 如果不是管理员，访问其他用户的数据，没有权限
        if (!Objects.equals(user.getUserRole(), UserRoleEnums.ADMIN.getValue()) && !Objects.equals(comment.getCommentUserId(), user.getUserId())) {
            return;
        }
        //删除评论
        commentService.deleteComment(id);
        //删除其子评论
        List<CommentEntity> childCommentList = commentService.listChildComment(id);
        for (int i = 0; i < childCommentList.size(); i++) {
            commentService.deleteComment(childCommentList.get(i).getCommentId());
        }
        //更新文章的评论数
        ArticleEntity article = articleService.getArticleByStatusAndId(null, comment.getCommentArticleId());
        articleService.updateCommentCount(article.getArticleId());
    }

    /*
    // 编辑评论页面
    @RequestMapping(value = "/edit/{id}")
    public String editCommentView(@PathVariable("id") Integer id, Model model, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        if (!Objects.equals(user.getUserRole(), UserRoleEnums.ADMIN.getValue())) { // 没有权限操作,只有管理员可以操作
            return "redirect:/403";
        }
        CommentEntity comment = commentService.getCommentById(id);
        model.addAttribute("comment", comment);
        return "Admin/Comment/edit";
    }
     */


    // 编辑评论提交
    @RequestMapping(value = "/editSubmit", method = RequestMethod.POST)
    public JsonReturnDTO editCommentSubmit(CommentEntity comment, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        if (!Objects.equals(user.getUserRole(), UserRoleEnums.ADMIN.getValue())) { // 没有权限操作,只有管理员可以操作
            return new JsonReturnDTO().fail("403");
            // return "redirect:/403";
        }
        commentService.updateComment(comment);
        return new JsonReturnDTO().success();
        // return "redirect:/admin/comment";
    }

    /*
    // 回复评论页面
    @RequestMapping(value = "/reply/{id}")
    public String replyCommentView(@PathVariable("id") Integer id, Model model) {
        CommentEntity comment = commentService.getCommentById(id);
        model.addAttribute("comment", comment);
        return "Admin/Comment/reply";
    }
    */

    // 回复评论提交
    @RequestMapping(value = "/replySubmit", method = RequestMethod.POST)
    public JsonReturnDTO replyCommentSubmit(HttpServletRequest request, CommentEntity comment, HttpSession session) {
        //文章评论数+1
        ArticleEntity article = articleService.getArticleByStatusAndId(ArticleStatusEnums.PUBLISHED.getValue(), comment.getCommentArticleId());
        if (article == null) {
            return new JsonReturnDTO().fail("404");
            // return "redirect:/404";
        }
        UserEntity user = (UserEntity) session.getAttribute("user");
        comment.setCommentContent(HtmlUtil.escape(comment.getCommentContent()));
        comment.setCommentAuthorName(user.getUserNickname());
        comment.setCommentAuthorEmail(user.getUserEmail());
        comment.setCommentAuthorUrl(user.getUserUrl());
        article.setArticleCommentCount(article.getArticleCommentCount() + 1);
        articleService.updateArticle(article);
        //添加评论
        comment.setCommentCreateTime(new Date());
        comment.setCommentIp(HttpUtils.getIpAddr(request));
        if (Objects.equals(user.getUserId(), article.getArticleUserId())) {
            comment.setCommentRole(ArticleRoleEnums.OWNER.getValue());
        } else {
            comment.setCommentRole(ArticleRoleEnums.VISITOR.getValue());
        }
        commentService.insertComment(comment);
        return new JsonReturnDTO().success();
        // return "redirect:/admin/comment";
    }

}
