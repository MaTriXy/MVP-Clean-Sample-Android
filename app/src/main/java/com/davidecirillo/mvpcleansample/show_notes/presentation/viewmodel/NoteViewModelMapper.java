package com.davidecirillo.mvpcleansample.show_notes.presentation.viewmodel;


import com.davidecirillo.mvpcleansample.show_notes.domain.model.NoteDomainModel;

public final class NoteViewModelMapper {

    public static NoteViewModel toViewModel(NoteDomainModel noteDomainModel){
        return new NoteViewModel(noteDomainModel.getText());
    }

    public static NoteDomainModel toDomainModel(NoteViewModel noteViewModel){
        return new NoteDomainModel(noteViewModel.getText());
    }
}
