package com.davidecirillo.mvpcleansample.show_notes.domain.usecase;


import com.davidecirillo.mvpcleansample.common.domain.BaseUseCase;
import com.davidecirillo.mvpcleansample.show_notes.data.NotesRepository;
import com.davidecirillo.mvpcleansample.show_notes.domain.model.NoteDomainModel;

public class SaveNoteUseCase extends BaseUseCase<SaveNoteUseCase.RequestValues, SaveNoteUseCase.ResponseValues> {

    private NotesRepository mNotesRepository;

    public SaveNoteUseCase(NotesRepository notesRepository) {
        mNotesRepository = notesRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        NoteDomainModel noteDomainModel = requestValues.getNoteDomainModel();
        mNotesRepository.saveNote(noteDomainModel);
        getUseCaseCallback().onSuccess(new ResponseValues());
    }

    public static class RequestValues implements BaseUseCase.RequestValues {

        private NoteDomainModel mNoteDomainModel;

        public RequestValues(NoteDomainModel noteDomainModel) {
            mNoteDomainModel = noteDomainModel;
        }

        NoteDomainModel getNoteDomainModel() {
            return mNoteDomainModel;
        }
    }

    public static class ResponseValues implements BaseUseCase.ResponseValues {

    }
}
