package com.lilith.galamall.service.impl;

import com.lilith.galamall.common.GalaRes;
import com.lilith.galamall.common.ResponseCode;
import com.lilith.galamall.dao.CategoryMapper;
import com.lilith.galamall.dao.ProductMapper;
import com.lilith.galamall.entity.Category;
import com.lilith.galamall.entity.Product;
import com.lilith.galamall.service.ProductService;
import com.lilith.galamall.util.DateTimeUtil;
import com.lilith.galamall.util.PropertiesUtil;
import com.lilith.galamall.vo.ProductDetailVo;
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

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public GalaRes<ProductDetailVo> manageProductDetail(Integer productId) {
        if (productId == null){
            return GalaRes.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARRGUMENT.getCode(), ResponseCode.ILLEGAL_ARRGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null){
            return GalaRes.createByErrorMessage("产品已下架或删除");
        }

        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return GalaRes.createBySuccess(productDetailVo);
    }

    private ProductDetailVo assembleProductDetailVo(Product product){
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setName(product.getName());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());

        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));

        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if(category == null){
            productDetailVo.setParentCategoryId(0);//默认根节点
        }else{
            productDetailVo.setParentCategoryId(category.getParentId());
        }

        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        return productDetailVo;
    }

    @Override
    public GalaRes setSaleStatus(Integer productId, Integer status) {
        if (productId == null || status == null){
            return GalaRes.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARRGUMENT.getCode(), ResponseCode.ILLEGAL_ARRGUMENT.getDesc());
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int count = productMapper.updateByPrimaryKeySelective(product);
        if (count > 0){
            return GalaRes.createBySuccess("修改产品可售状态成功");
        }
        return GalaRes.createByErrorMessage("修改产品可售状态失败");
    }


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
