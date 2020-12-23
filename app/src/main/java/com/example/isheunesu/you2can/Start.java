package com.example.isheunesu.you2can;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.isheunesu.you2can.Global.Global;
import com.example.isheunesu.you2can.model.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;

public class Start extends AppCompatActivity {
    private Button mPlayBtn,mMutiPlayer;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference questions;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mPlayBtn=(Button)findViewById(R.id.play_button);
        toolbar=(Toolbar) findViewById(R.id.toolBar);
        mMutiPlayer=(Button) findViewById(R.id.multi_player);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Start");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        firebaseDatabase=FirebaseDatabase.getInstance();


        questions=firebaseDatabase.getReference("EnglishQuestions");
        loadQuestions(Global.categoryId);

        mPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Playing.class));

            }
        });
        mMutiPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });


    }

    private void loadQuestions(String categoryId) {
        if(Global.questionList.size()>0)
            Global.questionList.clear();
        questions.orderByChild("CategoryId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                         for(DataSnapshot postSnapShot:dataSnapshot.getChildren()){
                             Question ques=postSnapShot.getValue(Question.class);
                             Global.questionList.add(ques);
                         }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        Collections.shuffle(Global.questionList);
    }
}
