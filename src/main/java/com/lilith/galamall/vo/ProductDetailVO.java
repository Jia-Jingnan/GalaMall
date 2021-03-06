package com.lilith.galamall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author:JiaJingnan
 * @Date: 下午11:42 2021/6/7
 */
@Data
public class ProductDetailVO {

    private Integer id;
    private Integer categoryId;
    private String name;
    private String subtitle;
    private String mainImage;
    private String subImages;
    private String detail;
    private BigDecimal price;
    private Integer stock;
    private Integer status;
    private String createTime;
    private String updateTime;

    private String imageHost;
    private Integer parentCategoryId;
}
