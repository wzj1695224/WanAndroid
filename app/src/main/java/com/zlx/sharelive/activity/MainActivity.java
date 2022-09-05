package com.zlx.sharelive.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zlx.module_base.base_ac.BaseMvvmAc;
import com.zlx.module_base.constant.RouterActivityPath;
import com.zlx.module_base.viewmodel.BaseViewModel;
import com.zlx.sharelive.R;
import com.zlx.sharelive.databinding.AcMainBinding;
import com.zlx.widget.bubblenavigation.listener.BubbleNavigationChangeListener;


@Route(path = RouterActivityPath.Main.PAGER_MAIN)
public class MainActivity extends BaseMvvmAc<AcMainBinding, BaseViewModel> implements BubbleNavigationChangeListener {


    @Override
    protected boolean canSwipeBack() {
        return false;
    }


    @Override
    public void initViews() {
        initTab();

        initNav();

        requestPermissions(
                "android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.READ_EXTERNAL_STORAGE"
        );
    }


    private void initNav() {

        binding.bottomNavigationViewLinear.setTypeface(Typeface.createFromAsset(getAssets(), "rubik.ttf"));

        binding.bottomNavigationViewLinear.setBadgeValue(0, null);
        binding.bottomNavigationViewLinear.setBadgeValue(1, null); //invisible badge
        binding.bottomNavigationViewLinear.setBadgeValue(2, null);
        binding.bottomNavigationViewLinear.setBadgeValue(3, null);
        binding.bottomNavigationViewLinear.setBadgeValue(4, null); //empty badge
        binding.bottomNavigationViewLinear.setNavigationChangeListener(this);
    }

    private void initTab() {
        FragPagerAdapter adapter = new FragPagerAdapter(getSupportFragmentManager());
        int fragCount = FragPagerAdapter.FRAG_PATH.length;

        binding.viewPager.setOffscreenPageLimit(fragCount);
        binding.viewPager.setScrollable(false);
        binding.viewPager.setAdapter(adapter);
    }


    @Override
    public void onNavigationChanged(View view, int position) {
        binding.viewPager.setCurrentItem(position, false);
    }

    //用户按返回按钮不关闭页面，而是返回到系统桌面。相当于按home键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected int initContentView(Bundle savedInstanceState) {
        return R.layout.ac_main;
    }

    @Override
    protected int initVariableId() {
        return 0;
    }
}
