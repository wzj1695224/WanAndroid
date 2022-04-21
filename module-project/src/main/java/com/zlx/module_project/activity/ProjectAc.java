package com.zlx.module_project.activity;

import android.os.Bundle;

import com.zlx.module_base.base_ac.BaseMvvmAc;
import com.zlx.module_base.viewmodel.BaseViewModel;
import com.zlx.module_project.R;
import com.zlx.module_project.databinding.AcProjectBinding;


public class ProjectAc extends BaseMvvmAc<AcProjectBinding, BaseViewModel> {

    @Override
    protected int initContentView(Bundle savedInstanceState) {
        return R.layout.ac_project;
    }

    @Override
    protected int initVariableId() {
        return 0;
    }
}
