package com.davidecirillo.mvpcleansample.common.domain;

import android.os.Handler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.concurrent.ThreadPoolExecutor;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(PowerMockRunner.class)
public class UseCaseThreadPoolSchedulerTest {

    private UseCaseThreadPoolScheduler mCut;

    @Mock
    private ThreadPoolExecutor mThreadPoolExecutor;

    @Mock
    private Handler mUiHandler;

    @Before
    public void setUp() throws Exception {
        mCut = new UseCaseThreadPoolScheduler(mThreadPoolExecutor, mUiHandler);
    }

    @Test
    public void testGivenRunnableWhenExecuteThenThreadPoolExecuted() throws Exception {
        Runnable runnable = mock(Runnable.class);

        // When
        mCut.execute(runnable);

        // Then
        verify(mThreadPoolExecutor, times(1)).execute(runnable);
    }

    @Test
    public void testWhenNotifyResponseThenPostOnUiHandler() throws Exception {
        // When
        mCut.onSuccess(mock(BaseUseCase.ResponseValues.class), mock(BaseUseCase.UseCaseCallback.class));

        // Then
        verify(mUiHandler, times(1)).post(any(Runnable.class));
    }

    @Test
    public void testWhenNotifyResponseThenCallbackOnSuccess() throws Exception {
        ArgumentCaptor<Runnable> argumentCaptor = ArgumentCaptor.forClass(Runnable.class);
        BaseUseCase.UseCaseCallback useCaseCallback = mock(BaseUseCase.UseCaseCallback.class);
        BaseUseCase.ResponseValues responseValues = mock(BaseUseCase.ResponseValues.class);

        // When
        mCut.onSuccess(responseValues, useCaseCallback);

        // Then
        verify(mUiHandler, times(1)).post(argumentCaptor.capture());
        argumentCaptor.getValue().run();
        verify(useCaseCallback, times(1)).onSuccess(responseValues);
    }

    @Test
    public void testWhenOnErrorThenPostOnUiHandler() throws Exception {
        // When
        mCut.onError(mock(BaseUseCase.UseCaseCallback.class));

        // Then
        verify(mUiHandler, times(1)).post(any(Runnable.class));
    }

    @Test
    public void testWhenOnErrorThenCallbackOnError() throws Exception {
        ArgumentCaptor<Runnable> argumentCaptor = ArgumentCaptor.forClass(Runnable.class);
        BaseUseCase.UseCaseCallback useCaseCallback = mock(BaseUseCase.UseCaseCallback.class);

        // When
        mCut.onError(useCaseCallback);

        // Then
        verify(mUiHandler, times(1)).post(argumentCaptor.capture());
        argumentCaptor.getValue().run();
        verify(useCaseCallback, times(1)).onError();
    }
}