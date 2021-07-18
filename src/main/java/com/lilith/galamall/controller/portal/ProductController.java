package com.lilith.galamall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.lilith.galamall.common.GalaRes;
import com.lilith.galamall.service.ProductService;
import com.lilith.galamall.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:JiaJingnan
 * @Date: 上午12:57 2021/7/18
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping("detail.do")
    public GalaRes<ProductDetailVo> detail(Integer productId){
        return productService.getProductDetail(productId);
    }

    @RequestMapping("list.do")
    public GalaRes<PageInfo> list(@RequestParam(value = "keyword", required = false) String keyword,
                                  @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                  @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10")int pageSize,
                                  @RequestParam(value = "orderBy", defaultValue = "")String orderBy){

        return productService.getProductByKeywordAndCategory(keyword, categoryId, pageNum, pageSize, orderBy);
    }


}
