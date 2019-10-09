package com.jdy.hotel.net;

import com.jdy.hotel.utils.CollectionUtils;
import com.jdy.hotel.utils.L;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.Collection;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class BaseDataSource<T> extends Callback<T> implements DataSource<T> {

    private final Collection<ResponseDataListener<T>> listeners = new ArrayList<>();

    @Override
    public void respond(T result) {
        if (CollectionUtils.isEmpty(listeners)) return;
        for (ResponseDataListener<T> listener : listeners) {
            listener.onRespond(result);
        }
    }

    @Override
    public void register(ResponseDataListener<T> listener) {
        listeners.add(listener);
    }

    @Override
    public void onBefore(Request request, int id) {
        L.i("请求 %s, ", request);
        super.onBefore(request, id);
    }

    @Override
    public T parseNetworkResponse(Response response, int i) throws Exception {
        return null;
    }

    @Override
    public void onError(Call call, Exception e, int i) {

    }

    @Override
    public void onResponse(T t, int i) {

    }
}
