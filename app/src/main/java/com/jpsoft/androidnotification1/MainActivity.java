package com.jpsoft.androidnotification1;

import static com.jpsoft.androidnotification1.MyApp.CHANNEL_ID_1;
import static com.jpsoft.androidnotification1.MyApp.CHANNEL_ID_2;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.jpsoft.androidnotification1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    NotificationManagerCompat notificationManager;
    MediaSessionCompat mediaSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        notificationManager = NotificationManagerCompat.from(this);
        mediaSession = new MediaSessionCompat(this, "MainActivity");

        binding.channel1Button.setOnClickListener(v -> channel_1_notification());

        binding.channel2Button.setOnClickListener(v -> channel_2_notification());
    }

    // Channel 1 Notification
    private void channel_1_notification() {
        String title = binding.editTextTextTitle.getText().toString();
        String content = binding.editTextTextDescription.getText().toString();
        Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getApplicationContext().getPackageName() + "/" + R.raw.message_notification);

        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, activityIntent, 0);

        Intent broadcastIntent = new Intent(this, NotificationReceiver.class);
        broadcastIntent.putExtra("toastMessage", content);

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.dog);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID_1)
                .setSmallIcon(R.drawable.ic_baseline_looks_one_24)
                .setContentTitle(title)
                .setContentText(content)
                .setLargeIcon(largeIcon)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(largeIcon)
                        .bigLargeIcon(null))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true) // When tap this notification will removed
                .setOnlyAlertOnce(true) // Alert just in first time
                .build();
        notificationManager.notify(1, notification);
    }

    // Channel 2 Notification
    private void channel_2_notification() {
        String title = binding.editTextTextTitle.getText().toString();
        String content = binding.editTextTextDescription.getText().toString();

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.dog);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID_2)
                .setSmallIcon(R.drawable.ic_baseline_looks_two_24)
                .setContentTitle(title)
                .setContentText(content)
                .setLargeIcon(largeIcon)
                .addAction(R.drawable.ic_baseline_unfold_less_24, "Unfold Less", null)
                .addAction(R.drawable.ic_baseline_skip_previous_24, "Previous", null)
                .addAction(R.drawable.ic_baseline_pause_24, "Pause", null)
                .addAction(R.drawable.ic_baseline_skip_next_24, "Next", null)
                .addAction(R.drawable.ic_baseline_unfold_more_24, "Unfold More", null)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(1, 2, 3)
                        .setMediaSession(mediaSession.getSessionToken())
                )
                .setSubText("Sub text")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();
        notificationManager.notify(2, notification);
    }
}