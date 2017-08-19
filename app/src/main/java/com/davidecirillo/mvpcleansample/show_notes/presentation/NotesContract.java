package com.davidecirillo.mvpcleansample.show_notes.presentation;


import com.davidecirillo.mvpcleansample.common.presentation.BaseView;
import com.davidecirillo.mvpcleansample.show_notes.presentation.viewmodel.NoteViewModel;

interface NotesContract {

    interface View extends BaseView {
        void showNewNote(NoteViewModel noteViewModel);
        void showList();
        void showEmptyPlaceholder();
    }

    interface Presenter {
        void populateNotesFromPrefs();
        void showListOrEmptyPlaceholder(int oldItemCount, int newItemCount);
        void saveNoteToMemory(NoteViewModel activityResultIntent);
        void deleteNoteFromMemory(NoteViewModel noteViewModel);
    }

}
