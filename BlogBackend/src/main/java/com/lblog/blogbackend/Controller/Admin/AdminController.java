package com.lblog.blogbackend.Controller.Admin;

import org.springframework.ui.Model;
import cn.hutool.json.JSONObject;
import com.lblog.blogbackend.constant.enums.UserRoleEnums;
import com.lblog.blogbackend.model.DTO.JsonReturnDTO;
import com.lblog.blogbackend.model.entity.ArticleEntity;
import com.lblog.blogbackend.model.entity.CommentEntity;
import com.lblog.blogbackend.model.entity.UserEntity;
import com.lblog.blogbackend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lblog.blogbackend.util.HttpUtils.getIpAddr;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    /*

    // adminPage
    @RequestMapping("/admin")
    public String index (Model model,HttpSession session) {
        UserEntity userEntity = (UserEntity) session.getAttribute("user");
        Integer userId = null;
        if(!UserRoleEnums.ADMIN.getValue().equals(userEntity.getUserRole())) {
            userId = userEntity.getUserId();
        }
        List<ArticleEntity> articleEntityList = articleService.listRecentArticle(userId,5);
        List<CommentEntity> commentEntityList = commentService.listRecentComment(userId,5);
        model.addAttribute("articleList",articleEntityList);
        model.addAttribute("commentList",commentEntityList);
        return "Admin/index";
    }

    // registerPage
    @RequestMapping("/register")
    public String registerPage() {
        return "Admin/register";
    }

    // loginPage
    @RequestMapping("/login")
    public String loginPage() {
        return "Admin/login";
    }

    */

    // registerVerify
    @RequestMapping(value = "/admin/registerVerify",method = RequestMethod.POST)
    @ResponseBody
    public JsonReturnDTO registerVerify(HttpServletRequest httpServletRequest) {
        String username = httpServletRequest.getParameter("username");
        String nickname = httpServletRequest.getParameter("nickname");
        String email = httpServletRequest.getParameter("email");
        String tele = httpServletRequest.getParameter("tele");
        String password = httpServletRequest.getParameter("password");
        UserEntity checkName = userService.getUserByName(username);
        if(checkName != null) {
            return new JsonReturnDTO().fail("用户名已存在!");
        }
        UserEntity checkEmail = userService.getUserByEmail(email);
        if(checkEmail != null) {
            return  new JsonReturnDTO().fail("电子邮箱已存在!");
        }
        UserEntity checkTele = userService.getUserByTele(tele);
        if(checkTele != null) {
            return new JsonReturnDTO().fail("电话号码已存在!");
        }
        // add users:
        UserEntity user = new UserEntity();
        // user.setUserAvatar("/img/avatar/avatar.png");
        user.setUserName(username);
        user.setUserNickname(nickname);
        user.setUserPass(password);
        user.setUserEmail(email);
        user.setUserTele(tele);
        user.setUserStatus(1);
        user.setArticleCount(0);
        user.setUserRole(UserRoleEnums.USER.getValue());
        try {
            userService.insertUser(user);
        }catch (Exception e) {
            e.printStackTrace();
            return new JsonReturnDTO().fail("System Error!");
        }
        return new JsonReturnDTO().success("注册成功!");
    }

    // loginVerify
    @RequestMapping(value = "/admin/loginVerify", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String loginVerify (HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Map<String,Object> stringObjectMap = new HashMap<String, Object>();
        String username = httpServletRequest.getParameter("username");
        String password = httpServletRequest.getParameter("password");
        String rememberme = httpServletRequest.getParameter("rememberme");
        UserEntity user = new UserEntity();
        user = userService.getUserByName(username);
        if(user == null) {
            stringObjectMap.put("isSuc",0);
            stringObjectMap.put("tip","用户名无效!");
        } else if(!user.getUserPass().equals(password)) {
            stringObjectMap.put("isSuc",0);
            stringObjectMap.put("tip","密码错误!");
        } else if(user.getUserStatus() == 0) {
            stringObjectMap.put("isSuc",0);
            stringObjectMap.put("tip","哈哈哈哈哈哈哈哈嗝你被封号了想想为什么");
        } else {
            stringObjectMap.put("isSuc",1);
            stringObjectMap.put("tip","");
            // add session:
            httpServletRequest.getSession().setAttribute("user",user);
            // add cookies:
            if (rememberme != null) {
                // add 2 cookies:
                Cookie nameCookie = new Cookie("username",username);
                Cookie pwdCookie  = new Cookie("password",password);
                // 有效期为7天
                nameCookie.setMaxAge(7*24*60*60);
                pwdCookie .setMaxAge(7*24*60*60);
                // 在该服务器下，任何项目，任何位置都能获取到cookie
                nameCookie.setPath("/");
                pwdCookie.setPath("/");
                httpServletResponse.addCookie(nameCookie);
                httpServletResponse.addCookie(pwdCookie);
            }
            user.setUserLastLoginTime(new Date());
            user.setUserIp(getIpAddr(httpServletRequest));
            userService.updateUser(user);
        }
        return new JSONObject(stringObjectMap).toString();
    }


    // logout:
    @RequestMapping(value = "/admin/logout")
    public JsonReturnDTO logout(HttpSession httpSession) {
        httpSession.removeAttribute("user");
        httpSession.invalidate();
        return new JsonReturnDTO().success("登出成功！再见！");
        // return "redirect:/loginVerify";
    }

    /*
    // 基本信息页面显示
    @RequestMapping(value = "/admin/personalProfile")
    public ModelAndView userPersonalProfile(HttpSession httpSession) {
        UserEntity sessionUser = (UserEntity) httpSession.getAttribute("user");
        UserEntity user = userService.getUserById(sessionUser.getUserId());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user",user);
        modelAndView.setViewName("Admin/User/personalProfile");
        return modelAndView;
    }

    // 编辑个人信息页面显示
    @RequestMapping(value = "/admin/personalProfile/edit")
    public ModelAndView userPersonalProfileEdition(HttpSession httpSession) {
        UserEntity loginUser = (UserEntity) httpSession.getAttribute("user");
        UserEntity user = userService.getUserById(loginUser.getUserId());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user",user);
        modelAndView.setViewName("Admin/User/editPersonalProfile");
        return modelAndView;
    }

    // 编辑用户提交页面显示
    @RequestMapping(value = "/admin/personalProfile/save")
    public String userPersonalProfileSave(UserEntity user, HttpSession httpSession) {
        UserEntity dbUser = (UserEntity) httpSession.getAttribute("user");
        user.setUserId(dbUser.getUserId());
        userService.updateUser(user);
        return "redirect:/admin/personalProfile";
    }
     */

    @RequestMapping(value = "/admin/personalProfile/save")
    public JsonReturnDTO userPersonalProfileSave(UserEntity user, HttpSession httpSession) {
        UserEntity dbUser = (UserEntity) httpSession.getAttribute("user");
        user.setUserId(dbUser.getUserId());
        userService.updateUser(user);
        return new JsonReturnDTO().success("编辑个人信息成功！");
    }

}
