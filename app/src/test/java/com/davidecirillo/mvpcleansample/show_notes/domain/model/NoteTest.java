package com.davidecirillo.mvpcleansample.show_notes.domain.model;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class NoteTest {

    @Test
    public void testAssertNotesAreEquals() throws Exception {
        NoteDomainModel noteDomainModel1 = new NoteDomainModel("ABC");
        NoteDomainModel noteDomainModel2 = new NoteDomainModel("ABC");

        assertTrue(noteDomainModel1.equals(noteDomainModel2));
    }

    @Test
    public void testAssertNotesAreNotEquals() throws Exception {
        NoteDomainModel noteDomainModel1 = new NoteDomainModel("ABC");
        NoteDomainModel noteDomainModel2 = new NoteDomainModel("AB");

        assertFalse(noteDomainModel1.equals(noteDomainModel2));
    }
}