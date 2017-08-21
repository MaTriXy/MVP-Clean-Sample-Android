package com.davidecirillo.mvpcleansample.show_notes.presentation.viewmodel;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class NoteViewModel implements Parcelable {

    private String mTitle;
    private String mText;
    private Date mCreatedAt;

    public NoteViewModel(String title, String text, Date createdAt) {
        mTitle = title;
        mText = text;
        mCreatedAt = createdAt;
    }

    protected NoteViewModel(Parcel in) {
        mTitle = in.readString();
        mText = in.readString();
        mCreatedAt = (Date) in.readSerializable();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mText);
        dest.writeSerializable(mCreatedAt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NoteViewModel> CREATOR = new Creator<NoteViewModel>() {
        @Override
        public NoteViewModel createFromParcel(Parcel in) {
            return new NoteViewModel(in);
        }

        @Override
        public NoteViewModel[] newArray(int size) {
            return new NoteViewModel[size];
        }
    };

    public String getTitle() {
        return mTitle;
    }

    public String getText() {
        return mText;
    }

    public Date getCreatedAtDate() {
        return mCreatedAt;
    }

}
