package com.davidecirillo.mvpcleansample.show_notes.domain.model;


public class NoteDomainModel {

    private String mText;

    public NoteDomainModel(String string) {
        mText = string;
    }

    public String getText() {
        return mText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NoteDomainModel noteDomainModel = (NoteDomainModel) o;

        return getText().equals(noteDomainModel.getText());

    }

    @Override
    public int hashCode() {
        return getText().hashCode();
    }
}
