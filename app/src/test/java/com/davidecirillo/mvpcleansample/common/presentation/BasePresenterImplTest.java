package com.davidecirillo.mvpcleansample.common.presentation;

import android.util.Log;

import com.davidecirillo.mvpcleansample.common.domain.UseCaseHandler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Log.class)
public class BasePresenterImplTest {

    private BasePresenterImpl mCut;

    @Before
    public void setUp() throws Exception {
        mockStatic(Log.class);

        mCut = new BasePresenterImpl(mock(UseCaseHandler.class));
    }

    @Test
    public void testGivenBindViewWhenUnbindThenViewNull() throws Exception {
        // Given
        mCut.bind(mock(BaseView.class));

        // When
        mCut.unbind();

        // Then
        assertNull(mCut.getView());
    }

    @Test
    public void givenViewWhenBindThenGetViewReturnTheExpectedView() throws Exception {
        // Given
        BaseView mock = mock(BaseView.class);

        // When
        mCut.bind(mock);

        // Then
        assertEquals(mock, mCut.getView());
    }
}