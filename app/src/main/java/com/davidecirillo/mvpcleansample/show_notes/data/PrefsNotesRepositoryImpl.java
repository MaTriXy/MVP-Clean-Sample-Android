package com.davidecirillo.mvpcleansample.show_notes.data;


import android.content.Context;

import com.davidecirillo.mvpcleansample.R;
import com.davidecirillo.mvpcleansample.show_notes.domain.model.NoteDomainModel;
import com.davidecirillo.mvpcleansample.utils.Prefs;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PrefsNotesRepositoryImpl implements NotesRepository {

    private Gson mGson;
    private Type mNoteListType;
    private Context mContext;

    public PrefsNotesRepositoryImpl(Context context) {
        mContext = context;
        mGson = new Gson();
        mNoteListType = new TypeToken<ArrayList<NoteDomainModel>>() {
        }.getType();
    }

    @Override
    public void saveNote(NoteDomainModel noteDomainModel) {
        ArrayList<NoteDomainModel> noteDomainModels = getNotes();
        noteDomainModels.add(noteDomainModel);
        Prefs.savePreference(mContext, R.string.prefs_note_list, mGson.toJson(noteDomainModels, mNoteListType));
    }

    @Override
    public void deleteNote(NoteDomainModel noteDomainModel) {
        ArrayList<NoteDomainModel> noteDomainModels = getNotes();
        noteDomainModels.remove(noteDomainModel);
        Prefs.savePreference(mContext, R.string.prefs_note_list, mGson.toJson(noteDomainModels, mNoteListType));
    }

    @Override
    public ArrayList<NoteDomainModel> getNotes() {
        String stringPreference = Prefs.getStringPreference(mContext, R.string.prefs_note_list);
        ArrayList<NoteDomainModel> noteDomainModels = mGson.fromJson(stringPreference, mNoteListType);

        if(noteDomainModels == null){
            noteDomainModels = new ArrayList<>();
        }
        return noteDomainModels;
    }
}
