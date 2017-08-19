package com.davidecirillo.mvpcleansample.add_note.presentation;

import android.util.Log;

import com.davidecirillo.mvpcleansample.add_note.domain.usecase.ValidateFieldsUseCase;
import com.davidecirillo.mvpcleansample.common.domain.BaseUseCase;
import com.davidecirillo.mvpcleansample.common.domain.UseCaseHandler;
import com.davidecirillo.mvpcleansample.show_notes.presentation.viewmodel.NoteViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Log.class)
public class AddNotePresenterTest {

    public static final String VALIDATION_ERROR_MESSAGE = "Please add some text";
    private AddNotePresenter mCut;

    @Mock
    private UseCaseHandler mUseCaseHandler;

    @Mock
    private ValidateFieldsUseCase mValidateFieldsUseCase;

    @Mock
    private AddNoteContract.View mView;

    @Before
    public void setUp() throws Exception {
        mockStatic(Log.class);

        mCut = new AddNotePresenter(mUseCaseHandler, mValidateFieldsUseCase);
        mCut.bind(mView);
    }

    @Test
    public void testWhenValidateFieldsThenCorrectUseCaseExecuted() throws Exception {
        // When
        mCut.validateFields("");

        //Then
        verify(mUseCaseHandler, times(1)).execute(
                eq(mValidateFieldsUseCase),
                any(ValidateFieldsUseCase.RequestValues.class),
                any(BaseUseCase.UseCaseCallback.class));
    }

    @Test
    public void testGivenValidateFieldsWhenUseCaseSuccessThenSubmitResults() throws Exception {
        ArgumentCaptor<BaseUseCase.UseCaseCallback> argumentCaptor = ArgumentCaptor.forClass(BaseUseCase.UseCaseCallback.class);

        // Given
        mCut.validateFields("");

        // When
        verify(mUseCaseHandler, times(1)).execute(
                eq(mValidateFieldsUseCase),
                any(ValidateFieldsUseCase.RequestValues.class),
                argumentCaptor.capture());
        argumentCaptor.getValue().onSuccess(Matchers.anyObject());

        //Then
        verify(mView, times(1)).submitResults(any(NoteViewModel.class));
    }

    @Test
    public void testGivenValidateFieldsWhenUseCaseErrorThenShowError() throws Exception {
        ArgumentCaptor<BaseUseCase.UseCaseCallback> argumentCaptor = ArgumentCaptor.forClass(BaseUseCase.UseCaseCallback.class);

        // When
        mCut.validateFields("");

        //Then
        verify(mUseCaseHandler, times(1)).execute(
                eq(mValidateFieldsUseCase),
                any(ValidateFieldsUseCase.RequestValues.class),
                argumentCaptor.capture());
        argumentCaptor.getValue().onError();
        verify(mView, times(1)).showError(VALIDATION_ERROR_MESSAGE);
    }
}