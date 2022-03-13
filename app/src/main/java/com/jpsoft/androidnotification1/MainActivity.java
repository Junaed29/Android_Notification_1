package com.jpsoft.androidnotification1;

import static com.jpsoft.androidnotification1.MyApp.CHANNEL_ID_1;
import static com.jpsoft.androidnotification1.MyApp.CHANNEL_ID_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.jpsoft.androidnotification1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        notificationManager = NotificationManagerCompat.from(this);

        binding.channel1Button.setOnClickListener(v -> channel_1_notification());

        binding.channel2Button.setOnClickListener(v -> channel_2_notification());
    }

    // Channel 1 Notification
    private void channel_1_notification(){
        String title = binding.editTextTextTitle.getText().toString();
        String content = binding.editTextTextDescription.getText().toString();
        Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"+ getApplicationContext().getPackageName() + "/" + R.raw.message_notification);

        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, activityIntent, 0);

        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID_1)
                .setSmallIcon(R.drawable.ic_baseline_looks_one_24)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(contentIntent)
                .build();
        notificationManager.notify(1, notification);
    }

    // Channel 2 Notification
    private void channel_2_notification(){
        String title = binding.editTextTextTitle.getText().toString();
        String content = binding.editTextTextDescription.getText().toString();

        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID_2)
                .setSmallIcon(R.drawable.ic_baseline_looks_two_24)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();
        notificationManager.notify(2, notification);
    }
}