package com.example.duoihinhbatchu.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.duoihinhbatchu.R;

public class MyService extends Service {
    private MediaPlayer mediaPlayer;

    public MyService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
         return null;
    }


    public void onCreate() {
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sai);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.dung);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.batdau);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.chienthang);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        mediaPlayer.release();
        super.onDestroy();
    }
}
