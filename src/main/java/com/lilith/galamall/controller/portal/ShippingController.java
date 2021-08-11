package com.lilith.galamall.controller.portal;

import com.lilith.galamall.common.Const;
import com.lilith.galamall.common.GalaRes;
import com.lilith.galamall.common.ResponseCode;
import com.lilith.galamall.entity.Shipping;
import com.lilith.galamall.entity.User;
import com.lilith.galamall.service.ShipppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @Author:JiaJingnan
 * @Date: 下午4:41 2021/8/22
 */
@RestController
@RequestMapping("/shipping/")
public class ShippingController {

    @Autowired
    private ShipppingService shipppingService;

    @RequestMapping("/add.do")
    public GalaRes add(HttpSession session, Shipping shipping){

        // 权限判断
        // 校验是否登陆
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return GalaRes.createByErrorCodeMessage(ResponseCode.NEEG_LOGIN.getCode(),ResponseCode.NEEG_LOGIN.getDesc());
        }
        return shipppingService.add(user.getId(), shipping);
    }


    @RequestMapping("/delete.do")
    public GalaRes del(HttpSession session, Integer shippingId){

        // 权限判断
        // 校验是否登陆
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return GalaRes.createByErrorCodeMessage(ResponseCode.NEEG_LOGIN.getCode(),ResponseCode.NEEG_LOGIN.getDesc());
        }
        return shipppingService.del(user.getId(), shippingId);
    }
}
