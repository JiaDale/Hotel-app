package com.jdy.hotel.data.response;


import android.os.Build;

import androidx.annotation.RequiresApi;

import com.jdy.hotel.utils.StringUtil;

/**
 * 常用工具包
 * <p>
 * [Description]
 * <p>
 * 创建人 Dale 时间 2019/9/22 10:22
 */
public class FailureResponse extends AbstractResponse {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public FailureResponse(String format, Object... parameters) {
        this(404,format, parameters);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public FailureResponse(int code, String format, Object... parameters) {
        super(false, code, String.format(StringUtil.checkValue(format, ""), parameters));
    }

}
