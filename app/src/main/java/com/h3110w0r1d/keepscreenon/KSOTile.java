package com.h3110w0r1d.keepscreenon;
import android.os.PowerManager;
import android.annotation.SuppressLint;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.util.Log;

public class KSOTile extends TileService {
    static int num = 0;
    private PowerManager.WakeLock mWakeLock = null;
    private PowerManager mPM;

    @SuppressLint("WrongConstant")
    public void onCreate() {
        this.mPM = (PowerManager)getSystemService("power");
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
        {
            if (this.mWakeLock != null && this.mWakeLock.isHeld())
                this.mWakeLock.release();
            this.mWakeLock = this.mPM.newWakeLock(268435466, "KSOService");
            this.mWakeLock.setReferenceCounted(false);
            this.mWakeLock.acquire();
            num = 1;
        }
        else
        {
            if (this.mWakeLock != null)
                this.mWakeLock.release();
            num = 0;
        }
        boolean enabled = num % 2 == 1;
        Log.d("TEST","Run onClick function with enabled = " + enabled);
        getQsTile().setState(enabled ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
        getQsTile().updateTile();
    }
}
