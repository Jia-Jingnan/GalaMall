package com.lilith.galamall.service.impl;

import com.lilith.galamall.common.GalaRes;
import com.lilith.galamall.dao.ProductMapper;
import com.lilith.galamall.entity.Product;
import com.lilith.galamall.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:JiaJingnan
 * @Date: 下午11:10 2021/6/7
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    public GalaRes saveOrUpdateProduct(Product product){
        if (product != null){
            if (StringUtils.isNotBlank(product.getSubImages())){
                String[] subImagesArray = product.getSubImages().split(",");
                if (subImagesArray.length > 0){
                    product.setMainImage(subImagesArray[0]);
                }
            }

            if (product.getId() != null){
                int count = productMapper.updateByPrimaryKey(product);
                if (count > 0){
                    GalaRes.createBySuccess("更新产品成功");
                }
                return GalaRes.createBySuccess("更新产品失败");
            } else {
                int count = productMapper.insert(product);
                if (count > 0){
                    GalaRes.createBySuccess("新增产品成功");
                }
                return GalaRes.createBySuccess("新增产品失败");
            }
        }

        return GalaRes.createByErrorMessage("新增或更新产品参数不正确");
    }
}
