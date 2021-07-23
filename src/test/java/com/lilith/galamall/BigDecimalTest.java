package com.lilith.galamall;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * @Author:JiaJingnan
 * @Date: 上午12:20 2021/8/15
 */

public class BigDecimalTest {

    @Test
    // 默认浮点型进行计算会出现丢失精度的情况
    public void test1(){
        System.out.println(0.05 + 0.01);
        System.out.println(1.0 - 0.42);
        System.out.println(4.015 * 100);
        System.out.println(123.3 / 100);
    }


    @Test
    public void test2(){
        BigDecimal b1 = new BigDecimal(0.05);
        BigDecimal b2 = new BigDecimal(0.01);
        System.out.println(b1.add(b2));
    }
    // 使用BigDecimal避免丢失精度,使用BigDecimal的String构造器
    @Test
    public void test3(){
        BigDecimal b1 = new BigDecimal("0.05");
        BigDecimal b2 = new BigDecimal("0.01");
        System.out.println(b1.add(b2));

    }
}
