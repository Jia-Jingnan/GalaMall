package com.lilith.galamall.controller.backend;

import com.lilith.galamall.common.Const;
import com.lilith.galamall.common.GalaRes;
import com.lilith.galamall.entity.User;
import com.lilith.galamall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @Author:JiaJingnan
 * @Date: 上午12:33 2021/6/4
 * 后台系统用户模块
 */
@RestController
@RequestMapping("/manage/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    public GalaRes<User> login(String username, String password, HttpSession session){
        GalaRes<User> res = userService.login(username,password);
        if (res.isSuccess()){
            User user = res.getData();
            if (user.getRole() == Const.Role.ROLE_ADMIN){
                session.setAttribute(Const.CURRENT_USER,user);
            } else {
                return GalaRes.createByErrorMessage("不是管理员，无法登陆");
            }
        }

        return res;
    }

}
