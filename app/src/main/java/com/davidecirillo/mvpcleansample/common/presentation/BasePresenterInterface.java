package com.davidecirillo.mvpcleansample.common.presentation;


interface BasePresenterInterface<T extends BaseView> {

    void bind(T view);

    void unbind();

    T getView();
}
