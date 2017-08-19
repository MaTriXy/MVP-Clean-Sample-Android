package com.davidecirillo.mvpcleansample.show_notes.presentation.notelist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.davidecirillo.mvpcleansample.R;
import com.davidecirillo.mvpcleansample.show_notes.presentation.viewmodel.NoteViewModel;


class NoteViewHolder extends RecyclerView.ViewHolder {

    NoteViewHolder(View itemView) {
        super(itemView);
    }

    void bind(NoteViewModel noteViewModel) {
        ((TextView) itemView.findViewById(R.id.text)).setText(noteViewModel.getText());
    }
}
