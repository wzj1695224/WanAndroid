package com.zlx.module_base.loadsir;

import com.kingja.loadsir.callback.Callback;
import com.zlx.module_base.R;


public class ErrorCallback extends Callback {

    @Override
    protected int onCreateView()
    {
        return R.layout.base_layout_error;
    }
}
