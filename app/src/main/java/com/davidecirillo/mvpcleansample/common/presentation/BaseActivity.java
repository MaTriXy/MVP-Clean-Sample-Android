package com.davidecirillo.mvpcleansample.common.presentation;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.davidecirillo.mvpcleansample.common.domain.UseCaseHandler;
import com.davidecirillo.mvpcleansample.show_notes.data.PrefsNotesRepositoryImpl;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    protected UseCaseHandler mUseCaseHandler;
    protected PrefsNotesRepositoryImpl mPrefsNoteRepository;
    protected BasePresenterImpl mBasePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();

        mBasePresenter = createPresenter();
    }

    private void injectDependencies() {
        mUseCaseHandler = UseCaseHandler.getInstance();
        mPrefsNoteRepository = new PrefsNotesRepositoryImpl(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBasePresenter.bind(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBasePresenter.unbind();
    }

    protected abstract BasePresenterImpl createPresenter();
}
