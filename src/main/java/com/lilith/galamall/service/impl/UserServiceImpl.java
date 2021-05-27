package com.lilith.galamall.service.impl;

import com.lilith.galamall.common.GalaRes;
import com.lilith.galamall.dao.UserMapper;
import com.lilith.galamall.entity.User;
import com.lilith.galamall.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Author:JiaJingnan
 * @Date: 上午2:09 2021/5/28
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public GalaRes<User> login(String username, String password) {

        int count = userMapper.checkUsername(username);
        if (count == 0){
            return GalaRes.createByErrorMessage("用户名不存在");
        }

        // todo 密码登陆md5
        User user = userMapper.selectLogin(username,password);
        if (user == null){
            return GalaRes.createByErrorMessage("密码错误");
        }

        user.setPassword(StringUtils.EMPTY);
        return GalaRes.createBySuccess("登陆成功",user);
    }
}
