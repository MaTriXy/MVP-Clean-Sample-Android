package com.davidecirillo.mvpcleansample.show_notes.data;


import com.davidecirillo.mvpcleansample.show_notes.domain.model.NoteDomainModel;

import java.util.ArrayList;

public interface NotesRepository {

    void saveNote(NoteDomainModel noteDomainModel);

    void deleteNote(NoteDomainModel noteDomainModel);

    ArrayList<NoteDomainModel> getNotes();
}
