package com.example.isheunesu.you2can;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.isheunesu.you2can.model.ConnectivityDetector;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class SignIn extends AppCompatActivity  {
    private EditText mEmail,mPassword;
    private Button mSignIn;
    private ProgressBar mProgressBar;
    private FirebaseAuth mAuth;
    private static final String TAG="";
    private FirebaseAuth.AuthStateListener mAuthListener;
    ConnectivityDetector cd;
    Toolbar toolbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mEmail=(EditText)findViewById(R.id.email);
        mPassword=(EditText)findViewById(R.id.password);
        mSignIn=(Button)findViewById(R.id.signIn);
        mProgressBar=(ProgressBar)findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.INVISIBLE);
        mAuth=FirebaseAuth.getInstance();
        setUpFireBase();
        cd=new ConnectivityDetector(this);
        toolbar=(Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Sign In");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);




        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEmpty(mEmail.getText().toString())&&!isEmpty(mPassword.getText().toString())){
                    Log.d(TAG,"onClick:Attempting to authenticate");
                    showProgressBar();
                    mAuth.signInWithEmailAndPassword(mEmail.getText().toString(),mPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            hideProgressBar();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignIn.this, "Authentication Failed!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }
        });




    }






    private void showProgressBar(){
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        if(mProgressBar.getVisibility()==View.VISIBLE){
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    private boolean isEmpty(String string){
        return string.equals("");
    }

    private void setUpFireBase(){
    Log.d(TAG,"Setup firebase started");

    mAuthListener=new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = mAuth.getCurrentUser();
            if(user!=null){
                if(user.isEmailVerified()){
                    Log.d(TAG, "onAuthStateChanged: signed_in: " + user.getUid());
                    Toast.makeText(SignIn.this, "Authenticated with: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                    if(cd.isConnected()){
                        startActivity(new Intent(getApplicationContext(),Home.class));
                    }else{
                        Toast.makeText(SignIn.this, "Please connect your device on the internet", Toast.LENGTH_LONG).show();
                    }



                }else {
                    Toast.makeText(SignIn.this, "Email is not verified \nCheck your inbox!", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                }
            }else{
                Log.d(TAG,"User is signed out");
            }
        }
    };
    }





    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
