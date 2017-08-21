package com.davidecirillo.mvpcleansample.show_notes.presentation.viewmodel;

import com.davidecirillo.mvpcleansample.show_notes.domain.model.NoteDomainModel;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;

public class NoteViewModelMapperTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testGivenNoteViewModelWithExpectedTextWhenMapToNoteThenNoteHasExpectedText() throws Exception {
        // Given
        String expectedTitle = "title";
        String expectedText = "text";
        Date expectedDate = mock(Date.class);
        NoteViewModel noteViewModel = new NoteViewModel(expectedTitle, expectedText, expectedDate);

        // When
        NoteDomainModel noteDomainModel = NoteViewModelMapper.toDomainModel(noteViewModel);

        // Then
        assertEquals(expectedText, noteDomainModel.getText());
    }

    @Test
    public void testGivenNoteViewModelWithExpectedTitleWhenMapToNoteThenNoteHasExpectedTitle() throws Exception {
        // Given
        String expectedTitle = "title";
        String expectedText = "text";
        Date expectedDate = mock(Date.class);
        NoteViewModel noteViewModel = new NoteViewModel(expectedTitle, expectedText, expectedDate);

        // When
        NoteDomainModel noteDomainModel = NoteViewModelMapper.toDomainModel(noteViewModel);

        // Then
        assertEquals(expectedTitle, noteDomainModel.getTitle());
    }

    @Test
    public void testGivenNoteViewModelWithExpectedDateWhenMapToNoteThenNoteHasExpectedDate() throws Exception {
        // Given
        String expectedTitle = "title";
        String expectedText = "text";
        Date expectedDate = Calendar.getInstance().getTime();
        NoteViewModel noteViewModel = new NoteViewModel(expectedTitle, expectedText, expectedDate);

        // When
        NoteDomainModel noteDomainModel = NoteViewModelMapper.toDomainModel(noteViewModel);

        // Then
        assertEquals(Long.valueOf(expectedDate.getTime()), noteDomainModel.getCreatedAt());
    }

    @Test
    public void testGivenNoteWithExpectedTextWhenMapToNoteViewModelThenNoteViewModelHasExpectedText() throws Exception {
        // Given
        String expectedTitle = "Title";
        String expectedText = "Text";
        Long expectedTimestamp = 5L;
        NoteDomainModel noteDomainModel = new NoteDomainModel(expectedTitle, expectedText, expectedTimestamp);

        // When
        NoteViewModel noteViewModel = NoteViewModelMapper.toViewModel(noteDomainModel);

        // Then
        assertEquals(expectedText, noteViewModel.getText());
    }

    @Test
    public void testGivenNoteWithExpectedTitleWhenMapToNoteViewModelThenNoteViewModelHasExpectedTitle() throws Exception {
        // Given
        String expectedTitle = "Title";
        String expectedText = "Text";
        Long expectedTimestamp = 5L;
        NoteDomainModel noteDomainModel = new NoteDomainModel(expectedTitle, expectedText, expectedTimestamp);

        // When
        NoteViewModel noteViewModel = NoteViewModelMapper.toViewModel(noteDomainModel);

        // Then
        assertEquals(expectedTitle, noteViewModel.getTitle());
    }

    @Test
    public void testGivenNoteWithExpectedTimestampWhenMapToNoteViewModelThenNoteViewModelHasExpectedTimestamp() throws Exception {
        // Given
        String expectedTitle = "Title";
        String expectedText = "Text";
        Long expectedTimestamp = 5L;
        NoteDomainModel noteDomainModel = new NoteDomainModel(expectedTitle, expectedText, expectedTimestamp);

        // When
        NoteViewModel noteViewModel = NoteViewModelMapper.toViewModel(noteDomainModel);

        // Then
        assertEquals(expectedTimestamp, Long.valueOf(noteViewModel.getCreatedAtDate().getTime()));
    }
}