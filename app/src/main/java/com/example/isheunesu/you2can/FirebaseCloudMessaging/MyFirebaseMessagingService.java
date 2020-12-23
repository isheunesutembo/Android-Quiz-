package com.example.isheunesu.you2can.FirebaseCloudMessaging;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.isheunesu.you2can.Playing;
import com.example.isheunesu.you2can.R;
import com.example.isheunesu.you2can.Start;
import com.example.isheunesu.you2can.Util;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static android.support.v4.app.NotificationCompat.PRIORITY_MAX;
import static com.example.isheunesu.you2can.Util.getCurrentUserId;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String LOG_TAG="FirebaseMessaging";
    public static final String INVITE = "invite";
    FirebaseUser currentUser;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        currentUser=FirebaseAuth.getInstance().getCurrentUser();

        String fromPushId=remoteMessage.getData().get("fromPushId");
        String fromId=remoteMessage.getData().get("fromId");
        String fromName=remoteMessage.getData().get("fromName");
        String type=remoteMessage.getData().get("type");
        Log.d(LOG_TAG, "MessageReceived: ");

        if(type.equals("invite")){
            handleInviteIntent(fromPushId,fromId,fromName);
        }else if(type.equals("accept")){
            startActivity(new Intent(getBaseContext(), Playing.class)
                    .putExtra("type", "wifi")
                    .putExtra("me", "x")
                    .putExtra("gameId", getCurrentUserId() + "-" + fromId)
                    .putExtra("withId", fromId));

        }else if(type.equals("reject")){
            // todo update to Oreo notifications
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setPriority(PRIORITY_MAX)
                            .setContentTitle(String.format("%s rejected your invite!", fromName));

            Intent resultIntent = new Intent(this, Playing.class)
                    .putExtra("type", "wifi");
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(Start.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            mBuilder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(1, mBuilder.build());

        }

        super.onMessageReceived(remoteMessage);
    }

    private void handleInviteIntent(String fromPushId, String fromId, String fromName) {
        Intent rejectIntent=new Intent(getApplicationContext(),MyReceiver.class)
                .setAction("reject")
                .putExtra("withId",fromId)
                .putExtra("to",fromPushId);

        PendingIntent pendingIntentReject=PendingIntent.getBroadcast(this,0,rejectIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        String gameId=fromId +""+getCurrentUserId();
        FirebaseDatabase.getInstance().getReference().child("games")
                .child(gameId)
                .setValue(null);

        Intent acceptIntent = new Intent(getApplicationContext(), MyReceiver.class)
                .setAction("accept")
                .putExtra("withId", fromId)
                .putExtra("to", fromPushId);

        PendingIntent pendingIntentAccept = PendingIntent.getBroadcast(this, 2, acceptIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Intent resultIntent = new Intent(this, Playing.class)
                .putExtra("type", "wifi")
                .putExtra("withId", fromId)
                .putExtra("to", fromPushId);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(Start.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        android.app.Notification build = new NotificationCompat.Builder(this, INVITE)
                .setSmallIcon(R.drawable.icon)
                .setPriority(PRIORITY_MAX)
                .setContentTitle(String.format("%s invites you to play!", fromName))
                .addAction(R.drawable.accept, "Accept", pendingIntentAccept)
                .setVibrate(new long[3000])
                .setChannelId(INVITE)
                .setContentIntent(resultPendingIntent)
                .addAction(R.drawable.canscel, "Reject", pendingIntentReject)
                .build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager == null) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(INVITE, INVITE, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        notificationManager.notify(1, build);

    }

}
