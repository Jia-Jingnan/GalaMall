package com.lilith.galamall.controller.portal;

import com.lilith.galamall.common.Const;
import com.lilith.galamall.common.GalaRes;
import com.lilith.galamall.entity.User;
import com.lilith.galamall.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @Author:JiaJingnan
 * @Date: 上午2:05 2021/5/28
 */
@Controller
@RequestMapping("user")
@Api(tags = "用户模块")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public GalaRes<User> login(String username, String password, HttpSession session){

        GalaRes<User> res = userService.login(username, password);
        if (res.isSuccess()){
            session.setAttribute(Const.CURRENT_USER, res.getData());
        }
        return res;
    }



    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public GalaRes<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return GalaRes.createBySuccess();
    }


    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public GalaRes<String> register(User user){

        return null;
    }



}
