package com.lilith.galamall.controller.portal;

import com.lilith.galamall.common.Const;
import com.lilith.galamall.common.GalaRes;
import com.lilith.galamall.common.ResponseCode;
import com.lilith.galamall.entity.User;
import com.lilith.galamall.service.CartService;
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

    // 查看购物车
    @RequestMapping("list.do")
    public GalaRes<CartVO> list(HttpSession session){
        // 权限判断
        // 校验是否登陆
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return GalaRes.createByErrorCodeMessage(ResponseCode.NEEG_LOGIN.getCode(),ResponseCode.NEEG_LOGIN.getDesc());
        }

        // 购物车核心逻辑
        return cartService.list(user.getId());

    }

    // 全选
    @RequestMapping("select_all.do")
    public GalaRes<CartVO> selectAll(HttpSession session){
        // 权限判断
        // 校验是否登陆
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return GalaRes.createByErrorCodeMessage(ResponseCode.NEEG_LOGIN.getCode(),ResponseCode.NEEG_LOGIN.getDesc());
        }

        // 购物车核心逻辑
        return cartService.selectOrUnselect(user.getId(), Const.Cart.CHECKED);

    }

    // 全反选

    // 单独选

    // 单独反选某个产品

    // 查询当前用户的购物车里面的产品数量，如果一个产品有10个，那么数量数量就是10



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

    @RequestMapping("delete_product.do")
    public GalaRes<CartVO> deleteProduct(HttpSession session, String productIds){
        // 权限判断
        // 校验是否登陆
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return GalaRes.createByErrorCodeMessage(ResponseCode.NEEG_LOGIN.getCode(),ResponseCode.NEEG_LOGIN.getDesc());
        }

        // 购物车核心逻辑
        return cartService.deleteProduct(user.getId(),productIds);

    }


}
