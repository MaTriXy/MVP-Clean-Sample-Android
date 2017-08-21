package com.davidecirillo.mvpcleansample.add_note.presentation;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.davidecirillo.mvpcleansample.R;
import com.davidecirillo.mvpcleansample.add_note.domain.usecase.ValidateFieldsUseCase;
import com.davidecirillo.mvpcleansample.common.presentation.BaseActivity;
import com.davidecirillo.mvpcleansample.show_notes.presentation.viewmodel.NoteViewModel;

public class AddNoteActivity extends BaseActivity implements AddNoteContract.View {

    public final static String KEY_NOTE_VIEW_MODEL_EXTRA = "NOTE_VIEW_MODEL_EXTRA";

    private EditText mContentEditText;
    private EditText mTitleEditText;
    private AddNotePresenter mPresenter;

    public static Intent getIntent(Context context){
        return new Intent(context, AddNoteActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_note);

        createPresenter();

        mContentEditText = (EditText) findViewById(R.id.note_text);
        mTitleEditText = (EditText) findViewById(R.id.note_title);

        // Request focus on first edit text
        mTitleEditText.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Add new note");
        }
    }

    @Override
    public void submitResults(NoteViewModel noteViewModel) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_NOTE_VIEW_MODEL_EXTRA, noteViewModel);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    protected void createPresenter() {
        ValidateFieldsUseCase validateFieldsUseCase = new ValidateFieldsUseCase();
        mPresenter = new AddNotePresenter(mUseCaseHandler, validateFieldsUseCase);
        bindPresenterToView(mPresenter);
    }

    public void onAddNoteClick(View view) {
        mPresenter.validateFields(
                mContentEditText.getText().toString(),
                mTitleEditText.getText().toString()
        );
    }
}
