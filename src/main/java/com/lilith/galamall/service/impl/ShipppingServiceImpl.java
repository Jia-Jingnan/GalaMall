package com.lilith.galamall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.lilith.galamall.common.GalaRes;
import com.lilith.galamall.dao.ShippingMapper;
import com.lilith.galamall.entity.Shipping;
import com.lilith.galamall.service.ShipppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author:JiaJingnan
 * @Date: 下午4:42 2021/8/22
 */
@Service
public class ShipppingServiceImpl implements ShipppingService {

    @Autowired
    private ShippingMapper shippingMapper;

    public GalaRes add(Integer userId, Shipping shipping){
        shipping.setUserId(userId);
        int rowCount = shippingMapper.insert(shipping);
        if (rowCount > 0){
            Map result = Maps.newHashMap();
            result.put("shippingId", shipping.getId());
            return GalaRes.createBySuccess("新建地址成功",result);
        }
        return GalaRes.createByErrorMessage("新建地址失败");
    }


    public GalaRes del(Integer userId, Integer shippingId){
        int result = shippingMapper.deleteByShippingIdUserId(userId,shippingId);
        if (result > 0){
            return GalaRes.createBySuccessMessage("删除地址成功");
        } else {
            return GalaRes.createByErrorMessage("删除地址失败");
        }
    }


    public GalaRes update(Integer userId, Shipping shipping){
        shipping.setUserId(userId);
        int rowCount = shippingMapper.updateByShipping(shipping);
        if (rowCount > 0){
            return GalaRes.createBySuccess("更新地址成功");
        }
        return GalaRes.createByErrorMessage("更新地址成功");
    }

    public GalaRes<Shipping> select(Integer userId, Integer shippingId){
        Shipping shipping = shippingMapper.selectByShippingIdUserId(userId,shippingId);
        if (shipping == null){
            return GalaRes.createByErrorMessage("无法查询到该地址");
        }
        return GalaRes.createBySuccess("查看地址成功",shipping);
    }

    public GalaRes<PageInfo> list(Integer userId, int pageNum, int pageSize){

        PageHelper.startPage(pageNum,pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUserId(userId);
        PageInfo pageInfo = new PageInfo(shippingList);

        return GalaRes.createBySuccess(pageInfo);

    }
}
