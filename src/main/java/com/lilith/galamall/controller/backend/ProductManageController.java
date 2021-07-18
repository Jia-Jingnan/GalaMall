package com.lilith.galamall.controller.backend;

import com.github.pagehelper.StringUtil;
import com.google.common.collect.Maps;
import com.lilith.galamall.common.Const;
import com.lilith.galamall.common.GalaRes;
import com.lilith.galamall.common.ResponseCode;
import com.lilith.galamall.entity.Product;
import com.lilith.galamall.entity.User;
import com.lilith.galamall.service.FileService;
import com.lilith.galamall.service.ProductService;
import com.lilith.galamall.service.UserService;
import com.lilith.galamall.util.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

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

    @Autowired
    private FileService fileService;

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


    @RequestMapping("/upload.do")
    public GalaRes upload(HttpSession session, @RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request){

        // 权限判断
        // 校验是否登陆
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return GalaRes.createByErrorCodeMessage(ResponseCode.NEEG_LOGIN.getCode(),"用户未登陆，请登陆");
        }

        // 校验是否未管理员
        if (userService.checkAdmin(user).isSuccess()){

            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = fileService.upload(file, path);
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
            Map fileMap = Maps.newHashMap();
            fileMap.put("uri", targetFileName);
            fileMap.put("url", url);
            return GalaRes.createBySuccess(fileMap);

        } else {
            return GalaRes.createByErrorMessage("无权限操作，需要管理员登陆");
        }
    }

    // 富文本文件上传
    @RequestMapping("/rich_img_upload.do")
    public Map richImgUpload(HttpSession session, @RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response){
        Map resultMap = Maps.newHashMap();
        // 权限判断
        // 校验是否登陆
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            resultMap.put("success", false);
            resultMap.put("msg","请管理员登陆");
            return resultMap;
        }

        // 校验是否未管理员
        // 富文本中对于返回值有自己的要求，我们使用的是simditor所以按照simditor的要求进行返回
        if (userService.checkAdmin(user).isSuccess()){

            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = fileService.upload(file, path);
            if (StringUtils.isBlank(targetFileName)){
                resultMap.put("success", false);
                resultMap.put("msg","上传失败");
                return resultMap;
            }
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;

            resultMap.put("success", true);
            resultMap.put("msg","上传成功");
            resultMap.put("file_path", url);

            response.addHeader("Access-Controler-Allow-Headers", "X-File-Name");

            return resultMap;

        } else {
            resultMap.put("success", false);
            resultMap.put("msg","无权限操作");
            return resultMap;
        }
    }








}
