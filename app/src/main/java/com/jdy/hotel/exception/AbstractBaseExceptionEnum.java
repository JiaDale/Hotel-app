package com.jdy.hotel.exception;

public interface AbstractBaseExceptionEnum {

    /**
     * 获取异常的状态码
     */
    int getCode();

    /**
     * 获取异常的提示信息
     */
    String getMessage();
}
