package com.lilith.galamall.service;

import com.lilith.galamall.common.GalaRes;
import com.lilith.galamall.entity.Shipping;

/**
 * @Author:JiaJingnan
 * @Date: 下午4:42 2021/8/22
 */
public interface ShipppingService {

    GalaRes add(Integer userId, Shipping shipping);

    GalaRes del(Integer userId, Integer shippingId);
}
