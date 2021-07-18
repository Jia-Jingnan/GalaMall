package com.lilith.galamall.common;

/**
 * @Author:JiaJingnan
 * @Date: 上午2:45 2021/5/28
 * 常量类
 */
public class Const {

    public static final String EMAIL = "email";
    public static final String USERNAME = "username";
    public static final String CURRENT_USER = "currentUser";

    public interface Role{
        int ROLE_CUSTOMER = 0; //普通用户
        int ROLE_ADMIN = 1; //管理员
    }

    // 商品状态的枚举
    public enum ProductStatusEnum{

        ON_SALE(1,"在线");

        private String value;
        private int code;

        ProductStatusEnum(int code, String value){
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }
}
