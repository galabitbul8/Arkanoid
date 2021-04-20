package com.galab_rotemle.ex2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "channel_main";
    private static final CharSequence CHANNEL_NAME = "Main Channel";
    private NotificationManager notificationManager;
    private int notificationID;

    private BroadcastReceiver batteryReceiver;
    private IntentFilter filter;
    MediaPlayer mp;
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        notificationsSetup();
        broadcastSetup();
        gameView = (GameView)findViewById(R.id.game);
        mp = MediaPlayer.create(this, R.raw.hit_sound2);
        gameView.setSound(mp);
    }


    private void notificationsSetup()
    {
        // 1. Get reference Notification Manager system Service
        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        // 2. Create Notification-Channel. ONLY for Android 8.0 (OREO API level 26) and higher.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID, 	// Constant for Channel ID
                    CHANNEL_NAME, 	// Constant for Channel NAME
                    NotificationManager.IMPORTANCE_HIGH);  // for popup use: IMPORTANCE_HIGH
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationID = 1;
    }

    private void broadcastSetup()
    {
        // 1. Create a new Class that extends Broadcast Receiver
        // 2. Create BatteryReceiver object
        batteryReceiver = new MyReceiver(this,CHANNEL_ID,this.notificationID, this.notificationManager);

        // 3. Create IntentFilter for BATTERY_CHANGED & AIRPLANE_MODE_CHANGED broadcast
        filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
    }


    @Override
    protected void onStart()
    {
        super.onStart();
        // 4. Register the receiver to start listening for battery change messages
        registerReceiver(batteryReceiver, filter);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        // 5. Un-Register the receiver to stop listening for battery change messages
        unregisterReceiver(batteryReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.setIsRun(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.setIsRun(false);
    }
}