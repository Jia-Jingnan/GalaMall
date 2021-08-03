package com.lilith.galamall.controller.portal;

import com.lilith.galamall.common.Const;
import com.lilith.galamall.common.GalaRes;
import com.lilith.galamall.common.ResponseCode;
import com.lilith.galamall.entity.User;
import com.lilith.galamall.service.CartService;
import com.lilith.galamall.service.impl.UserServiceImpl;
import com.lilith.galamall.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @Author:JiaJingnan
 * @Date: 上午12:57 2021/7/21
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @RequestMapping("add.do")
    public GalaRes<CartVO> add(HttpSession session, Integer count, Integer productId){
        // 权限判断
        // 校验是否登陆
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return GalaRes.createByErrorCodeMessage(ResponseCode.NEEG_LOGIN.getCode(),ResponseCode.NEEG_LOGIN.getDesc());
        }

        // 购物车核心逻辑
        return cartService.add(user.getId(),productId,count);

    }

    @RequestMapping("update.do")
    public GalaRes<CartVO> update(HttpSession session, Integer count, Integer productId){
        // 权限判断
        // 校验是否登陆
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return GalaRes.createByErrorCodeMessage(ResponseCode.NEEG_LOGIN.getCode(),ResponseCode.NEEG_LOGIN.getDesc());
        }

        // 购物车核心逻辑
        return cartService.update(user.getId(),productId,count);

    }


}
