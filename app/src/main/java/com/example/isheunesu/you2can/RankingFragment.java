package com.example.isheunesu.you2can;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.isheunesu.you2can.Global.Global;
import com.example.isheunesu.you2can.Interface.ItemClickListener;
import com.example.isheunesu.you2can.Interface.RankingCallBack;
import com.example.isheunesu.you2can.ViewHolder.RankingViewHolder;
import com.example.isheunesu.you2can.model.QuestionScore;
import com.example.isheunesu.you2can.model.Ranking;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class RankingFragment extends Fragment {
    private static String TAG="";
    View myFragment;
    FirebaseDatabase database;
    DatabaseReference question_score,rankingTb1;
    RecyclerView rankingList;
    LinearLayoutManager layoutManager;
    FirebaseRecyclerAdapter<Ranking,RankingViewHolder>adapter;
    int sum=0;
    public static RankingFragment newInstance(){
        RankingFragment rankingFragment=new RankingFragment();
        return rankingFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database=FirebaseDatabase.getInstance();
        question_score=database.getReference("Question_Score");
        rankingTb1=database.getReference("Ranking");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment=inflater.inflate(R.layout.fragment_ranking,container,false) ;
        rankingList=(RecyclerView)myFragment.findViewById(R.id.rankingList);
        layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        rankingList.setLayoutManager(layoutManager);

        updateScore(Global.currentUser.getName(), new RankingCallBack<Ranking>() {
            @Override
            public void callBack(Ranking ranking) {
                rankingTb1.child(ranking.getUserName())
                        .setValue(ranking);

               // showRanking();
            }
        });

        adapter=new FirebaseRecyclerAdapter<Ranking, RankingViewHolder>(
                Ranking.class,
                R.layout.layout_ranking,
                RankingViewHolder.class,
                rankingTb1.orderByChild("score")

        ) {
            @Override
            protected void populateViewHolder(RankingViewHolder viewHolder, Ranking model, int position) {
             viewHolder.mTextName.setText(model.getUserName());
             viewHolder.mTextScore.setText(String.valueOf(model.getScore()));

             viewHolder.setItemClickListener(new ItemClickListener() {
                 @Override
                 public void onClick(View view, int position, boolean isLongClick) {

                 }
             });
            }
        };
        adapter.notifyDataSetChanged();
        rankingList.setAdapter(adapter);


        return  myFragment;
      //rankingList.setHasFixedSize(true);

    }



    private void updateScore(final String name, final RankingCallBack<Ranking> callBack) {
        question_score.orderByChild("User").equalTo(name).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot data:dataSnapshot.getChildren()){
                            QuestionScore ques=data.getValue(QuestionScore.class);
                            sum+=Integer.parseInt(ques.getScore());

                        }

                        Ranking ranking=new Ranking(name,sum);
                        callBack.callBack(ranking);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }


}
