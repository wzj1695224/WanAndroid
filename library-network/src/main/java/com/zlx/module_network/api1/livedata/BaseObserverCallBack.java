package com.zlx.module_network.api1.livedata;


public abstract class BaseObserverCallBack<T> {

    public abstract void onSuccess(T data);

    public boolean showErrorMsg() {
        return false;
    }

    public void onFail(String msg) {

    }

    public void onFinish() {

    }

}
