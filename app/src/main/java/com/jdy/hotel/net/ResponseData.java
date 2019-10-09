package com.jdy.hotel.net;

import com.jdy.hotel.exception.BizExceptionEnum;

import java.io.Serializable;

public class ResponseData implements Serializable {

    private static final long serialVersionUID = 7769939977974872395L;


    public static final String DEFAULT_SUCCESS_MESSAGE = "请求成功";

    public static final String DEFAULT_ERROR_MESSAGE = "网络异常";

    public static final String DEFAULT_ERROR_DATA = "数据错误！";

    public static final Integer DEFAULT_SUCCESS_CODE = 200;

    public static final Integer DEFAULT_ERROR_CODE = 500;

    /**
     * 请求是否成功
     */
    private boolean success;

    /**
     * 响应状态码
     */
    private int code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 响应对象
     */
    private Object data;


    public ResponseData() {
    }

    public ResponseData(boolean success, int code, String message, Object data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public static ResponseData success(){
        return new SuccessResponseData();
    }

    public static ResponseData success(Object result){
        return new SuccessResponseData(result);
    }

    public static ResponseData error(String message){
        return new ErrorResponseData(message);
    }

    public static ResponseData error(int code){
        return new ErrorResponseData(code, DEFAULT_ERROR_DATA);
    }

    public static ResponseData error(int code,  String message){
        return new ErrorResponseData(code, message);
    }

    public static ResponseData error(BizExceptionEnum exceptionEnum){
        return error(exceptionEnum.getCode(), exceptionEnum.getMessage());
    }
}
