package com.zlx.module_base.impl;

import android.os.Bundle;


public interface IAcView {
    void initViews();
    void initEvents();
    void beforeOnCreate();
    void afterOnCreate(Bundle savedInstanceState);
    void initImmersionBar();
}
