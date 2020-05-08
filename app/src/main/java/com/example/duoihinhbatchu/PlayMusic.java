package com.example.duoihinhbatchu;

import android.content.Context;
import android.media.MediaPlayer;

public class PlayMusic {
    public PlayMusic(Context context, int idMusic){
        MediaPlayer mediaPlayer = MediaPlayer.create(context, idMusic);
        mediaPlayer.start();
    }
}
