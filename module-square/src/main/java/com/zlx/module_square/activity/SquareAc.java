package com.zlx.module_square.activity;

import android.os.Bundle;

import com.zlx.module_base.base_ac.BaseMvvmAc;
import com.zlx.module_base.viewmodel.BaseViewModel;
import com.zlx.module_square.R;
import com.zlx.module_square.databinding.AcSquareBinding;


public class SquareAc extends BaseMvvmAc<AcSquareBinding, BaseViewModel> {

    @Override
    protected int initContentView(Bundle savedInstanceState) {
        return R.layout.ac_square;
    }

    @Override
    protected int initVariableId() {
        return 0;
    }
}
