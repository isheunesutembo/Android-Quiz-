package com.example.isheunesu.you2can.FirebaseCloudMessaging;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.isheunesu.you2can.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import okhttp3.OkHttpClient;

import static com.example.isheunesu.you2can.Util.getCurrentUserId;

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG="My  Receiver";
    @Override
    public void onReceive(Context context, final Intent intent) {
        Log.d(TAG,"");
        FirebaseDatabase.getInstance().getReference("Users")
                .child(getCurrentUserId())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user=dataSnapshot.getValue(User.class);
                        OkHttpClient okHttpClient=new OkHttpClient();
                        String to=intent.getExtras().getString("to");
                        String withId=intent.getExtras().getString("withId");


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }
}
