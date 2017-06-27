package com.xmzd.doolive.api;

import rx.Subscriber;

/**
 * Date    2017/6/26
 * Author  xiaomao
 */

public abstract class ApiSubscriber<T> extends Subscriber<ApiResponse<T>> {

    @Override
    public void onCompleted() {

    }


    @Override
    public void onError(Throwable e) {
        onFailure(e.toString());
    }


    @Override
    public void onNext(ApiResponse<T> response) {
        // TODO: 2017/6/26  response
        if (response.isSuccess()) {
            onSuccess(response.getData());
        } else {
            onFailure(response.getMessage());
        }
    }

    public abstract void onSuccess(T t);
    public abstract void onFailure(String msg);
}
