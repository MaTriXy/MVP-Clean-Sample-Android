package com.davidecirillo.mvpcleansample.common.domain;


import android.os.Handler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class UseCaseThreadPoolScheduler implements UseCaseScheduler {

    private final Handler mUiHandler;

    private static final int POOL_SIZE = 2;

    private static final int MAX_POOL_SIZE = 4;

    private static final int TIMEOUT = 30;

    private ThreadPoolExecutor mThreadPoolExecutor;

    UseCaseThreadPoolScheduler() {
        this(new ThreadPoolExecutor(POOL_SIZE, MAX_POOL_SIZE, TIMEOUT, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(POOL_SIZE)),
                new Handler());
    }

    UseCaseThreadPoolScheduler(ThreadPoolExecutor threadPoolExecutor, Handler uiHandler) {
        mThreadPoolExecutor = threadPoolExecutor;
        mUiHandler = uiHandler;
    }

    @Override
    public void execute(Runnable runnable) {
        mThreadPoolExecutor.execute(runnable);
    }

    @Override
    public <V extends BaseUseCase.ResponseValues> void onSuccess(final V response,
                                                                 final BaseUseCase.UseCaseCallback<V> useCaseCallback) {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                useCaseCallback.onSuccess(response);
            }
        });
    }

    @Override
    public <V extends BaseUseCase.ResponseValues> void onError(
            final BaseUseCase.UseCaseCallback<V> useCaseCallback) {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                useCaseCallback.onError();
            }
        });
    }

}
