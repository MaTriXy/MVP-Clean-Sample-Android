package com.davidecirillo.mvpcleansample.show_notes.presentation.notelist;

import android.icu.text.SimpleDateFormat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.davidecirillo.mvpcleansample.R;
import com.davidecirillo.mvpcleansample.show_notes.presentation.viewmodel.NoteViewModel;


class NoteViewHolder extends RecyclerView.ViewHolder {

    private final TextView mContentView;
    private final TextView mDateView;
    private final TextView mTitleView;

    NoteViewHolder(View itemView) {
        super(itemView);
        mContentView = itemView.findViewById(R.id.content);
        mDateView = itemView.findViewById(R.id.date);
        mTitleView = itemView.findViewById(R.id.title);
    }

    void bind(NoteViewModel noteViewModel) {
        mContentView.setText(noteViewModel.getText());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM - HH:mm");
        String formattedDate = simpleDateFormat.format(noteViewModel.getCreatedAtDate());
        mDateView.setText(formattedDate);

        mTitleView.setText(noteViewModel.getTitle());
    }
}
