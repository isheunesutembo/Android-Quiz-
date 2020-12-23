package com.example.isheunesu.you2can.model;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityDetector {
    Context context;

    public ConnectivityDetector(Context context) {
        this.context=context;
    }

    public boolean isConnected(){
        ConnectivityManager connectivity=(ConnectivityManager)context.getSystemService(Service.CONNECTIVITY_SERVICE);

        if(connectivity!=null){
            NetworkInfo networkInfo=connectivity.getActiveNetworkInfo();
            if(networkInfo!=null){
                if(networkInfo.getState()==NetworkInfo.State.CONNECTED){
                    return true;
                }
            }
        }



    return false;
    }
}
