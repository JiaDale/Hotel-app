package com.jdy.hotel.data.response;

public class ErrorData extends AbstractResponse {

    ErrorData(boolean status, int code, String msg) {
        super(status, code, msg);
    }

    public ErrorData(int code) {
        this(false, code, "错误数据！");
    }
}
