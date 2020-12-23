package com.example.isheunesu.you2can;

import android.app.Fragment;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.isheunesu.you2can.ViewHolder.RevisionViewHolder;
import com.example.isheunesu.you2can.model.Question;
import com.example.isheunesu.you2can.model.Revision;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RevisionList extends android.support.v4.app.Fragment {

    FirebaseRecyclerAdapter<Revision,RevisionViewHolder> adapter;
    private FirebaseDatabase database;
    private DatabaseReference mQuestionRef;
    RecyclerView revisionRecyclerView;
    View revisionFragment;
    Revision revision=new Revision();
    private RecyclerView.LayoutManager layoutManager;
    private List<Revision> revisionList=new ArrayList<>();


    public static RevisionList newInstance(){
        RevisionList revisionList=new RevisionList();
        return revisionList;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database=FirebaseDatabase.getInstance();
        mQuestionRef=database.getReference("EnglishQuestions");


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        revisionFragment=inflater.inflate(R.layout.activity_revision_list,container,false);
        revisionRecyclerView=revisionFragment.findViewById(R.id.revision_list);
        revisionRecyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getActivity());
        revisionRecyclerView.setLayoutManager(layoutManager);
        loadRevisionList();

        return revisionFragment;


    }

    private void loadRevisionList() {
      adapter=new FirebaseRecyclerAdapter<Revision, RevisionViewHolder>(
              Revision.class,
              R.layout.revision_list,
              RevisionViewHolder.class,
              mQuestionRef

      ) {
          @Override
          protected void populateViewHolder(RevisionViewHolder viewHolder, Revision model, int position) {
              viewHolder.mRevisionQuestion.setText(model.getCorrectAnswer());
              viewHolder.mRevisionAnswer.setText(model.getCorrectAnswer());

          }
      };

        adapter.notifyDataSetChanged();
        revisionRecyclerView.setAdapter(adapter);

    }
}
