package com.lilith.galamall.service;

import com.lilith.galamall.common.GalaRes;
import com.lilith.galamall.entity.User;

/**
 * @Author:JiaJingnan
 * @Date: 上午2:08 2021/5/28
 */
public interface UserService {

    GalaRes<User> login(String username, String password);
}
