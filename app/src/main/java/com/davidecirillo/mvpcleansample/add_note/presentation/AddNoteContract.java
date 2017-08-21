package com.davidecirillo.mvpcleansample.add_note.presentation;


import com.davidecirillo.mvpcleansample.common.presentation.BaseView;
import com.davidecirillo.mvpcleansample.show_notes.presentation.viewmodel.NoteViewModel;

interface AddNoteContract {

    interface View extends BaseView {
        void submitResults(NoteViewModel noteViewModel);
        void showError(String error);
    }

    interface Presenter {
        void validateFields(String text, String title);
    }
}
