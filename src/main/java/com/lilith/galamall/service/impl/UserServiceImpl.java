package com.lilith.galamall.service.impl;

import com.lilith.galamall.common.Const;
import com.lilith.galamall.common.GalaRes;
import com.lilith.galamall.common.TokenCache;
import com.lilith.galamall.dao.UserMapper;
import com.lilith.galamall.entity.User;
import com.lilith.galamall.service.UserService;
import com.lilith.galamall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


/**
 * @Author:JiaJingnan
 * @Date: 上午2:09 2021/5/28
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public GalaRes<String> checkAnswer(String username, String question, String answer) {
        int result = userMapper.checkAnswer(username,question,answer);
        if (result > 0){
            // 生成token，放在本地缓存中
            String forgetToken = UUID.randomUUID().toString();
            // 设置本地缓存
            TokenCache.setKey("token"+username,forgetToken);
            return GalaRes.createBySuccess(forgetToken);
        }

        return GalaRes.createByErrorMessage("忘记密码问题答案错误");
    }



    public GalaRes<String> selectQuestion(String username){
        GalaRes validInfo = this.checkValid(username,Const.USERNAME);
        if (validInfo.isSuccess()){
            return GalaRes.createByErrorMessage("用户不存在");
        }
        String question = userMapper.selectQuestionByUsername(username);
        if (StringUtils.isNotBlank(question)){
            return GalaRes.createBySuccess(question);
        }
        return GalaRes.createByErrorMessage("找回密码问题为空");
    }


    public GalaRes<String> checkValid(String value, String type){
        if (StringUtils.isNotBlank(type)){

            if (Const.USERNAME.equalsIgnoreCase(type)){
                int count = userMapper.checkUsername(value);
                if (count > 0){
                    return GalaRes.createByErrorMessage("用户名已存在");
                }
            }

            if (Const.EMAIL.equalsIgnoreCase(type)){
                int count = userMapper.checkEmail(value);
                if (count > 0){
                    return GalaRes.createByErrorMessage("邮箱已存在");
                }
            }
        } else {
            return GalaRes.createByErrorMessage("参数错误");
        }

        return GalaRes.createBySuccessMessage("校验成功");
    }

    public GalaRes<String> register(User user){
        GalaRes galaRes = this.checkValid(user.getUsername(),Const.USERNAME);
        if (!galaRes.isSuccess()){
            return galaRes;
        }

        galaRes = this.checkValid(user.getEmail(),Const.EMAIL);
        if (!galaRes.isSuccess()){
            return galaRes;
        }
        // 设置为普通用户
        user.setRole(Const.Role.ROLE_CUSTOMER);

        // md5 加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));

        int count = userMapper.insert(user);

        if (count == 0){
            return GalaRes.createByErrorMessage("注册失败");
        }
        return GalaRes.createBySuccessMessage("注册成功");

    }

    @Override
    public GalaRes<User> login(String username, String password) {

        int count = userMapper.checkUsername(username);
        if (count == 0){
            return GalaRes.createByErrorMessage("用户名不存在");
        }

        // 密码登陆md5
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectLogin(username,md5Password);
        if (user == null){
            return GalaRes.createByErrorMessage("密码错误");
        }

        user.setPassword(StringUtils.EMPTY);
        return GalaRes.createBySuccess("登陆成功",user);
    }
}
