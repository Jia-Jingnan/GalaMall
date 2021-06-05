package com.lilith.galamall.controller.backend;

import com.lilith.galamall.common.Const;
import com.lilith.galamall.common.GalaRes;
import com.lilith.galamall.common.ResponseCode;
import com.lilith.galamall.entity.User;
import com.lilith.galamall.service.CategoryService;
import com.lilith.galamall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @Author:JiaJingnan
 * @Date: 上午2:05 2021/5/28
 */
@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {


    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/get_deep_category.do", method = RequestMethod.POST)
    public GalaRes getCategoryAndDeepChildrenCategory(HttpSession session,
                                               @RequestParam(value = "categoryId", defaultValue = "0")Integer categoryId){

        // 校验是否登陆
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return GalaRes.createByErrorCodeMessage(ResponseCode.NEEG_LOGIN.getCode(),"用户未登陆，请登陆");
        }
        // 校验是否未管理员
        if (userService.checkAdmin(user).isSuccess()){

            // 查询当前节点的id和递归子节点的id
            return categoryService.selectCategoryAndChildrenById(categoryId);
        } else {
            return GalaRes.createByErrorMessage("无权限操作，需要管理员登陆");
        }
    }


    @RequestMapping(value = "/get_category.do", method = RequestMethod.POST)
    public GalaRes getChildrenParallelCategory(HttpSession session,
                                               @RequestParam(value = "categoryId", defaultValue = "0")Integer categoryId){

        // 校验是否登陆
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return GalaRes.createByErrorCodeMessage(ResponseCode.NEEG_LOGIN.getCode(),"用户未登陆，请登陆");
        }
        // 校验是否未管理员
        if (userService.checkAdmin(user).isSuccess()){

            // 增加子节点的category信息，并且不递归，保持平级
            return categoryService.getChildrenParallelCategory(categoryId);
        } else {
            return GalaRes.createByErrorMessage("无权限操作，需要管理员登陆");
        }
    }



    @RequestMapping(value = "/update_category.do", method = RequestMethod.POST)
    public GalaRes updateCategoryName(HttpSession session, Integer categoryId, String categoryName){

        // 校验是否登陆
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return GalaRes.createByErrorCodeMessage(ResponseCode.NEEG_LOGIN.getCode(),"用户未登陆，请登陆");
        }
        // 校验是否未管理员
        if (userService.checkAdmin(user).isSuccess()){

            // 增加更新操作
            return categoryService.updateCategoryName(categoryId, categoryName);
        } else {
            return GalaRes.createByErrorMessage("无权限操作，需要管理员登陆");
        }
    }


    @RequestMapping(value = "/add_category.do", method = RequestMethod.POST)
    public GalaRes addCategory(HttpSession session, String categoryName,
                               @RequestParam(value = "parentId", defaultValue = "0") int paraentId){
        // 校验是否登陆
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return GalaRes.createByErrorCodeMessage(ResponseCode.NEEG_LOGIN.getCode(),"用户未登陆，请登陆");
        }
        // 校验是否未管理员
        if (userService.checkAdmin(user).isSuccess()){

            // 增加分类操作
            return categoryService.addCategory(categoryName, paraentId);
        } else {
            return GalaRes.createByErrorMessage("无权限操作，需要管理员登陆");
        }

    }

}
