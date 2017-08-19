package com.davidecirillo.mvpcleansample.show_notes.domain.usecase;


import com.davidecirillo.mvpcleansample.common.domain.BaseUseCase;
import com.davidecirillo.mvpcleansample.show_notes.data.NotesRepository;
import com.davidecirillo.mvpcleansample.show_notes.domain.model.NoteDomainModel;

import java.util.ArrayList;
import java.util.List;

public class GetNotesUseCase extends BaseUseCase<GetNotesUseCase.RequestValues, GetNotesUseCase.ResponseValues> {

    private NotesRepository mNotesRepository;

    public GetNotesUseCase(NotesRepository notesRepository) {
        mNotesRepository = notesRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        ArrayList<NoteDomainModel> noteDomainModels = mNotesRepository.getNotes();
        getUseCaseCallback().onSuccess(new ResponseValues(noteDomainModels));
    }

    public static class RequestValues implements BaseUseCase.RequestValues {

    }

    public static class ResponseValues implements BaseUseCase.ResponseValues {
        private List<NoteDomainModel> mNoteDomainModels;

        public ResponseValues(List<NoteDomainModel> noteDomainModels) {
            mNoteDomainModels = noteDomainModels;
        }

        public List<NoteDomainModel> getNoteDomainModels() {
            return mNoteDomainModels;
        }
    }
}
