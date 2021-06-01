package com.lilith.galamall.service;

import com.lilith.galamall.common.GalaRes;
import com.lilith.galamall.entity.User;

/**
 * @Author:JiaJingnan
 * @Date: 上午2:08 2021/5/28
 */
public interface UserService {

    GalaRes<User> login(String username, String password);

    GalaRes<String> register(User user);

    GalaRes<String> checkValid(String value, String type);

    GalaRes<String> selectQuestion(String username);

    GalaRes<String> checkAnswer(String username, String question, String answer);

    GalaRes<String> forgetResetPassword(String username, String passowrd, String forgetToken);

    GalaRes<String> resetPassword(String passwordOld, String passwordNew, User user);
}
