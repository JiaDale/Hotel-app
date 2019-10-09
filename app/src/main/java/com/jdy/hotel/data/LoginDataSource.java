package com.jdy.hotel.data;


import android.text.TextUtils;

import com.google.gson.Gson;
import com.jdy.hotel.net.BaseDataSource;
import com.jdy.hotel.net.ResponseData;
import com.jdy.hotel.net.ResponseDataListener;
import com.jdy.hotel.utils.L;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource extends BaseDataSource<ResponseData> {

    public LoginDataSource(ResponseDataListener<ResponseData> listener) {
        register(listener);
    }

    public void login(String username, String password) {
        String url = "http://192.168.1.4:8080/user/login";
        OkHttpUtils.post().url(url).addParams("username", username)
                .addParams("password", password).build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        L.i("请求失败  ID ： %d,   Exception : %s", id, e);
                        respond(ResponseData.error(e.getMessage()));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        L.i("响应结果! ID ： %d,   Exception : %s ", id, response);

                        if (TextUtils.isEmpty(response)) {
                            //如果没有请求到数据
                            onError(null, new NullPointerException("Have not any element!"), id);
                        }

                        try {
                            respond(new Gson().fromJson(response, ResponseData.class));
                        } catch (Exception e) {
                            onError(null, e, id);
                        }
                    }
                });
    }


    public void logout() {
        // TODO: revoke authentication
    }
}
