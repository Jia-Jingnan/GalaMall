package com.lilith.galamall.controller.portal;

import com.lilith.galamall.common.Const;
import com.lilith.galamall.common.GalaRes;
import com.lilith.galamall.common.ResponseCode;
import com.lilith.galamall.entity.User;
import com.lilith.galamall.service.UserService;
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
            // 设置Session中的登陆用户
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

    @RequestMapping(value = "/forget_check_answer.do", method = RequestMethod.GET)
    @ResponseBody
    public GalaRes<String> forgetCheckAnswer(String username, String question, String answer){

        return userService.checkAnswer(username, question, answer);

    }

    @RequestMapping(value = "/forget_reset_password.do", method = RequestMethod.GET)
    @ResponseBody
    public GalaRes<String> forgetResetPassword(String username, String password, String forgetToken){

        return userService.forgetResetPassword(username,password,forgetToken);
    }

    // 登陆状态重设密码
    @RequestMapping(value = "/reset_password.do", method = RequestMethod.GET)
    @ResponseBody
    public GalaRes<String> resetPassword(HttpSession session, String passwordOld, String passwordNew){

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return GalaRes.createByErrorMessage("用户为登陆");
        }

        return userService.resetPassword(passwordOld,passwordNew,user);

    }

    @RequestMapping(value = "/update_information.do", method = RequestMethod.GET)
    @ResponseBody
    public GalaRes<User> updateInformation(HttpSession session, User user){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null){
            return GalaRes.createByErrorMessage("用户未登陆");
        }

        user.setId(currentUser.getId());
        user.setUsername(user.getUsername());
        GalaRes<User> userGalaRes = userService.updateInformation(user);
        if (userGalaRes.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,userGalaRes.getData());
        }
        return userGalaRes;
    }

    @RequestMapping(value = "/get_information.do", method = RequestMethod.GET)
    @ResponseBody
    public GalaRes<User> getInformation(HttpSession session){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null){
            return GalaRes.createByErrorCodeMessage(ResponseCode.NEEG_LOGIN.getCode(),"用户未登陆,需要强制登陆status=10");

        }

        return userService.getInformation(currentUser.getId());

    }












}
