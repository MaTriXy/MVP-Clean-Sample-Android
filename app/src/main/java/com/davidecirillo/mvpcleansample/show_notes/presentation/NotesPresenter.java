package com.davidecirillo.mvpcleansample.show_notes.presentation;


import com.davidecirillo.mvpcleansample.common.domain.BaseUseCase;
import com.davidecirillo.mvpcleansample.common.domain.EmptyUseCaseCallback;
import com.davidecirillo.mvpcleansample.common.domain.UseCaseHandler;
import com.davidecirillo.mvpcleansample.common.presentation.BasePresenter;
import com.davidecirillo.mvpcleansample.show_notes.domain.model.NoteDomainModel;
import com.davidecirillo.mvpcleansample.show_notes.domain.usecase.DeleteNoteUseCase;
import com.davidecirillo.mvpcleansample.show_notes.domain.usecase.GetNotesUseCase;
import com.davidecirillo.mvpcleansample.show_notes.domain.usecase.SaveNoteUseCase;
import com.davidecirillo.mvpcleansample.show_notes.presentation.viewmodel.NoteViewModel;
import com.davidecirillo.mvpcleansample.show_notes.presentation.viewmodel.NoteViewModelMapper;

class NotesPresenter extends BasePresenter<NotesContract.View> implements NotesContract.Presenter {

    private SaveNoteUseCase mSaveNoteUseCase;
    private final GetNotesUseCase mGetNotesUseCase;
    private final DeleteNoteUseCase mDeleteNoteUseCase;

    NotesPresenter(UseCaseHandler useCaseHandler,
                   SaveNoteUseCase saveNoteUseCase,
                   GetNotesUseCase getNotesUseCase,
                   DeleteNoteUseCase deleteNoteUseCase) {
        super(useCaseHandler);
        mSaveNoteUseCase = saveNoteUseCase;
        mGetNotesUseCase = getNotesUseCase;
        mDeleteNoteUseCase = deleteNoteUseCase;
    }

    @Override
    public void populateNotesFromPrefs() {
        mUseCaseHandler.execute(mGetNotesUseCase, new GetNotesUseCase.RequestValues(), new BaseUseCase.UseCaseCallback<GetNotesUseCase.ResponseValues>() {

            @Override
            public void onSuccess(GetNotesUseCase.ResponseValues response) {
                // Iterate through the domain models, convert them into view models and show them to the screen
                for (NoteDomainModel noteDomainModel : response.getNoteDomainModels()) {
                    NoteViewModel noteViewModel = NoteViewModelMapper.toViewModel(noteDomainModel);

                    getView().showNewNote(noteViewModel);
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void showListOrEmptyPlaceholder(int oldItemCount, int newItemCount) {
        if (newItemCount > 0) {
            getView().showList();
        } else {
            getView().showEmptyPlaceholder();
        }
    }

    @Override
    public void saveNoteToMemory(NoteViewModel noteViewModel) {
        // Save note to prefs
        NoteDomainModel noteDomainModel = NoteViewModelMapper.toDomainModel(noteViewModel);
        mUseCaseHandler.execute(mSaveNoteUseCase, new SaveNoteUseCase.RequestValues(noteDomainModel), new EmptyUseCaseCallback<SaveNoteUseCase.ResponseValues>());
    }

    @Override
    public void deleteNoteFromMemory(NoteViewModel noteViewModel) {
        NoteDomainModel noteDomainModel = NoteViewModelMapper.toDomainModel(noteViewModel);
        mUseCaseHandler.execute(mDeleteNoteUseCase, new DeleteNoteUseCase.RequestValues(noteDomainModel), new EmptyUseCaseCallback<DeleteNoteUseCase.ResponseValues>());
    }
}
