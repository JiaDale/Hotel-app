package com.jdy.hotel.utils;

import android.util.Log;

public final class L {

    public final static String TAG = "Hotel";

    public static void i(String format, Object... args) {
        Log.i(TAG, String.format(format, args));
    }

    public static void e(String format, Object... args) {
        Log.e(TAG, String.format(format, args));
    }

    public static void w(String format, Object... args) {
        Log.w(TAG, String.format(format, args));
    }

    public static void d(String format, Object... args) {
        Log.d(TAG, String.format(format, args));
    }

    public static void v(String format, Object... args) {
        Log.v(TAG, String.format(format, args));
    }
}
