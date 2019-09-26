package com.jdy.hotel.data.response;


import android.os.Build;

import com.jdy.hotel.data.Beans.Entity;
import com.jdy.hotel.utils.CheckUtil;
import com.jdy.hotel.utils.L;
import com.jdy.hotel.utils.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

public interface ResponseResult extends Entity {

    public final static String STATUS = "status";
    public final static String CODE = "code";
    public final static String MSG = "msg";
    public final static String DATA = "Data";

    boolean getStatus();

    int getCode();

    String getMessage();


    static ResponseResult create(boolean status, int code, String msg, Map<String, Object> dataMap) {
        if (status) {
            return new SucceedResponse(true, code, dataMap);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return new FailureResponse(code, msg);
        }

        dataMap = CheckUtil.checkValue(dataMap, new HashMap<>());
        dataMap.put(STATUS, false);
        dataMap.put(CODE, code);
        dataMap.put(MSG, msg);
        return new DefaultResponse(dataMap);
    }

    @SuppressWarnings("unchecked")
    static ResponseResult create(Object data) {
        if (ObjectUtils.isNull(data)) {
            return create(false, 400, "");
        }

        if (data instanceof String) {
            String msg = data.toString();
            int code = valueOf(msg);
            boolean status = code < 200 || code > 300;
            return create(!status, code, msg);
        }

        if (data instanceof ResponseResult) {
            return (ResponseResult) data;
        }

        if (data instanceof Entity) {
//            Entity entity = (Entity) data;
        }

        if (data instanceof Map) {
            return new DefaultResponse((Map<String, Object>) data);
        }

        L.e("数据%s无法转换成Response对象", data);
        return create(false, 400, "发生数据转换错误!");
    }


    static ResponseResult create(boolean b, int i, String s) {
        return create(b, i, s, new HashMap<>());
    }

    static int valueOf(String message) {
        switch (message) {
            case "404":
                return 404;
            default:
                return 500;
        }
    }

    ResponseResult setError(Exception e);

    Exception getException();
}
