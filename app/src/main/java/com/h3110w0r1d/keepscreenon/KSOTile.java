package com.h3110w0r1d.keepscreenon;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.annotation.SuppressLint;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

public class KSOTile extends TileService {
    static int num = 0;
    private PowerManager.WakeLock mWakeLock = null;
    private PowerManager mPM;

    private NotificationManager mNM;

    @SuppressLint("WrongConstant")
    public void onCreate() {
        this.mPM = (PowerManager)getSystemService("power");
        mNM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
    }

    @Override
    public void onStopListening() {
        super.onStopListening();
    }

    @SuppressLint("InvalidWakeLockTag")
    @Override
    public void onClick() {
        super.onClick();
        if(num == 0)
        {//开启
            if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
                NotificationChannel notificationChannel =
                        new NotificationChannel("channelid1","channelname",NotificationManager.IMPORTANCE_HIGH);
                //如果这里用IMPORTANCE_NOENE就需要在系统的设置里面开启渠道，通知才能正常弹出
                mNM.createNotificationChannel(notificationChannel);
            }
            NotificationCompat.Builder builder = new NotificationCompat.Builder(KSOTile.this,"channelid1");
            builder.setSmallIcon(R.drawable.ic_tile)
                    .setContentTitle("已开启屏幕常亮，不要忘记关闭哦！")
                    .setAutoCancel(false);
            Notification notification = builder.build();
            notification.flags = Notification.FLAG_NO_CLEAR;
            mNM.notify(1,notification);

            if (this.mWakeLock != null && this.mWakeLock.isHeld())
                this.mWakeLock.release();
            this.mWakeLock = this.mPM.newWakeLock(268435466, "KSOService");
            this.mWakeLock.setReferenceCounted(false);
            this.mWakeLock.acquire();
            num = 1;
        }
        else
        {//关闭
            if (this.mWakeLock != null)
                this.mWakeLock.release();
            mNM.cancel(1);
            num = 0;
        }
        boolean enabled = num % 2 == 1;
        Log.d("TEST","Run onClick function with enabled = " + enabled);
        getQsTile().setState(enabled ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
        getQsTile().updateTile();
    }
}


