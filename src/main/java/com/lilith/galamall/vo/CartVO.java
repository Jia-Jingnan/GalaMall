package com.lilith.galamall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author:JiaJingnan
 * @Date: 上午1:11 2021/7/22
 */
@Data
public class CartVO {


    private List<CartProductVO> cartProductVOList;
    private BigDecimal cartTotalPrice;
    private Boolean allChecked; //是够全部勾选
    private String imageHost; // 商品图片
}
