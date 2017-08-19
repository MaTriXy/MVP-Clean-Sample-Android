package com.davidecirillo.mvpcleansample.show_notes.presentation.viewmodel;


import android.os.Parcel;
import android.os.Parcelable;

public class NoteViewModel implements Parcelable {

    private String mText;

    public NoteViewModel(String text) {
        mText = text;
    }

    protected NoteViewModel(Parcel in) {
        mText = in.readString();
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

    public String getText() {
        return mText;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mText);
    }
}
