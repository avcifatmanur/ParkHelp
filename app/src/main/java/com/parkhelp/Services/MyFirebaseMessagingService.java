package com.parkhelp.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.R;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.parkhelp.Activitys.MainActivity;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    public MyFirebaseMessagingService() {
        super();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getData().size() > 0) { // Data mesajı içeriyor mu
            //Uygulama arkaplanda veya ön planda olması farketmez. Her zaman çağırılacaktır.
            //Gelen içerik json formatındadır.
            Log.d(TAG, "Mesaj data içeriği: " + remoteMessage.getData());
            //Json formatındaki datayı parse edip kullanabiliriz. Biz direk datayı Push Notification olarak bastırıyoruz.
            sendNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
        }

        if (remoteMessage.getNotification() != null) { //Notification mesajı içeriyor mu
            //Uygulama arkaplanda ise burası çağrılmaz.Ön planda ise notification mesajı geldiğinde çağırılır.
            //getBody() ile mesaj içeriği
            //getTitle() ile mesaj başlığı
            Log.d(TAG, "Mesaj Notification Başlığı: " + remoteMessage.getNotification().getTitle() +" "+"Mesaj Notification İçeriği: " + remoteMessage.getNotification().getBody() );
            //Gelen içeriğe göre notifikasyon bildiriminde bulunma:
            sendNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
        }

    }

    private void sendNotification(String messageTitle,String messageBody) {
        String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        long[] pattern = {0, 400, 800, 600, 800, 800, 800, 1000}; //Titreşim örüntüsü

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.stat_notify_error)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setVibrate(pattern)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        try {
            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + this.getPackageName() + "/raw/notification");
            Ringtone r = RingtoneManager.getRingtone(this, alarmSound);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}