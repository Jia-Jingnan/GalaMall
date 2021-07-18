package com.lilith.galamall.service;

import com.github.pagehelper.PageInfo;
import com.lilith.galamall.common.GalaRes;
import com.lilith.galamall.entity.Product;
import com.lilith.galamall.vo.ProductDetailVo;

/**
 * @Author:JiaJingnan
 * @Date: 下午11:10 2021/6/7
 */
public interface ProductService {

    GalaRes saveOrUpdateProduct(Product product);

    GalaRes setSaleStatus(Integer productId, Integer status);

    GalaRes<ProductDetailVo> manageProductDetail(Integer productId);

    GalaRes<PageInfo> getProductList(int pageNum, int pageSize);

    GalaRes<PageInfo> productSearch(String productName, Integer productId, int pageNum, int pageSize);

    GalaRes<ProductDetailVo> getProductDetail(Integer productId);

    GalaRes<PageInfo> getProductByKeywordAndCategory(String keyword, Integer categoryId, int pageNum, int pageSize, String orderBy);
}
