package com.davidecirillo.mvpcleansample.show_notes.domain.model;


public class NoteDomainModel {

    private String mTitle;
    private String mText;
    private Long mCreatedAt;

    public NoteDomainModel(String title, String string, Long createdAt) {
        mTitle = title;
        mText = string;
        mCreatedAt = createdAt;
    }

    public String getText() {
        return mText;
    }

    public Long getCreatedAt() {
        return mCreatedAt;
    }

    public String getTitle() {
        return mTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NoteDomainModel that = (NoteDomainModel) o;

        return getText().equals(that.getText()) && getCreatedAt().equals(that.getCreatedAt());

    }

    @Override
    public int hashCode() {
        int result = getText().hashCode();
        result = 31 * result + getCreatedAt().hashCode();
        return result;
    }
}
