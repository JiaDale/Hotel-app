package com.jdy.hotel.data;


import com.jdy.hotel.data.response.ResponseResult;

public class AbstractDataSource implements DataSource<ResponseResult> {

    private ResponseResult responseResult;

    final RequestOverListener<ResponseResult> listener;

    public AbstractDataSource(RequestOverListener<ResponseResult> listener) {
        this.listener = listener;
    }

    @Override
    public ResponseResult getResult() {
        return responseResult;
    }

    @Override
    public void setResult(ResponseResult result) {
        responseResult = result;
    }

//    @Override
//    public Result<ResponseResult> getResult() {
//        return responseResult;
//    }
//
//    @Override
//    public void setResult(Result<ResponseResult> result) {
//        responseResult = result;
//    }
}
