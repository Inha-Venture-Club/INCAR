package ac.inhaventureclub.incar.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import ac.inhaventureclub.incar.R;
import ac.inhaventureclub.incar.activity.MainActivity;
import okhttp3.Dispatcher;

public class MyFireBaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    // 푸시 알림 설정
    private String title = "";
    private String body = "";

    // [ START receive_message ]
    @Override
    public void onNewToken(String token){
        Log.d("FCM Log", "Refreshed token: "+token);

        // If you want to send message to this application instance of
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        if(remoteMessage.getNotification() != null){
            Log.d("FCM Log", "알림 메시지: "+remoteMessage.getNotification().getBody());

            if(remoteMessage.getData().size()>0){
                Log.d(TAG,"Message data payload: "+ remoteMessage.getData());
                title = remoteMessage.getData().get("title");
                body = remoteMessage.getData().get("body");

                if(true){
                    scheduleJob();
                }else {
                    handleNow();
                }


            }

            // Notification 사용했을 때 data 가져오기
            if(remoteMessage.getNotification() != null){
                Log.d(TAG,"Message Notification Body: "+remoteMessage.getNotification().getIcon());
                Log.d(TAG,"Message Notification Body: "+remoteMessage.getNotification().getTitle());
                Log.d(TAG,"Message Notification Body: "+remoteMessage.getNotification().getBody());
            }
            sendNotification();

            // Also if you intend on generating your own notifications as a result of a received FCM
            // Message, here is where that should be initiated. See sendNotification method below.

//            String messageBody = remoteMessage.getNotification().getBody();
//            String messageTitle = remoteMessage.getNotification().getTitle();
//
//              어디서 이 push를 띄울거냐!
//            Intent intent = new Intent(this, MainActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
//            String channelId = "Channel ID";
//            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//            NotificationCompat.Builder notificationBuilder =
//                    new NotificationCompat.Builder(this,channelId)
//                    .setSmallIcon(R.drawable.incar_logo)
//                    .setContentTitle(messageTitle) // 채팅 보낸사람 정보
//                    .setContentText(messageBody) // 받은 메시지
//                    .setAutoCancel(true)
//                    .setSound(defaultSoundUri)
//                    .setContentIntent(pendingIntent);
//            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//                String channelName = "Channel Name";
//                NotificationChannel channel = new NotificationChannel(channelId, channelName,NotificationManager.IMPORTANCE_HIGH);
//                notificationManager.createNotificationChannel(channel);
//            }
//            notificationManager.notify(0,notificationBuilder.build());
        }
    }

    private void scheduleJob(){
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        Job myJob = dispatcher.newJobBuilder()
                .setService(MyJobService.class)
                .setTag("my-job-tag")
                .build();
        dispatcher.schedule(myJob);
    }

    private void handleNow(){
        Log.d(TAG,"Short lived task is done.");
    }

    private void sendRegistrationToServer(String token){
        // TODO: Implement this method to send token to your app server.
    }

    private void sendNotification(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        String channelId = "Channel ID";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this,channelId)
                .setSmallIcon(R.drawable.icon_incar_logo_with_full_background_color)
                .setContentTitle(title) // 채팅 보낸사람 정보
                .setContentText(body) // 받은 메시지
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setPriority(Notification.PRIORITY_HIGH);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo norification channel is needed.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readalbe title",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0,notificationBuilder.build());

    }
}
