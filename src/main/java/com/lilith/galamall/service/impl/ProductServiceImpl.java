package com.lilith.galamall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.lilith.galamall.common.Const;
import com.lilith.galamall.common.GalaRes;
import com.lilith.galamall.common.ResponseCode;
import com.lilith.galamall.dao.CategoryMapper;
import com.lilith.galamall.dao.ProductMapper;
import com.lilith.galamall.entity.Category;
import com.lilith.galamall.entity.Product;
import com.lilith.galamall.service.CategoryService;
import com.lilith.galamall.service.ProductService;
import com.lilith.galamall.util.DateTimeUtil;
import com.lilith.galamall.util.PropertiesUtil;
import com.lilith.galamall.vo.ProductDetailVO;
import com.lilith.galamall.vo.ProductListVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private CategoryService categoryService;

    @Override
    public GalaRes<PageInfo> getProductByKeywordAndCategory(String keyword, Integer categoryId, int pageNum, int pageSize, String orderBy) {

        // 判断参数
        if (StringUtils.isBlank(keyword) && categoryId == null){
            return GalaRes.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARRGUMENT.getCode(), ResponseCode.ILLEGAL_ARRGUMENT.getDesc());
        }

        // 声明一个集合
        List<Integer> categoryIdList = new ArrayList<>();

        if (categoryId != null){
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if (category == null && StringUtils.isBlank(keyword)){
                // 没有该分类，并且没有关键字，这个时候返回一个空的结果，不报错
                PageHelper.startPage(pageNum, pageSize);
                List<ProductListVO> productListVOList = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(productListVOList);
                return GalaRes.createBySuccess(pageInfo);
            }

            categoryIdList = categoryService.selectCategoryAndChildrenById(category.getId()).getData();

        }

        if (StringUtils.isNotBlank(keyword)){
            keyword = new StringBuilder().append("%").append(keyword).append("%").toString();
        }

        PageHelper.startPage(pageNum, pageSize);
        // 排序处理
        if (StringUtils.isNotBlank(orderBy)){
            // 动态排序
            if (Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)){
                String[] orderByArray = orderBy.split("_");
                PageHelper.orderBy(orderByArray[0] + " " + orderByArray[1]);
            }
        }
        List<Product> productList = productMapper.selectByNameAndCategoryIds(StringUtils.isBlank(keyword)?null:keyword,
                categoryIdList.size()==0?null:categoryIdList);

        List<ProductListVO> productListVOList = Lists.newArrayList();
        for (Product product : productList){
            ProductListVO productListVO = assembleProductListVO(product);
            productListVOList.add(productListVO);
        }

        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productListVOList);
        return GalaRes.createBySuccess(pageInfo);
    }

    @Override
    public GalaRes<ProductDetailVO> getProductDetail(Integer productId) {

        if (productId == null){
            return GalaRes.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARRGUMENT.getCode(), ResponseCode.ILLEGAL_ARRGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null){
            return GalaRes.createByErrorMessage("产品已下架或删除");
        }

        if (product.getStatus() != Const.ProductStatusEnum.ON_SALE.getCode()){
            return GalaRes.createByErrorMessage("产品已下架或删除");
        }

        ProductDetailVO productDetailVo = assembleProductDetailVo(product);
        return GalaRes.createBySuccess(productDetailVo);
    }

    @Override
    public GalaRes<PageInfo> productSearch(String productName, Integer productId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        if (StringUtils.isNoneBlank(productName)){
            // 构造查询条件 %productName%
            productName = new StringBuilder().append("%").append(productName).append("%").toString();
        }
        List<Product> productList = productMapper.selectByNameAndProductId(productName,productId);
        List<ProductListVO> productListVOList = Lists.newArrayList();
        for (Product productItem : productList){
            ProductListVO productListVO = assembleProductListVO(productItem);
            productListVOList.add(productListVO);
        }

        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(productListVOList);

        return GalaRes.createBySuccess(pageResult);
    }

    @Override
    public GalaRes<PageInfo> getProductList(int pageNum, int pageSize) {

        // startPage -- start
        // 填充sql逻辑
        // pageHelper -- 收尾
        PageHelper.startPage(pageNum,pageSize);
        List<Product> productList = productMapper.selectList();
        List<ProductListVO> productListVOList = Lists.newArrayList();
        for (Product productItem : productList){
            ProductListVO productListVO = assembleProductListVO(productItem);
            productListVOList.add(productListVO);
        }

        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(productListVOList);

        return GalaRes.createBySuccess(pageResult);
    }

    private ProductListVO assembleProductListVO(Product product){
        ProductListVO productListVO = new ProductListVO();
        productListVO.setId(product.getId());
        productListVO.setName(product.getName());
        productListVO.setCategoryId(product.getCategoryId());
        productListVO.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        productListVO.setMainImage(product.getMainImage());
        productListVO.setPrice(product.getPrice());
        productListVO.setSubtitle(product.getSubtitle());
        productListVO.setStatus(product.getStatus());
        return productListVO;
    }

    @Override
    public GalaRes<ProductDetailVO> manageProductDetail(Integer productId) {
        if (productId == null){
            return GalaRes.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARRGUMENT.getCode(), ResponseCode.ILLEGAL_ARRGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null){
            return GalaRes.createByErrorMessage("产品已下架或删除");
        }

        ProductDetailVO productDetailVo = assembleProductDetailVo(product);
        return GalaRes.createBySuccess(productDetailVo);
    }

    private ProductDetailVO assembleProductDetailVo(Product product){
        ProductDetailVO productDetailVo = new ProductDetailVO();
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
