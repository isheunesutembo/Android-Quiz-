package com.example.isheunesu.you2can;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Util {
    public static void savePushToken(String refreshedToken ,String userId){
        FirebaseDatabase.getInstance().getReference("Users")
                .child(userId)
                .child("pushId")
                .setValue(refreshedToken);
    }
    public static String getCurrentUserId(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null || currentUser.isAnonymous()) {
            return "";
        } else {
            return currentUser.getUid();
        }
    }
}
