package com.example.isheunesu.you2can;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.isheunesu.you2can.BroadCastReceiver.AlarmReceiver;
import com.example.isheunesu.you2can.Global.Global;
import com.example.isheunesu.you2can.Interface.ItemClickListener;
import com.example.isheunesu.you2can.ViewHolder.CategoryViewHolder;
import com.example.isheunesu.you2can.model.Category;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Calendar;


public class CategoryFragment extends Fragment {
    private RecyclerView.LayoutManager layoutManager;
    RecyclerView listCategory;
    FirebaseRecyclerAdapter<Category,CategoryViewHolder> adapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference categories;
    View myFragment;
    public static CategoryFragment newInstance(){
        CategoryFragment categoryFragment=new CategoryFragment();
        return categoryFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseDatabase=FirebaseDatabase.getInstance();
        categories=firebaseDatabase.getReference("Category");
        registerAlarm();
    }

    private void registerAlarm() {

        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,6);
        calendar.set(Calendar.MINUTE,00);
        calendar.set(Calendar.SECOND,0);

        Intent intent=new Intent(getContext(), AlarmReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(getContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am=(AlarmManager)getContext().getSystemService(getContext().ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       myFragment=inflater.inflate(R.layout.fragment_category,container,false) ;
        listCategory=(RecyclerView)myFragment.findViewById(R.id.list_Category);
        listCategory.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(container.getContext());
        listCategory.setLayoutManager(layoutManager);
        loadCategories();
       return myFragment;


    }

    private void loadCategories() {
      adapter=new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(
              Category.class,
              R.layout.category_layout,
              CategoryViewHolder.class,
              categories
      ) {
          @Override
          protected void populateViewHolder(CategoryViewHolder viewHolder, final Category model, int position) {
             viewHolder.mCategoryText.setText(model.getName());
             Picasso.get()
                     .load(model.getImage())
                     .into(viewHolder.mCategoryImage);
             viewHolder.setItemClickListener(new ItemClickListener() {
                 @Override
                 public void onClick(View view, int position, boolean isLongClick) {
                     Intent startGame=new Intent(getActivity(),Start.class);
                     Global.categoryId=adapter.getRef(position).getKey();
                     startActivity(startGame);

                 }
             });
          }
      };
      adapter.notifyDataSetChanged();
      listCategory.setAdapter(adapter);
    }

}
