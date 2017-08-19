package com.davidecirillo.mvpcleansample.show_notes.presentation.notelist;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davidecirillo.mvpcleansample.R;
import com.davidecirillo.mvpcleansample.show_notes.presentation.viewmodel.NoteViewModel;

import java.util.ArrayList;
import java.util.List;

public class NotesRecyclerViewAdapter extends RecyclerView.Adapter<NoteViewHolder> implements ItemTouchHelperAdapter {

    private List<NoteViewModel> mNoteViewModels;
    private NoteChangeObserver mNoteChangeObserver;

    public NotesRecyclerViewAdapter() {
        mNoteViewModels = new ArrayList<>();
    }

    public void addNote(NoteViewModel noteViewModel) {
        mNoteViewModels.add(noteViewModel);
        notifyItemInserted(mNoteViewModels.size() - 1);

        sendOnNoteListChanged(mNoteViewModels.size() - 1, mNoteViewModels.size());
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_note, parent, false);
        return new NoteViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        NoteViewModel noteViewModel = mNoteViewModels.get(position);
        holder.bind(noteViewModel);
    }

    @Override
    public int getItemCount() {
        return mNoteViewModels.size();
    }

    @Override
    public void onItemDismiss(int position) {
        NoteViewModel removedItem = mNoteViewModels.remove(position);
        notifyItemRemoved(position);

        sendOnNoteListChanged(mNoteViewModels.size() + 1, mNoteViewModels.size());
        sendOnItemDeleted(removedItem);
    }

    private void sendOnNoteListChanged(int oldCount, int newCount) {
        if (mNoteChangeObserver != null) {
            mNoteChangeObserver.onNoteListChanged(oldCount, newCount);
        }
    }

    private void sendOnItemDeleted(NoteViewModel removedItem) {
        if(mNoteChangeObserver != null){
            mNoteChangeObserver.onItemDeleted(removedItem);
        }
    }

    public void setNoteChangeObserver(NoteChangeObserver noteChangeObserver) {
        mNoteChangeObserver = noteChangeObserver;
    }

    /**
     * Observe the changes to the adapter data
     */
    public interface NoteChangeObserver {

        void onNoteListChanged(int oldItemCount, int newItemCount);

        void onItemDeleted(NoteViewModel noteViewModel);
    }

}
