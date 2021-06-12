package com.lilith.galamall.controller.backend;

import com.lilith.galamall.common.Const;
import com.lilith.galamall.common.GalaRes;
import com.lilith.galamall.common.ResponseCode;
import com.lilith.galamall.entity.Product;
import com.lilith.galamall.entity.User;
import com.lilith.galamall.service.ProductService;
import com.lilith.galamall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @Author:JiaJingnan
 * @Date: 下午11:08 2021/6/7
 */
@RestController
@RequestMapping("/manage/product")
public class ProductManageController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @RequestMapping("/save.do")
    public GalaRes productSave(HttpSession session, Product product){
        // 校验是否登陆
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return GalaRes.createByErrorCodeMessage(ResponseCode.NEEG_LOGIN.getCode(),"用户未登陆，请登陆");
        }

        // 校验是否未管理员
        if (userService.checkAdmin(user).isSuccess()){

            // 增加商品保存操作
            return productService.saveOrUpdateProduct(product);

        } else {
            return GalaRes.createByErrorMessage("无权限操作，需要管理员登陆");
        }
    }

    @RequestMapping("/set_sale_status.do")
    public GalaRes setSaleStatus(HttpSession session, Integer productId, Integer status){
        // 校验是否登陆
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return GalaRes.createByErrorCodeMessage(ResponseCode.NEEG_LOGIN.getCode(),"用户未登陆，请登陆");
        }

        // 校验是否未管理员
        if (userService.checkAdmin(user).isSuccess()){

            // 增加业务操作
            return productService.setSaleStatus(productId, status);

        } else {
            return GalaRes.createByErrorMessage("无权限操作，需要管理员登陆");
        }
    }

    @RequestMapping("/detail.do")
    public GalaRes getDetail(HttpSession session, Integer productId, Integer status){
        // 校验是否登陆
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return GalaRes.createByErrorCodeMessage(ResponseCode.NEEG_LOGIN.getCode(),"用户未登陆，请登陆");
        }

        // 校验是否未管理员
        if (userService.checkAdmin(user).isSuccess()){

            // 增加业务操作
            return productService.manageProductDetail(productId);

        } else {
            return GalaRes.createByErrorMessage("无权限操作，需要管理员登陆");
        }
    }

    @RequestMapping("/list.do")
    public GalaRes getList(HttpSession session,
                           @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                           @RequestParam(value = "pageSize", defaultValue = "10")int pageSize){
        // 校验是否登陆
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return GalaRes.createByErrorCodeMessage(ResponseCode.NEEG_LOGIN.getCode(),"用户未登陆，请登陆");
        }

        // 校验是否未管理员
        if (userService.checkAdmin(user).isSuccess()){

            // 增加商品保存操作
            return productService.getProductList(pageNum,pageSize);

        } else {
            return GalaRes.createByErrorMessage("无权限操作，需要管理员登陆");
        }
    }

    @RequestMapping("/search.do")
    public GalaRes productSearch(HttpSession session, String productName, Integer productId,
                           @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                           @RequestParam(value = "pageSize", defaultValue = "10")int pageSize){
        // 校验是否登陆
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return GalaRes.createByErrorCodeMessage(ResponseCode.NEEG_LOGIN.getCode(),"用户未登陆，请登陆");
        }

        // 校验是否未管理员
        if (userService.checkAdmin(user).isSuccess()){

            // 增加商品保存操作
            return productService.productSearch(productName, productId, pageNum, pageSize);

        } else {
            return GalaRes.createByErrorMessage("无权限操作，需要管理员登陆");
        }
    }




}
