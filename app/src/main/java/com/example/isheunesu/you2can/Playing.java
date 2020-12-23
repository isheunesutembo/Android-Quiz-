package com.example.isheunesu.you2can;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.isheunesu.you2can.Global.Global;


public class Playing extends AppCompatActivity implements View.OnClickListener {
   final static long INTERVAL=1000;
   final static long TIMEOUT=10000;
   int progressValue=0;
   CountDownTimer mCountDown;
   int index=0,score=0,thisQuestion=0,totalQuestion,correctAnswer;
   private ProgressBar mProgressbar;
   private Button btnA,btnB,btnC,btnD,mSubmit;
   private TextView txtScore,txtQuestionNumber,question_text;
   MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);
        txtScore=(TextView) findViewById(R.id.text_score);
        txtQuestionNumber=(TextView) findViewById(R.id.text_total_question);
        question_text=(TextView) findViewById(R.id.question_text);
        btnA=(Button)findViewById(R.id.answerA);
        btnB=(Button)findViewById(R.id.answerB);
        btnC=(Button)findViewById(R.id.answerC);
        btnD=(Button)findViewById(R.id.answerD);




        mProgressbar=(ProgressBar) findViewById(R.id.progressBar);

        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        mCountDown.cancel();
        if(index < totalQuestion){

            Button clickedButton=(Button)view;

            if(clickedButton.getText().equals(Global.questionList.get(index).getCorrectAnswer())){

                if(player==null){
                    player=MediaPlayer.create(this,R.raw.audience);

                }
                player.start();
                showQuestion(++index);
                score+=1;
                correctAnswer++;
            }else{

               showQuestion(++index);



            }
            txtScore.setText(String.valueOf("Score:"+score));

        }else{
            Intent intent=new Intent(Playing.this,Done.class);
            Bundle dataSend=new Bundle();
            dataSend.putInt("SCORE",score);
            dataSend.putInt("TOTAL",totalQuestion);
            dataSend.putInt("CORRECT",correctAnswer);
            intent.putExtras(dataSend);
            startActivity(intent);
            finish();
        }

    }

    private void showQuestion(int index) {
        if(index<totalQuestion){
            thisQuestion++;
            txtQuestionNumber.setText("Question "+thisQuestion+"/"+totalQuestion);

            question_text.setText(Global.questionList.get(index).getQuestion());
            btnA.setText(Global.questionList.get(index).getAnswerA());
            btnB.setText(Global.questionList.get(index).getAnswerB());
            btnC.setText(Global.questionList.get(index).getAnswerC());
            btnD.setText(Global.questionList.get(index).getAnswerD());
            mCountDown.start();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        totalQuestion=Global.questionList.size();
        mCountDown=new CountDownTimer(TIMEOUT,INTERVAL) {
            @Override
            public void onTick(long miniSec) {
                mProgressbar.setProgress(progressValue);
                progressValue++;

            }

            @Override
            public void onFinish() {

            }
        };
        showQuestion(++index);
    }
}
