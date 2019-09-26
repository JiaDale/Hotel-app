package com.jdy.hotel.data;

public interface DataSource<T> {
    /**
     * 请求响应的结果
     *
     * @return 响应的结果
     */
     T getResult();

     void setResult(T result);


}
