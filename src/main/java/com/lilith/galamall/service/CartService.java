package com.lilith.galamall.service;

import com.lilith.galamall.common.GalaRes;
import com.lilith.galamall.vo.CartVO;

/**
 * @Author:JiaJingnan
 * @Date: 上午1:00 2021/7/21
 */
public interface CartService {


    GalaRes<CartVO> add(Integer userId, Integer productId, Integer count);

    GalaRes<CartVO> update(Integer userId, Integer productId, Integer count);
}
