package com.example.isheunesu.you2can;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.isheunesu.you2can.Global.Global;
import com.example.isheunesu.you2can.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private EditText mUsername,mEmail,mPassword,mConfirmPassword;
    private Button mRegister;
    private ProgressBar mProgressBar;
    private String email,password,username;
    private static final String TAG="";
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference userReferences;
    private User mUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    android.support.v7.widget.Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mUsername=(EditText)findViewById(R.id.username);
        mEmail=(EditText)findViewById(R.id.email);
        mPassword=(EditText)findViewById(R.id.password);
        mConfirmPassword=(EditText)findViewById(R.id.confirm_password);
        mRegister=(Button)findViewById(R.id.register);
        mProgressBar=(ProgressBar)findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.INVISIBLE);
        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();

        toolbar=(android.support.v7.widget.Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setUpFireBase();


        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=mEmail.getText().toString();
                username=mUsername.getText().toString();
                password=mPassword.getText().toString();
                if(checkInputs(email,username,password,mConfirmPassword.getText().toString())){
                   if(doMatch(password,mConfirmPassword.getText().toString())) {
                       registerNewEmail();
                   }else{
                       Toast.makeText(Register.this, "Passwords do not match1", Toast.LENGTH_SHORT).show();
                   }
                }else {
                    Toast.makeText(Register.this, "All fields must be filled1", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkInputs(String email,String username,String password,String confirmpassword){
        Log.d(TAG,"checking for null inputs");
       if(email.equals("")||username.equals("")||password.equals("")||confirmpassword.equals("")){
           Toast.makeText(this, "All fields must be filled!", Toast.LENGTH_SHORT).show();
           return false;
       }
       return true;
    }

    private boolean doMatch(String s1,String s2){
        return s1.equals(s2);
    }

    private void registerNewEmail(){
        showProgressBar();
        mAuth.createUserWithEmailAndPassword(email,password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    sendVerificationEmail();
                    addNewUser();
                }else{
                    Toast.makeText(Register.this, "Someone with that email already exists", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    private void addNewUser(){
        String userId=FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d(TAG, "addNewUser: Adding new User: \n user_id:" + userId);
        String user_name=mUsername.getText().toString();
        mUser=new User();

        mUser.setName(mUsername.getText().toString());
        mUser.setUser_id(userId);
        userReferences=firebaseDatabase.getReference("Users");
        Global.currentUser=mUser;

        userReferences.child("Users")
                .child(userId)
                .setValue(mUser);
        FirebaseAuth.getInstance().signOut();
        redirectToLogIn();
    }

    private void sendVerificationEmail(){
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Register.this, "Verifivation sent to"+email, Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Register.this, "could'nt sent email", Toast.LENGTH_SHORT).show();
                        hideProgressBar();
                    }
                }
            });
        }
    }
    private void redirectToLogIn(){
        startActivity(new Intent(getApplicationContext(),SignIn.class));
    }
    private void setUpFireBase(){
    mAuthListener=new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            final FirebaseUser user=mAuth.getCurrentUser();
            if(user!=null){
                Log.d(TAG, "onAuthStateChanged: signed_in: " + user.getUid());
            }else{
                Log.d(TAG, "onAuthStateChanged: signed_out");
            }
        }
    };
    }



    private void showProgressBar(){
        mProgressBar.setVisibility(View.VISIBLE);
    }
    private void hideProgressBar(){
        mProgressBar.setVisibility(View.GONE);
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
}
