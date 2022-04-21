package com.zlx.module_base.impl;

import android.view.View;


public interface INetView {

    void showLoading();

    void showLoading(View view);

    void showSuccess();

    void showEmpty();

    void onRetryBtnClick();

}
