package com.zlx.module_mine.language;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zlx.module_base.base_ac.BaseMvvmAc;
import com.zlx.module_base.base_manage.ActivityUtil;
import com.zlx.module_base.base_util.LanguageUtil;
import com.zlx.module_base.constant.RouterActivityPath;
import com.zlx.module_mine.BR;
import com.zlx.module_mine.R;
import com.zlx.module_mine.adapters.RvAdapterLanguageSet;
import com.zlx.module_mine.bean.LanguageBean;
import com.zlx.module_mine.databinding.AcLanguageSetBinding;

import java.util.ArrayList;
import java.util.List;


public class LanguageSetAc extends BaseMvvmAc<AcLanguageSetBinding,LanguageViewModel> {

    private RvAdapterLanguageSet adapter;

    @Override
    public void initViews() {
        super.initViews();
        viewModel.setTitleText(getString(R.string.mine_language_set));
        adapter = new RvAdapterLanguageSet(new ArrayList<>());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        adapter.setListener(position -> {
            LanguageBean languageBean = adapter.getDatas().get(position);
            LanguageUtil.switchLanguage(languageBean.getLocale());
            ActivityUtil.finishAllActivity();
            ARouter.getInstance().build(RouterActivityPath.Main.PAGER_MAIN).navigation();
        });
        viewModel.languageLiveData.observe(this, new Observer<List<LanguageBean>>() {
            @Override
            public void onChanged(List<LanguageBean> languageBeans) {
                adapter.refresh(languageBeans);
            }
        });
        viewModel.getData();
    }




    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.ac_language_set;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}
