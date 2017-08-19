package com.davidecirillo.mvpcleansample.add_note.presentation;


import android.support.annotation.NonNull;

import com.davidecirillo.mvpcleansample.add_note.domain.usecase.ValidateFieldsUseCase;
import com.davidecirillo.mvpcleansample.common.domain.BaseUseCase;
import com.davidecirillo.mvpcleansample.common.domain.UseCaseHandler;
import com.davidecirillo.mvpcleansample.common.presentation.BasePresenterImpl;
import com.davidecirillo.mvpcleansample.show_notes.presentation.viewmodel.NoteViewModel;

class AddNotePresenter extends BasePresenterImpl<AddNoteContract.View> implements AddNoteContract.Presenter {

    private ValidateFieldsUseCase mValidateFieldsUseCase;

    AddNotePresenter(@NonNull UseCaseHandler useCaseHandler, @NonNull ValidateFieldsUseCase validateFieldsUseCase) {
        super(useCaseHandler);
        mValidateFieldsUseCase = validateFieldsUseCase;
    }

    @Override
    public void validateFields(final String text) {
        mUseCaseHandler.execute(mValidateFieldsUseCase,
                new ValidateFieldsUseCase.RequestValues(text),
                new BaseUseCase.UseCaseCallback<ValidateFieldsUseCase.ResponseValues>() {


                    @Override
                    public void onSuccess(ValidateFieldsUseCase.ResponseValues response) {
                        getView().submitResults(new NoteViewModel(text));
                    }

                    @Override
                    public void onError() {
                        getView().showError("Please add some text");
                    }
                });
    }
}
