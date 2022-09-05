package com.zlx.module_base.base_fg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.zlx.module_base.viewmodel.BaseViewModel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


public abstract class BaseMvvmFg<V extends ViewDataBinding, VM extends BaseViewModel> extends BaseFg {
    protected VM viewModel;
    protected V binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId = initContentView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false);
        initImmersionBar();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewDataBinding();
        getLifecycle().addObserver(viewModel);
        initViews();
    }

    protected void initViews() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (binding != null) {
            binding.unbind();
        }
    }

    private void initViewDataBinding() {
        if (viewModel == null) {
            //noinspection unchecked
            Class<VM> clazz = (Class<VM>) getViewModelClass();
            ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication());
            viewModel = new ViewModelProvider(this, factory).get(clazz);
        }

        if (initVariableId() > 0) {
            binding.setVariable(initVariableId(), viewModel);
        }

        viewModel.uiChangeLiveData().onBackPressedEvent().observe(this, o -> {
           requireActivity().onBackPressed();
        });
    }


    private Class<?> getViewModelClass() {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType)
            return (Class<?>) ((ParameterizedType) type).getActualTypeArguments()[1];

        // 如果没有指定泛型参数，则默认使用BaseViewModel
        return BaseViewModel.class;
    }
    
    

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    protected abstract int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    protected abstract int initVariableId();
}
