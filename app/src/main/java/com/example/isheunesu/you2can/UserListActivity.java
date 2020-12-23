package com.example.isheunesu.you2can;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.example.isheunesu.you2can.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {
 
  private List<User>users=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

    }
}
