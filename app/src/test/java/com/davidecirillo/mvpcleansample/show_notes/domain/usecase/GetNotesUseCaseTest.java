package com.davidecirillo.mvpcleansample.show_notes.domain.usecase;

import com.davidecirillo.mvpcleansample.common.domain.BaseUseCase;
import com.davidecirillo.mvpcleansample.show_notes.data.NotesRepository;
import com.davidecirillo.mvpcleansample.show_notes.domain.model.NoteDomainModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
public class GetNotesUseCaseTest {

    private GetNotesUseCase mCut;

    @Mock
    private NotesRepository mNotesRepository;

    @Mock
    private BaseUseCase.UseCaseCallback<GetNotesUseCase.ResponseValues> mResponseValueUseCaseCallback;

    @Before
    public void setUp() throws Exception {
        mCut = new GetNotesUseCase(mNotesRepository);
        mCut.setUseCaseCallback(mResponseValueUseCaseCallback);
    }

    @Test
    public void testWhenExecuteUseCaseThenGetNotesCalled() throws Exception {
        // Given

        // When
        mCut.executeUseCase(new GetNotesUseCase.RequestValues());

        // Then
        verify(mNotesRepository, times(1)).getNotes();
    }

    @Test
    public void testGivenListOfNoteReturnedByRepositoryWhenExecuteUseCaseThenOnSuccessWithCorrectNotes() throws Exception {
        ArgumentCaptor<GetNotesUseCase.ResponseValues> argumentCaptor = ArgumentCaptor.forClass(GetNotesUseCase.ResponseValues.class);

        // Given
        ArrayList<NoteDomainModel> noteDomainModels = new ArrayList<>();
        noteDomainModels.add(new NoteDomainModel(""));
        given(mNotesRepository.getNotes()).willReturn(noteDomainModels);

        // When
        mCut.executeUseCase(new GetNotesUseCase.RequestValues());

        // Then
        verify(mResponseValueUseCaseCallback, times(1)).onSuccess(argumentCaptor.capture());
        assertEquals(noteDomainModels, argumentCaptor.getValue().getNoteDomainModels());
    }

}