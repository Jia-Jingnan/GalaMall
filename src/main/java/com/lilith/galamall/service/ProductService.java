package com.lilith.galamall.service;

import com.lilith.galamall.common.GalaRes;
import com.lilith.galamall.entity.Product;

/**
 * @Author:JiaJingnan
 * @Date: 下午11:10 2021/6/7
 */
public interface ProductService {

    GalaRes saveOrUpdateProduct(Product product);

    GalaRes setSaleStatus(Integer productId, Integer status);
}
