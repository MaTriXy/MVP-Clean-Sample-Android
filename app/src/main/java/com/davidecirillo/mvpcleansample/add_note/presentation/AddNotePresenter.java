package com.davidecirillo.mvpcleansample.add_note.presentation;


import android.icu.util.Calendar;
import android.support.annotation.NonNull;

import com.davidecirillo.mvpcleansample.add_note.domain.usecase.ValidateFieldsUseCase;
import com.davidecirillo.mvpcleansample.common.domain.BaseUseCase;
import com.davidecirillo.mvpcleansample.common.domain.UseCaseHandler;
import com.davidecirillo.mvpcleansample.common.presentation.BasePresenter;
import com.davidecirillo.mvpcleansample.show_notes.presentation.viewmodel.NoteViewModel;

class AddNotePresenter extends BasePresenter<AddNoteContract.View> implements AddNoteContract.Presenter {

    private ValidateFieldsUseCase mValidateFieldsUseCase;

    AddNotePresenter(@NonNull UseCaseHandler useCaseHandler, @NonNull ValidateFieldsUseCase validateFieldsUseCase) {
        super(useCaseHandler);
        mValidateFieldsUseCase = validateFieldsUseCase;
    }

    @Override
    public void validateFields(final String text, final String title) {
        mUseCaseHandler.execute(mValidateFieldsUseCase,
                new ValidateFieldsUseCase.RequestValues(title, text),
                new BaseUseCase.UseCaseCallback<ValidateFieldsUseCase.ResponseValues>() {


                    @Override
                    public void onSuccess(ValidateFieldsUseCase.ResponseValues response) {
                        getView().submitResults(new NoteViewModel(title, text, Calendar.getInstance().getTime()));
                    }

                    @Override
                    public void onError() {
                        getView().showError("Please complete all the fields");
                    }
                });
    }
}
