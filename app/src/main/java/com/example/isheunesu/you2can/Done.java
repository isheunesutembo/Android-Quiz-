package com.example.isheunesu.you2can;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.isheunesu.you2can.Global.Global;
import com.example.isheunesu.you2can.model.QuestionScore;
import com.example.isheunesu.you2can.model.User;
import com.facebook.common.Common;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Done extends Activity {
    private Button tryAgainBtn,reviseBtn;
    private TextView textTotalScore,textTotalQuestion;
    private ProgressBar doneProgressBar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference question_score;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);
        tryAgainBtn=(Button)findViewById(R.id.tryagain);
        reviseBtn=(Button) findViewById(R.id.revise);
        textTotalScore=(TextView)findViewById(R.id.txtTotalScore);
        textTotalQuestion=(TextView)findViewById(R.id.txtTotalQuestion);
        doneProgressBar=(ProgressBar)findViewById(R.id.doneProgressBar);
        mAuth=FirebaseAuth.getInstance();

        firebaseDatabase=FirebaseDatabase.getInstance();

        question_score=firebaseDatabase.getReference("Question_Score");
        question_score.keepSynced(true);


        tryAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Home.class));
            }
        });


        reviseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             startActivity(new Intent(getApplicationContext(),RevisionActivity.class));
            }
        });
        Bundle extras=getIntent().getExtras();
        if(extras!=null){
            int score=extras.getInt("SCORE");
            int totalQuestion=extras.getInt("TOTAL");
            int correctAnswer=extras.getInt("CORRECT");
            int total_score=500;
            textTotalScore.setText(String.valueOf("Score :"+score));
            textTotalQuestion.setText("Passed :"+correctAnswer+"/"+totalQuestion);
            doneProgressBar.setMax(totalQuestion);
            doneProgressBar.setProgress(correctAnswer);
            //Bug below

          /*  question_score.child(String.format(Global.currentUser.getName(),Global.categoryId)).push().
                    setValue(new QuestionScore(String.format(Global.currentUser.getName(),Global.categoryId),
                                    Global.currentUser.getName(),String.valueOf(score))
                            );
                    */
        }
    }
}
