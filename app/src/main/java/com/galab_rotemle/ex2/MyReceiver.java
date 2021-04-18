package com.galab_rotemle.ex2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class MyReceiver extends BroadcastReceiver
{
    private int batteryPercent, notificationId;
    private boolean isCharge,isSend;
    private Context context;
    private String channelId;
    private NotificationManager notificationManager;

    public MyReceiver(Context context, String channelId, int notificationID, NotificationManager notificationManager) {
        this.context = context;
        this.channelId = channelId;
        this.notificationManager = notificationManager;
        this.notificationId = notificationID;
        this.isSend = true;
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        String action = intent.getAction();

        if(action.equals(Intent.ACTION_BATTERY_CHANGED)){
            this.batteryPercent = intent.getIntExtra("level",0);

            if(BatteryManager.BATTERY_STATUS_CHARGING == intent.getIntExtra(BatteryManager.EXTRA_STATUS,-1)){
                this.isCharge = true;
            }else{
                this.isCharge = false;
            }

            if(this.batteryPercent <=10 && !this.isCharge && this.isSend){
                Intent intent1 = new Intent(this.context,MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this.context,0,intent1,0);

                Notification notification = new NotificationCompat.Builder(this.context,this.channelId)
                        .setContentTitle("Low Battery").setContentText("Battery will run out soon.")
                        .setContentIntent(pendingIntent).build();
                this.notificationManager.notify(this.notificationId,notification);
                this.isSend =  false;
            }
            if (this.batteryPercent >10 && !this.isSend)
                this.isSend = true;
        }
    }
}