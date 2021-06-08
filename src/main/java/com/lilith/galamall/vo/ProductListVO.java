package com.lilith.galamall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author:JiaJingnan
 * @Date: 上午10:03 2021/6/8
 */
@Data
public class ProductListVO {

    private Integer id;
    private Integer categoryId;
    private String name;
    private String subtitle;
    private String mainImage;
    private BigDecimal price;
    private Integer status;
    private String imageHost;
}
