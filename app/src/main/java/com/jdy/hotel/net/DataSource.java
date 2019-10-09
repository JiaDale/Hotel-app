package com.jdy.hotel.net;

public interface DataSource<T> {

     void respond(T result);

     void register(ResponseDataListener<T> listener);

}
