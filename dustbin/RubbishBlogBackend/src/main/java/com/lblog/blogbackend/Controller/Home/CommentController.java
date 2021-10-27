package com.lblog.blogbackend.Controller.Home;

import cn.hutool.http.HtmlUtil;
import cn.hutool.http.HttpUtil;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Objects;

@Controller
@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleService articleService;

    // 添加评论
    @RequestMapping(value = "/comment", method = {RequestMethod.POST})
    public JsonReturnDTO insertComment(HttpServletRequest request, CommentEntity comment, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        if (user == null) {
            return new JsonReturnDTO().fail("请先登录");
        }
        ArticleEntity article = articleService.getArticleByStatusAndId(ArticleStatusEnums.PUBLISHED.getValue(), comment.getCommentArticleId());
        if (article == null) {
            return new JsonReturnDTO().fail("文章不存在");
        }
        comment.setCommentUserId(user.getUserId());
        comment.setCommentCreateTime(new Date());
        comment.setCommentIp(HttpUtils.getIpAddr(request));
        if (Objects.equals(user.getUserId(), article.getArticleUserId())) {
            comment.setCommentRole(ArticleRoleEnums.OWNER.getValue());
        } else {
            comment.setCommentRole(ArticleRoleEnums.VISITOR.getValue());
        }
        comment.setCommentAuthorAvatar(user.getUserAvatar());
        //过滤字符，防止XSS攻击
        comment.setCommentContent(HtmlUtil.escape(comment.getCommentContent()));
        comment.setCommentAuthorName(user.getUserNickname());
        comment.setCommentAuthorEmail(user.getUserEmail());
        comment.setCommentAuthorUrl(user.getUserUrl());
        try {
            commentService.insertComment(comment);
            //更新文章的评论数
            articleService.updateCommentCount(article.getArticleId());
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonReturnDTO().fail();
        }
        return new JsonReturnDTO().success();
    }


}