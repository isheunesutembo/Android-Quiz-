package com.example.isheunesu.you2can.FirebaseCloudMessaging;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static com.example.isheunesu.you2can.Util.savePushToken;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    private static final String LOG_TAG="MyFirebaseInstanceId";

    @Override
    public void onTokenRefresh() {
        Log.d(LOG_TAG,"refreshed token");
        String refreshedToken=FirebaseInstanceId.getInstance().getToken();
        FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser==null||currentUser.isAnonymous()){
            return;
        }
        savePushToken(refreshedToken,currentUser.getUid());
        super.onTokenRefresh();
    }
}
