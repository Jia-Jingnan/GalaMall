package com.lilith.galamall.service;

import com.github.pagehelper.PageInfo;
import com.lilith.galamall.common.GalaRes;
import com.lilith.galamall.entity.Shipping;

/**
 * @Author:JiaJingnan
 * @Date: 下午4:42 2021/8/22
 */
public interface ShipppingService {

    GalaRes add(Integer userId, Shipping shipping);

    GalaRes del(Integer userId, Integer shippingId);

    GalaRes update(Integer userId, Shipping shipping);

    GalaRes<Shipping> select(Integer userId, Integer shippingId);

    GalaRes<PageInfo> list(Integer userId, int pageNum, int pageSize);

}
