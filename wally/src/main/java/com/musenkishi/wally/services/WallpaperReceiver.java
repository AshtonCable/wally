package com.musenkishi.wally.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.musenkishi.wally.dataprovider.PeriodicRandomImage;

/**
 * Created by Maher on 10/6/2017.
 * This class handles the behavior when the alarm is triggered
 */

public class WallpaperReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PeriodicRandomImage.setRandomWallpaper(context);
    }
}
