package com.example.isheunesu.you2can.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.isheunesu.you2can.Interface.ItemClickListener;
import com.example.isheunesu.you2can.R;

public class RankingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mTextName,mTextScore;
    private ItemClickListener itemClickListener;
    public RankingViewHolder(View itemView) {
        super(itemView);
        mTextScore=(TextView) itemView.findViewById(R.id.ranking_text_name);
        mTextName=(TextView) itemView.findViewById(R.id.ranking_text_score);
       itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
