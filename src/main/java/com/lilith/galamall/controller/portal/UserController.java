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

import javax.servlet.http.HttpSession;

/**
 * @Author:JiaJingnan
 * @Date: 上午2:05 2021/5/28
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    @ResponseBody
    public GalaRes<User> login(String username, String password, HttpSession session){

        GalaRes<User> res = userService.login(username, password);
        if (res.isSuccess()){
            session.setAttribute(Const.CURRENT_USER, res.getData());
        }
        return res;
    }

    @RequestMapping(value = "/logout.do", method = RequestMethod.GET)
    @ResponseBody
    public GalaRes<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return GalaRes.createBySuccess();
    }


    @RequestMapping(value = "/register.do", method = RequestMethod.GET)
    @ResponseBody
    public GalaRes<String> register(User user){

        return userService.register(user);
    }


    @RequestMapping(value = "/check_valid.do", method = RequestMethod.GET)
    @ResponseBody
    public GalaRes<String> checkValid(String value, String type){

        return userService.checkValid(value,type);
    }

    @RequestMapping(value = "/get_user_info.do", method = RequestMethod.GET)
    @ResponseBody
    public GalaRes<User> getUserInfo(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user != null){
            return GalaRes.createBySuccess(user);
        }
        return GalaRes.createByErrorMessage("用户未登陆，无法获取当前用户信息");
    }


    @RequestMapping(value = "/forget_get_question.do", method = RequestMethod.GET)
    @ResponseBody
    public GalaRes<String> forgetGetQuestion(String username){
        return userService.selectQuestion(username);
    }

    @RequestMapping(value = "/forget_check_answer", method = RequestMethod.GET)
    @ResponseBody
    public GalaRes<String> forgetCheckAnswer(String username, String question, String answer){

        return userService.checkAnswer(username, question, answer);

    }






}
