package com.lilith.galamall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author:JiaJingnan
 * @Date: 上午1:06 2021/7/22
 */
@Data
public class CartProductVO {

    // 结合产品和购物车的抽象对象
    private Integer id;
    private Integer userId;
    private Integer productId;
    private Integer quantity; // 购物车中此商品的数量
    private String productName;
    private String productSubtitle;
    private String productMainImage;
    private BigDecimal productPrice;
    private Integer productStatus;
    private BigDecimal productTotalPrice;
    private Integer productStock;
    private Integer productChecked; // 此商品是否勾选

    private String limitQuantity; // 限制数量的返回结果


}
