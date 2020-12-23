package com.example.isheunesu.you2can.ViewHolder;

        import android.support.v7.widget.RecyclerView;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.example.isheunesu.you2can.Interface.ItemClickListener;
        import com.example.isheunesu.you2can.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mCategoryText;
    public ImageView mCategoryImage;
    public ItemClickListener itemClickListener;

    public CategoryViewHolder(View itemView) {
        super(itemView);
        mCategoryImage=(ImageView)itemView.findViewById(R.id.category_image);
        mCategoryText=(TextView)itemView.findViewById(R.id.category_text);
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
