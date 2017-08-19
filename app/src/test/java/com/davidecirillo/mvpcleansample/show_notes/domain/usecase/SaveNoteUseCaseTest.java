package com.davidecirillo.mvpcleansample.show_notes.domain.usecase;

import com.davidecirillo.mvpcleansample.show_notes.data.NotesRepository;
import com.davidecirillo.mvpcleansample.show_notes.domain.model.NoteDomainModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(PowerMockRunner.class)
public class SaveNoteUseCaseTest {

    private SaveNoteUseCase mCut;

    @Mock
    private NotesRepository mNotesRepository;

    @Mock
    private SaveNoteUseCase.UseCaseCallback<SaveNoteUseCase.ResponseValues> mResponseValueUseCaseCallback;

    @Before
    public void setUp() throws Exception {
        mCut = new SaveNoteUseCase(mNotesRepository);
        mCut.setUseCaseCallback(mResponseValueUseCaseCallback);
    }

    @Test
    public void testGivenRequestValuesWithNoteWhenExecuteUseCaseThenSaveNoteOnRepository() throws Exception {
        // Given
        SaveNoteUseCase.RequestValues requestValues = mock(SaveNoteUseCase.RequestValues.class);
        NoteDomainModel noteDomainModel = new NoteDomainModel("");
        given(requestValues.getNoteDomainModel()).willReturn(noteDomainModel);

        // When
        mCut.executeUseCase(requestValues);

        // Then
        verify(mNotesRepository, times(1)).saveNote(noteDomainModel);
    }

    @Test
    public void testGivenRequestValuesWithNoteWhenExecuteUseCaseThenOnSuccessCalledOnCallback() throws Exception {
        // Given
        SaveNoteUseCase.RequestValues requestValues = mock(SaveNoteUseCase.RequestValues.class);
        NoteDomainModel noteDomainModel = new NoteDomainModel("");
        given(requestValues.getNoteDomainModel()).willReturn(noteDomainModel);

        // When
        mCut.executeUseCase(requestValues);

        // Then
        verify(mResponseValueUseCaseCallback, times(1)).onSuccess(any(SaveNoteUseCase.ResponseValues.class));
    }
}