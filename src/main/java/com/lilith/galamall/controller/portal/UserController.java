package com.lilith.galamall.controller.portal;

import com.lilith.galamall.common.Const;
import com.lilith.galamall.common.GalaRes;
import com.lilith.galamall.entity.User;
import com.lilith.galamall.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

/**
 * @Author:JiaJingnan
 * @Date: 上午2:05 2021/5/28
 */
@RestController
@RequestMapping("user")
@Api(tags = "用户模块")
public class UserController {

    @Autowired
    private UserService userService;


    @ApiOperation(value = "用户名密码登陆", httpMethod = "POST")
    @PostMapping("/login")
    public GalaRes<User> login(String username, String password, HttpSession session){

        GalaRes<User> res = userService.login(username, password);
        if (res.isSuccess()){
            session.setAttribute(Const.CURRENT_USER, res.getData());
        }
        return res;
    }


    @ApiOperation(value = "登出", httpMethod = "GET")
    @GetMapping("/logout")
    public GalaRes<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return GalaRes.createBySuccess();
    }


}
