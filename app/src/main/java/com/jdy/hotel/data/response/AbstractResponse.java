package com.jdy.hotel.data.response;


import android.os.Build;

import com.google.gson.Gson;
import com.jdy.hotel.data.Beans.Setter;
import com.jdy.hotel.utils.BooleanFunction;
import com.jdy.hotel.utils.CheckUtil;
import com.jdy.hotel.utils.StringFunction;

import java.net.UnknownServiceException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 常用工具包
 * <p>
 * [Description]
 * <p>
 * 创建人 Dale 时间 2019/9/22 10:32
 */
class AbstractResponse implements ResponseResult {

    private Map<String, Object> dataMap = new ConcurrentHashMap<>();

    AbstractResponse(Map<String, Object> map){
        dataMap = map;
    }

    AbstractResponse(boolean status, int code, String msg) {
        this(status, code, msg, new HashMap<>());
    }

    AbstractResponse(boolean status, int code, Map<String, Object> dataMap) {
        this(status, code, "", dataMap);
    }

    AbstractResponse(boolean status, int code, String msg, Map<String, Object> dataMap) {
        set(STATUS, status);
        set(CODE, code);
        set(MSG, msg);
        set(DATA, dataMap);
    }

    @Override
    public boolean getStatus() {
        Object succeed = get(STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return BooleanFunction.getInstance().apply(succeed);
        }
        if (null == succeed) return false;

        return (boolean) succeed;
    }

    @Override
    public int getCode() {
        Object object = get(CODE);
        if (object instanceof Number) {
            return ((Number) object).intValue();
        }
        if (getStatus()) {
            return 200;
        }
        throw new IllegalArgumentException("响应参数存在问题！");
    }

    @Override
    public String getMessage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return StringFunction.getInstance().apply(get(MSG));
        }
        return  get(MSG).toString();
    }

    @Override
    public ResponseResult setError(Exception e) {
        set("Exception", e);
        return this;
    }


    @Override
    public Exception getException() {
        Object exception = get("Exception");
        if (exception instanceof  Exception)
            return (Exception) exception;
        return new UnknownServiceException("发生一个未知错误！");
    }


    @Override
    public Map<String, Object> getDataMap() {
        return dataMap;
    }


    @Override
    public String toString() {
        //向前台传一个json串
        return new Gson().toJson(getDataMap());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> V get(String key) {
        return (V) CheckUtil.checkValue(dataMap.get(key), null);
    }

    @Override
    public <V> Setter set(String key, V value, boolean ignoreCase) {
        dataMap.put(key, value);
        return this;
    }
}
