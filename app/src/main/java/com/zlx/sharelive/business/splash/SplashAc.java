package com.zlx.sharelive.business.splash;

import android.os.Bundle;

import com.zlx.module_base.base_ac.BaseMvvmAc;
import com.zlx.module_base.base_util.RouterUtil;
import com.zlx.module_base.database.MMkvHelper;
import com.zlx.sharelive.BR;
import com.zlx.sharelive.R;
import com.zlx.sharelive.databinding.AcSplashLayoutBinding;


public class SplashAc extends BaseMvvmAc<AcSplashLayoutBinding, SplashViewModel> {

    @Override
    public void initViews() {
        super.initViews();
        viewModel.projectListLiveData().observe(this, projectListRes -> {
            if (projectListRes.size() > 0) {
                MMkvHelper.getInstance().saveProjectTabs(projectListRes);
            }
        });
        viewModel.listProjectsTab();

        binding.particleview.setOnParticleAnimListener(() -> {
            RouterUtil.launchMain();
            finish();
        });

        binding.particleview.startAnim();

    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.ac_splash_layout;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}
