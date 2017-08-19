package com.davidecirillo.mvpcleansample.show_notes.data;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.davidecirillo.mvpcleansample.show_notes.domain.model.NoteDomainModel;
import com.davidecirillo.mvpcleansample.utils.Prefs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

public class PrefsNotesRepositoryImplTest {

    private NotesRepository mCut;
    private Context mContext;

    @Before
    public void setUp() throws Exception {
        mContext = InstrumentationRegistry.getTargetContext();
        mCut = new PrefsNotesRepositoryImpl(mContext);
    }

    @After
    public void tearDown() throws Exception {
        Prefs.clearPreferencesSync(mContext);
    }

    @Test
    public void testWhenSaveNoteThenNoteSalvedInPrefs() throws Exception {
        // When
        String expectedText = "Test";
        mCut.saveNote(new NoteDomainModel(expectedText));

        // Then
        ArrayList<NoteDomainModel> noteDomainModels = mCut.getNotes();
        assertEquals(1, noteDomainModels.size());
        assertEquals(expectedText, noteDomainModels.get(0).getText());
    }

    @Test
    public void testGivenOneNoteSavedWhenGetNotesThenSizeAndContentCorrectReturned() throws Exception {
        // Given
        String expectedText = "Text";
        mCut.saveNote(new NoteDomainModel(expectedText));

        // When
        ArrayList<NoteDomainModel> noteDomainModels = mCut.getNotes();

        // Then
        assertEquals(1, noteDomainModels.size());
        assertEquals(expectedText, noteDomainModels.get(0).getText());
    }

    @Test
    public void testGivenTwoNotesAddedWhenDeleteFirstNoteThenOnlySecondNoteInMemory() throws Exception {
        // Given
        NoteDomainModel noteDomainModel = new NoteDomainModel("Test");
        NoteDomainModel noteDomainModel1 = new NoteDomainModel("Test1");
        mCut.saveNote(noteDomainModel);
        mCut.saveNote(noteDomainModel1);
        assertEquals(2, mCut.getNotes().size());

        // When
        mCut.deleteNote(noteDomainModel);

        // Then
        assertEquals(1, mCut.getNotes().size());
        assertEquals(noteDomainModel1, mCut.getNotes().get(0));
    }
}