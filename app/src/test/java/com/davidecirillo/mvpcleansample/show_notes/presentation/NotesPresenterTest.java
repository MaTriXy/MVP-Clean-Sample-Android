package com.davidecirillo.mvpcleansample.show_notes.presentation;

import android.util.Log;

import com.davidecirillo.mvpcleansample.common.domain.BaseUseCase;
import com.davidecirillo.mvpcleansample.common.domain.UseCaseHandler;
import com.davidecirillo.mvpcleansample.show_notes.domain.model.NoteDomainModel;
import com.davidecirillo.mvpcleansample.show_notes.domain.usecase.DeleteNoteUseCase;
import com.davidecirillo.mvpcleansample.show_notes.domain.usecase.GetNotesUseCase;
import com.davidecirillo.mvpcleansample.show_notes.domain.usecase.SaveNoteUseCase;
import com.davidecirillo.mvpcleansample.show_notes.presentation.viewmodel.NoteViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class})
public class NotesPresenterTest {

    @Mock UseCaseHandler mUseCaseHandler;
    @Mock SaveNoteUseCase mSaveNoteUseCase;
    @Mock DeleteNoteUseCase mDeleteNoteUseCase;
    @Mock GetNotesUseCase mGetNotesUseCase;
    @Mock NotesContract.View mView;

    private NotesPresenter mCut;

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(Log.class);

        mCut = new NotesPresenter(mUseCaseHandler, mSaveNoteUseCase, mGetNotesUseCase, mDeleteNoteUseCase);
        mCut.bind(mView);
    }

    @Test
    public void testWhenPopulateNotesFromPrefsThenGetNotesUseCaseExecuted() throws Exception {
        // Given
        ArgumentCaptor<BaseUseCase> baseUseCaseArgumentCaptor = ArgumentCaptor.forClass(BaseUseCase.class);

        // When
        mCut.populateNotesFromPrefs();

        // Then
        verify(mUseCaseHandler, times(1)).execute(baseUseCaseArgumentCaptor.capture(), any(BaseUseCase.RequestValues.class), any(BaseUseCase.UseCaseCallback.class));
        assertTrue(baseUseCaseArgumentCaptor.getValue() instanceof GetNotesUseCase);
    }

    @Test
    public void testWhenPopulateNotesFromPrefs() throws Exception {
        // Given
        ArgumentCaptor<BaseUseCase.UseCaseCallback> argumentCaptor = ArgumentCaptor.forClass(BaseUseCase.UseCaseCallback.class);

        // When
        mCut.populateNotesFromPrefs();

        verify(mUseCaseHandler, times(1)).execute(any(BaseUseCase.class), any(BaseUseCase.RequestValues.class), argumentCaptor.capture());
        List<NoteDomainModel> notesFromPrefs = new ArrayList<>();
        notesFromPrefs.add(new NoteDomainModel("1"));
        notesFromPrefs.add(new NoteDomainModel("2"));
        argumentCaptor.getValue().onSuccess(new GetNotesUseCase.ResponseValues(notesFromPrefs));

        // Then
        verify(mView, times(2)).showNewNote(any(NoteViewModel.class));
    }

    @Test
    public void testWhenShowListOfEmptyPlaceholderCalledThenShowListOnView() throws Exception {
        // When
        mCut.showListOrEmptyPlaceholder(9, 1);

        // Then
        verify(mView, times(1)).showList();
    }

    @Test
    public void testWhenShowListOfEmptyPlaceholderCalledThenShoEmptyPlaceholder() throws Exception {
        // When
        mCut.showListOrEmptyPlaceholder(9, 0);

        // Then
        verify(mView, times(1)).showEmptyPlaceholder();
    }

    @Test
    public void testWhenSaveNoteToMemoryThenUseCaseExecuted() throws Exception {
        // Given
        ArgumentCaptor<BaseUseCase> argumentCaptor = ArgumentCaptor.forClass(BaseUseCase.class);

        // When
        mCut.saveNoteToMemory(mock(NoteViewModel.class));

        // Verify
        verify(mUseCaseHandler, times(1)).execute(argumentCaptor.capture(), any(BaseUseCase.RequestValues.class), any(BaseUseCase.UseCaseCallback.class));
        assertTrue(argumentCaptor.getValue() instanceof SaveNoteUseCase);
    }

    @Test
    public void testWhenDeleteNoteFromMemoryThenCorrectUseCaseExecuted() throws Exception {
        // Given
        ArgumentCaptor<BaseUseCase> argumentCaptor = ArgumentCaptor.forClass(BaseUseCase.class);

        // When
        mCut.deleteNoteFromMemory(mock(NoteViewModel.class));

        // Verify
        verify(mUseCaseHandler, times(1)).execute(argumentCaptor.capture(), any(BaseUseCase.RequestValues.class), any(BaseUseCase.UseCaseCallback.class));
        assertTrue(argumentCaptor.getValue() instanceof DeleteNoteUseCase);
    }
}