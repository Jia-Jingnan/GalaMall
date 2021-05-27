package com.lilith.galamall.common;

import lombok.Getter;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.Serializable;

/**
 * @Author:JiaJingnan
 * @Date: 上午2:10 2021/5/28
 */
@Getter
public class GalaRes<T> implements Serializable {

    private int status;
    private String msg;
    private T data;

    private GalaRes(int status){
        this.status = status;
    }

    private GalaRes(int status, T data){
        this.status = status;
        this.data = data;
    }

    private GalaRes(int status, String msg, T data){
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    private GalaRes(int status, String msg){
        this.status = status;
        this.msg = msg;
    }

    @JsonIgnore
    public boolean isSuccess(){
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    public static <T> GalaRes<T> createBySuccess(){
        return new GalaRes<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> GalaRes<T> createBySuccessMessage(String msg){
        return new GalaRes<>(ResponseCode.SUCCESS.getCode(),msg);
    }

    public static <T> GalaRes<T> createBySuccess(T data){
        return new GalaRes<>(ResponseCode.SUCCESS.getCode(),data);
    }

    public static <T> GalaRes<T> createBySuccess(String msg, T data){
        return new GalaRes<>(ResponseCode.SUCCESS.getCode(),msg,data);
    }

    public static <T> GalaRes<T> createByError(){
        return new GalaRes<>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }

    public static <T> GalaRes<T> createByErrorMessage(String errMsg){
        return new GalaRes<>(ResponseCode.ERROR.getCode(),errMsg);
    }

    public static <T> GalaRes<T> createByErrorCodeMessage(int errorCode, String errMsg){
        return new GalaRes<>(errorCode,errMsg);
    }





}
