package com.jdy.hotel.data.response;


public class CorrectData extends AbstractResponse {
    CorrectData(boolean status, int code, String msg) {
        super(status, code, msg);
    }
    public CorrectData(boolean b) {
        this(b, 200, "正确数据！");
    }
}
