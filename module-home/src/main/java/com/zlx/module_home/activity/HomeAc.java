package com.zlx.module_home.activity;

import android.os.Bundle;

import com.zlx.module_base.base_ac.BaseMvvmAc;
import com.zlx.module_base.viewmodel.BaseViewModel;
import com.zlx.module_home.R;
import com.zlx.module_home.databinding.AcHomeBinding;


/**
 * module单独运行
 */
public class HomeAc extends BaseMvvmAc<AcHomeBinding, BaseViewModel> {

    @Override
    protected int initContentView(Bundle savedInstanceState) {
        return R.layout.ac_home;
    }

    @Override
    protected int initVariableId() {
        return 0;
    }
}
