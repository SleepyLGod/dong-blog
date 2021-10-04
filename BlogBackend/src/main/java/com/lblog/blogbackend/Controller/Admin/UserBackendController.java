package com.lblog.blogbackend.Controller.Admin;

import cn.hutool.json.JSONObject;
import com.lblog.blogbackend.constant.enums.UserRoleEnums;
import com.lblog.blogbackend.model.entity.UserEntity;
import com.lblog.blogbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/user")
public class UserBackendController {

    @Autowired
    private UserService userService;

    // 后台用户列表显示
    @RequestMapping(value = "")
    public ModelAndView userEntityList() {
        ModelAndView modelandview = new ModelAndView();
        List<UserEntity> userEntityList = userService.ListUser();
        modelandview.addObject("userList", userEntityList);
        modelandview.setViewName("Admin/User/index");
        return modelandview;
    }

    // 检查用户名是否存在
    @RequestMapping(value = "/checkUserName", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String checkUserName(HttpServletRequest request) {
        Map<String, Object> stringObjectHashMap = new HashMap<String, Object>();
        String username = request.getParameter("username");
        UserEntity user = userService.getUserByName(username);
        int id = Integer.valueOf(request.getParameter("id"));
        if (user != null) {
            if (user.getUserId() != id) { // 用户名已存在,但不是当前用户(编辑用户的时候，不提示)
                stringObjectHashMap.put("isFailed", 1);
                stringObjectHashMap.put("tip", "用户名已存在！");
            }
        } else {
            stringObjectHashMap.put("isFailed", 0);
            stringObjectHashMap.put("tip", "");
        }
        return new JSONObject(stringObjectHashMap).toString();
    }

    // 检查Email是否存在
    @RequestMapping(value = "/checkUserEmail", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String checkUserEmail(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        String email = request.getParameter("email");
        UserEntity user = userService.getUserByEmail(email);
        int id = Integer.valueOf(request.getParameter("id"));
        if (user != null) {
            if (user.getUserId() != id) { // 用户名已存在,但不是当前用户(编辑用户的时候，不提示)
                map.put("isFailed", 1);
                map.put("tip", "电子邮箱已存在！");
            }
        } else {
            map.put("isFailed", 0);
            map.put("tip", "");
        }
        return new JSONObject(map).toString();
    }

    // 后台添加用户页面提交
    @RequestMapping(value = "/insertSubmit", method = RequestMethod.POST)
    public String insertUserSubmit(UserEntity user) {
        UserEntity user2 = userService.getUserByName(user.getUserName());
        UserEntity user3 = userService.getUserByEmail(user.getUserEmail());
        UserEntity user4 = userService.getUserByTele(user.getUserTele());
        if (user2 == null && user3 == null && user4 == null) {
            user.setUserRegisterTime(new Date());
            user.setUserStatus(1);
            user.setUserRole(UserRoleEnums.USER.getValue());
            userService.insertUser(user);
        }
        return "redirect:/admin/user";
    }

    // 编辑用户提交
    @RequestMapping(value = "/editSubmit", method = RequestMethod.POST)
    public String editUserSubmit(UserEntity user) {
        userService.updateUser(user);
        return "redirect:/admin/user";
    }

    // 删除用户
    @RequestMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
        return "redirect:/admin/user";
    }


    // 后台添加用户页面显示
    @RequestMapping(value = "/insert")
    public ModelAndView insertUserView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Admin/User/insert");
        return modelAndView;
    }

    // 编辑用户页面显示
    @RequestMapping(value = "/edit/{id}")
    public ModelAndView editUserView(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        UserEntity user = userService.getUserById(id);
        modelAndView.addObject("user", user);
        modelAndView.setViewName("Admin/User/edit");
        return modelAndView;
    }

}