package com.lilith.galamall.common;

import lombok.Data;
import lombok.Getter;

/**
 * @Author:JiaJingnan
 * @Date: 上午2:15 2021/5/28
 */
@Getter
public enum ResponseCode {

    SUCCESS(0,"SUCCESS"),
    ERROR(1,"ERROR"),
    NEEG_LOGIN(10,"NEED_LOGIN"),
    ILLEGAL_ARRGUMENT(2,"ILLEGAL_ARGUMENT");

    private final int code;
    private final String desc;

    ResponseCode(int code, String desc){
        this.code = code;
        this.desc = desc;
    }

}
