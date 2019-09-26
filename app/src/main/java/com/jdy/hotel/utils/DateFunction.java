package com.jdy.hotel.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Date;
import java.util.Objects;
import java.util.function.Function;

/**
 * Description: Tools
 * Created by Administrator on 2019/9/18 14:29
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class DateFunction implements Function<Object, Date> {

    private DateFunction() {
    }

    @Override
    public Date apply(Object value) {
        if (Objects.isNull(value)) {
            return null;
        }

        if (value instanceof Date) {
            return (Date) value;
        }

        if (value instanceof String) {
            return DateUtil.convert(value.toString());
        }

        if (value instanceof Long) {
            new Date((Long) value);
        }

        throw new IllegalArgumentException("无法预知 value 的数据类型！");
    }

    private static class FunctionHolder {
        private static final DateFunction singleton = new DateFunction();
    }

    public static DateFunction getInstance() {
        return FunctionHolder.singleton;
    }
}
