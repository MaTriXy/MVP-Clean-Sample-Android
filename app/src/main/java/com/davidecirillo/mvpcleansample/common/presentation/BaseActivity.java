package com.davidecirillo.mvpcleansample.common.presentation;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.davidecirillo.mvpcleansample.common.domain.UseCaseHandler;
import com.davidecirillo.mvpcleansample.show_notes.data.PreferenceNotesRepository;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    protected UseCaseHandler mUseCaseHandler;
    protected PreferenceNotesRepository mPrefsNoteRepository;

    @Nullable
    private BasePresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
    }

    private void injectDependencies() {
        mUseCaseHandler = UseCaseHandler.getInstance();
        mPrefsNoteRepository = new PreferenceNotesRepository(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.bind(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.unbind();
        }
    }

    /**
     * After this method will be called, the presenter will be automatically bound/unbound to the view following the activity lifecycle
     * @param presenter Presenter to be bound
     */
    protected void bindPresenterToView(BasePresenter presenter) {
        mPresenter = presenter;
    }
}
