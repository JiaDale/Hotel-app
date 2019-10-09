package com.jdy.hotel.net;

/**
 * 常用工具包
 * <p>
 * [Description]
 * <p>
 * 创建人 Dale 时间 2019/10/3 9:54
 */
public class ErrorResponseData extends ResponseData{

    public ErrorResponseData(String message) {
        super(false, DEFAULT_ERROR_CODE, message, null);
    }

    public ErrorResponseData(int code, String message) {
        super(false, code, message, null);
    }

    public ErrorResponseData(int code, String message, Object data) {
        super(false, code, message, data);
    }
}
