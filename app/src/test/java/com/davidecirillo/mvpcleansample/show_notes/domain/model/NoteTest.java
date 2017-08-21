package com.davidecirillo.mvpcleansample.show_notes.domain.model;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class NoteTest {

    @Test
    public void testAssertNotesAreEquals() throws Exception {
        NoteDomainModel noteDomainModel1 = new NoteDomainModel("ABC", "ABCD", 1L);
        NoteDomainModel noteDomainModel2 = new NoteDomainModel("ABC", "ABCD", 1L);

        assertTrue(noteDomainModel1.equals(noteDomainModel2));
    }

    @Test
    public void testAssertNotesAreNotEquals() throws Exception {
        NoteDomainModel noteDomainModel1 = new NoteDomainModel("ABC", "ACD", 0L);
        NoteDomainModel noteDomainModel2 = new NoteDomainModel("AB", "ABCD", 1L);

        assertFalse(noteDomainModel1.equals(noteDomainModel2));
    }
}