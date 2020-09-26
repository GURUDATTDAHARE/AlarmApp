package com.guru.timerapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

public class Mybroadcast extends BroadcastReceiver {
    MediaPlayer mp;
    @Override
    public void onReceive(Context context, Intent intent) {
        mp= MediaPlayer.create(context, R.raw.song);
       mp.start();
        Toast.makeText(context, "Alarm....", Toast.LENGTH_LONG).show();
      //  Log.d("yo","alarm ");
    }
}
