package com.davidecirillo.mvpcleansample.common.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(PowerMockRunner.class)
public class UseCaseHandlerTest {

    private UseCaseHandler mCut;

    @Mock
    private UseCaseScheduler mUseCaseScheduler;

    @Before
    public void setUp() throws Exception {
        mCut = new UseCaseHandler(mUseCaseScheduler);
    }

    @Test
    public void testWhenExecuteThenSetRequestValues() throws Exception {
        // When
        BaseUseCase baseUseCase = mock(BaseUseCase.class);
        BaseUseCase.RequestValues values = mock(BaseUseCase.RequestValues.class);
        mCut.execute(baseUseCase, values, mock(BaseUseCase.UseCaseCallback.class));

        // Then
        verify(baseUseCase, times(1)).setRequestValues(values);
    }

    @Test
    public void testWhenExecuteThenSetUseCaseCallback() throws Exception {
        // When
        BaseUseCase baseUseCase = mock(BaseUseCase.class);
        BaseUseCase.RequestValues values = mock(BaseUseCase.RequestValues.class);
        BaseUseCase.UseCaseCallback callback = mock(BaseUseCase.UseCaseCallback.class);
        mCut.execute(baseUseCase, values, callback);

        // Then
        verify(baseUseCase, times(1)).setUseCaseCallback(any(BaseUseCase.UseCaseCallback.class));
    }

    @Test
    public void testWhenExecuteThenExecuteOnScheduler() throws Exception {
        // When
        BaseUseCase baseUseCase = mock(BaseUseCase.class);
        BaseUseCase.RequestValues values = mock(BaseUseCase.RequestValues.class);
        BaseUseCase.UseCaseCallback callback = mock(BaseUseCase.UseCaseCallback.class);
        mCut.execute(baseUseCase, values, callback);

        // Then
        verify(mUseCaseScheduler, times(1)).execute(any(Runnable.class));
    }

    @Test
    public void testWhenExecuteThenUseCaseRun() throws Exception {
        ArgumentCaptor<Runnable> argumentCaptor = ArgumentCaptor.forClass(Runnable.class);
        BaseUseCase useCase = mock(BaseUseCase.class);

        // When
        mCut.execute(useCase, mock(BaseUseCase.RequestValues.class), mock(BaseUseCase.UseCaseCallback.class));

        // Then
        verify(mUseCaseScheduler, times(1)).execute(argumentCaptor.capture());
        argumentCaptor.getValue().run();
        verify(useCase, times(1)).run();
    }
}