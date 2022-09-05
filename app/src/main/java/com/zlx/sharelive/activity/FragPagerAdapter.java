package com.zlx.sharelive.activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zlx.module_base.constant.RouterFragmentPath;


class FragPagerAdapter extends FragmentPagerAdapter {

    /**
     * Fragment pos -> path
     */
    static final String[] FRAG_PATH = new String[] {
            RouterFragmentPath.Home.PAGER_HOME,
            RouterFragmentPath.Project.PAGER_PROJECT,
            RouterFragmentPath.Square.PAGER_SQUARE,
            RouterFragmentPath.Public.PAGER_PUBLIC,
            RouterFragmentPath.Mine.PAGER_MINE
    };


    public FragPagerAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position < 0 || position >= FRAG_PATH.length) {
            //noinspection ConstantConditions
            return null;
        }
        String path = FRAG_PATH[position];
        return (Fragment) ARouter.getInstance().build(path).navigation();
    }


    @Override
    public int getCount() {
        return FRAG_PATH.length;
    }

}
