package com.davidecirillo.mvpcleansample.add_note.domain.usecase;


import com.davidecirillo.mvpcleansample.common.domain.BaseUseCase;

public class ValidateFieldsUseCase extends BaseUseCase<ValidateFieldsUseCase.RequestValues, ValidateFieldsUseCase.ResponseValues> {

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        String text = requestValues.getText();
        String title = requestValues.getTitle();
        boolean isValid = text != null && !text.isEmpty() &&
                title != null && !title.isEmpty();

        if (isValid) {
            getUseCaseCallback().onSuccess(new ResponseValues());
        } else {
            getUseCaseCallback().onError();
        }
    }

    public static class RequestValues implements BaseUseCase.RequestValues {

        private String mTitle;
        private String mText;

        public RequestValues(String title, String text) {
            mTitle = title;
            mText = text;
        }

        public String getTitle() {
            return mTitle;
        }

        public String getText() {
            return mText;
        }
    }

    public static class ResponseValues implements BaseUseCase.ResponseValues {

    }
}
