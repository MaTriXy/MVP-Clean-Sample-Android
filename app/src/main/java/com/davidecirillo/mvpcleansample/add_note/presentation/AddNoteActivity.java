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
import com.davidecirillo.mvpcleansample.common.presentation.BasePresenterImpl;
import com.davidecirillo.mvpcleansample.show_notes.presentation.viewmodel.NoteViewModel;

public class AddNoteActivity extends BaseActivity implements AddNoteContract.View {

    public final static String KEY_NOTE_VIEW_MODEL_EXTRA = "NOTE_VIEW_MODEL_EXTRA";
    private EditText mTextNoteEditText;

    public static Intent getIntent(Context context){
        return new Intent(context, AddNoteActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_note);

        mTextNoteEditText = (EditText) findViewById(R.id.note_text);
        mTextNoteEditText.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
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

    @Override
    protected BasePresenterImpl createPresenter() {
        ValidateFieldsUseCase validateFieldsUseCase = new ValidateFieldsUseCase();
        return new AddNotePresenter(mUseCaseHandler, validateFieldsUseCase);
    }

    public void onAddNoteClick(View view) {
        ((AddNotePresenter) mBasePresenter).validateFields(mTextNoteEditText.getText().toString());
    }
}
