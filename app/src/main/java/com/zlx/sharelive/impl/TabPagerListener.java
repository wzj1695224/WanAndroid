package com.zlx.sharelive.impl;

import androidx.fragment.app.Fragment;


public interface TabPagerListener {
    Fragment getFragment(int position);

    int count();
}
