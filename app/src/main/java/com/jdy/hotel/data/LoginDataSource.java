package com.jdy.hotel.data;


import android.text.TextUtils;

import com.google.gson.Gson;
import com.jdy.hotel.data.response.ResponseResult;
import com.jdy.hotel.utils.L;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Map;

import okhttp3.Call;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource extends AbstractDataSource {

    public LoginDataSource(RequestOverListener<ResponseResult> listener) {
        super(listener);
    }

    public void login(String username, String password) {
        String url = "http://192.168.1.4:8080/Hotel/login/action";
        OkHttpUtils.get().url(url).addParams("UserName", username)
                .addParams("Password", password).build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.over(ResponseResult.create(false, id, e.getMessage()).setError(e));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (TextUtils.isEmpty(response)) {
                            onError(null, new NullPointerException("Have not any element!"), id);
                        }

                        try {
                            listener.over(ResponseResult.create(new Gson().fromJson(response, Map.class)));
                        } catch (Exception e) {
                            L.i("响应结果：  %s,    %d ", response, id);
                            onError(null, e, id);
                        }
                    }
                });

//        new Callback<ResponseResult>() {
//            @Override
//            public ResponseResult parseNetworkResponse(okhttp3.Response response, int id) throws Exception {
//                L.i("-%d------------------------parseNetworkResponse-----------------------------------\n E : %s", id, response);
//                return null;
//            }
//
//            @SuppressWarnings("unchecked")
//            @Override
//            public void onError(Call call, Exception e, int id) {
//
//            }
//
//            @SuppressWarnings("unchecked")
//            @Override
//            public void onResponse(ResponseResult response, int id) {
//                listener.over(response);
//            }
//        }
    }


    public void logout() {
        // TODO: revoke authentication
    }
}
