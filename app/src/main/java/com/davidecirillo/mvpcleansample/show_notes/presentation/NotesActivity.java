package com.davidecirillo.mvpcleansample.show_notes.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ViewSwitcher;

import com.davidecirillo.mvpcleansample.R;
import com.davidecirillo.mvpcleansample.add_note.presentation.AddNoteActivity;
import com.davidecirillo.mvpcleansample.common.presentation.BaseActivity;
import com.davidecirillo.mvpcleansample.common.presentation.BasePresenterImpl;
import com.davidecirillo.mvpcleansample.show_notes.domain.usecase.DeleteNoteUseCase;
import com.davidecirillo.mvpcleansample.show_notes.domain.usecase.GetNotesUseCase;
import com.davidecirillo.mvpcleansample.show_notes.domain.usecase.SaveNoteUseCase;
import com.davidecirillo.mvpcleansample.show_notes.presentation.notelist.NotesRecyclerViewAdapter;
import com.davidecirillo.mvpcleansample.show_notes.presentation.notelist.SimpleItemTouchHelperCallback;
import com.davidecirillo.mvpcleansample.show_notes.presentation.viewmodel.NoteViewModel;

public class NotesActivity extends BaseActivity implements NotesContract.View, NotesRecyclerViewAdapter.NoteChangeObserver {

    private static final int ADD_NOTE_RESULT_CODE = 1234;
    private static final int EMPTY_PLACEHOLDER_POSITION = 0;
    private static final int RECYCLER_VIEW_POSITION = 1;

    private NotesRecyclerViewAdapter mAdapter;
    private Intent mActivityResultIntent;

    // Views
    private ViewSwitcher mViewSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        mViewSwitcher = (ViewSwitcher) findViewById(R.id.view_switcher);

        setupNoteRecyclerView();

        ((NotesPresenter) mBasePresenter).populateNotesFromPrefs();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mActivityResultIntent != null && mActivityResultIntent.hasExtra(AddNoteActivity.KEY_NOTE_VIEW_MODEL_EXTRA)) {
            NoteViewModel noteViewModel = mActivityResultIntent.getParcelableExtra(AddNoteActivity.KEY_NOTE_VIEW_MODEL_EXTRA);

            ((NotesPresenter) mBasePresenter).saveNoteToMemory(noteViewModel);
            mActivityResultIntent = null;

            showNewNote(noteViewModel);
        }
    }

    @Override
    protected BasePresenterImpl createPresenter() {
        SaveNoteUseCase saveNoteUseCase = new SaveNoteUseCase(mPrefsNoteRepository);
        GetNotesUseCase getNotesUseCase = new GetNotesUseCase(mPrefsNoteRepository);
        DeleteNoteUseCase deleteNoteUseCase = new DeleteNoteUseCase(mPrefsNoteRepository);
        return new NotesPresenter(mUseCaseHandler, saveNoteUseCase, getNotesUseCase, deleteNoteUseCase);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ADD_NOTE_RESULT_CODE) {
            mActivityResultIntent = data;
        }
    }

    @Override
    public void showNewNote(NoteViewModel noteViewModel) {
        mAdapter.addNote(noteViewModel);
    }

    @Override
    public void showList() {
        mViewSwitcher.setDisplayedChild(RECYCLER_VIEW_POSITION);
    }

    @Override
    public void showEmptyPlaceholder() {
        mViewSwitcher.setDisplayedChild(EMPTY_PLACEHOLDER_POSITION);
    }

    public void onFabButtonClick(View view) {
        startActivityForResult(AddNoteActivity.getIntent(this), ADD_NOTE_RESULT_CODE);
    }

    private void setupNoteRecyclerView() {
        mAdapter = new NotesRecyclerViewAdapter();
        mAdapter.setNoteChangeObserver(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.notes_recycler_view);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        // Swipe item touch helper
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onNoteListChanged(int oldItemCount, int newItemCount) {
        ((NotesPresenter) mBasePresenter).showListOrEmptyPlaceholder(oldItemCount, newItemCount);
    }

    @Override
    public void onItemDeleted(NoteViewModel noteViewModel) {
        ((NotesPresenter) mBasePresenter).deleteNoteFromMemory(noteViewModel);
    }
}
