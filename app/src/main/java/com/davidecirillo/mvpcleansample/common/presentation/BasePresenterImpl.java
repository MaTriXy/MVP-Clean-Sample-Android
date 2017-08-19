package com.davidecirillo.mvpcleansample.common.presentation;


import android.util.Log;

import com.davidecirillo.mvpcleansample.common.domain.UseCaseHandler;

public class BasePresenterImpl<T extends BaseView> implements BasePresenter<T> {

    private static final String TAG = BasePresenterImpl.class.getSimpleName();

    protected UseCaseHandler mUseCaseHandler;

    public BasePresenterImpl(UseCaseHandler useCaseHandler) {
        mUseCaseHandler = useCaseHandler;
    }

    private T mView;

    @Override
    public void bind(T view) {
        this.mView = view;
        Log.d(TAG, "binding presenter ["+this+"] with view ["+mView+"] ");
    }

    @Override
    public void unbind() {
        Log.d(TAG, "unbinding presenter ["+this+"] with view ["+mView+"] ");
        mView = null;
    }

    @Override
    public T getView() {
        return mView;
    }
}
