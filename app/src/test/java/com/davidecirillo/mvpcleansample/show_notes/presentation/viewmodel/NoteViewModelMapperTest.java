package com.davidecirillo.mvpcleansample.show_notes.presentation.viewmodel;

import com.davidecirillo.mvpcleansample.show_notes.domain.model.NoteDomainModel;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class NoteViewModelMapperTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testGivenNoteViewModelWithExpectedTextWhenMapToNoteThenNoteHasExpectedText() throws Exception {
        // Given
        String expectedText = "1";
        NoteViewModel noteViewModel = new NoteViewModel(expectedText);

        // When
        NoteDomainModel noteDomainModel = NoteViewModelMapper.toDomainModel(noteViewModel);

        // Then
        assertEquals(expectedText, noteDomainModel.getText());
    }

    @Test
    public void testGivenNoteWithExpectedTextWhenMapToNoteViewModelThenNoteViewModelHasExpectedText() throws Exception {
        // Given
        String expectedText = "1";
        NoteDomainModel noteDomainModel = new NoteDomainModel(expectedText);

        // When
        NoteViewModel noteViewModel = NoteViewModelMapper.toViewModel(noteDomainModel);

        // Then
        assertEquals(expectedText, noteViewModel.getText());
    }
}