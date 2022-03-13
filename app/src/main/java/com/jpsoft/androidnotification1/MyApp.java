package com.jpsoft.androidnotification1;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;

public class MyApp extends Application {
    public static final String CHANNEL_ID_1  = "channel1";
    public static final String CHANNEL_ID_2  = "channel2";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
    }

    private void createNotificationChannel() {
        Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"+ getApplicationContext().getPackageName() + "/" + R.raw.message_notification);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ){

            //Channel 1
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            NotificationChannel channel1 = new NotificationChannel(CHANNEL_ID_1, "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
                    );
            channel1.setDescription("This is channel 1");
            channel1.enableLights(true);
            channel1.enableVibration(true);
            channel1.setSound(soundUri, audioAttributes);


            //Channel 2
            NotificationChannel channel2 = new NotificationChannel(CHANNEL_ID_2, "Channel 2",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel1.setDescription("This is channel 2");


            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }
    }
}
