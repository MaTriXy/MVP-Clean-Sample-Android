package com.davidecirillo.mvpcleansample.common.presentation;


interface BasePresenter<T extends BaseView> {

    void bind(T view);

    void unbind();

    T getView();
}
