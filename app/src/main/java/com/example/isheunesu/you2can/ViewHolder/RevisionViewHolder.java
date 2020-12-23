package com.example.isheunesu.you2can.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.isheunesu.you2can.R;

public class RevisionViewHolder extends RecyclerView.ViewHolder {
    public TextView mRevisionQuestion,mRevisionAnswer;
    public RevisionViewHolder(View itemView) {
        super(itemView);
        itemView.findViewById(R.id.revision_question);
        itemView.findViewById(R.id.revision_answer);
    }

}
