package com.davidecirillo.mvpcleansample.common.domain;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mock;

public class BaseUseCaseTest {

    private BaseUseCase mCut;

    @Before
    public void setUp() throws Exception {
        mCut = new UseCaseTest();
    }

    @Test
    public void testWhenRunThenUseCaseExecuted() throws Exception {
        // When
        mCut.run();

        // Then
        assertTrue(((UseCaseTest) mCut).isExecuted());
    }

    @Test
    public void testWhenSetUserCaseCallbackThenCorrectUseCaseCallbackReturned() throws Exception {
        // When
        BaseUseCase.UseCaseCallback useCaseCallback = mock(BaseUseCase.UseCaseCallback.class);
        mCut.setUseCaseCallback(useCaseCallback);

        // Then
        assertEquals(useCaseCallback, mCut.getUseCaseCallback());
    }

    public static class UseCaseTest extends BaseUseCase{

        private boolean mExecuted;

        @Override
        protected void executeUseCase(RequestValues requestValues) {
            mExecuted = true;
        }

        public boolean isExecuted() {
            return mExecuted;
        }
    }

}