package com.example.duoihinhbatchu.main;

import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.duoihinhbatchu.PlayMusic;
import com.example.duoihinhbatchu.R;
import com.example.duoihinhbatchu.Screenshot;
import com.example.duoihinhbatchu.database.DBHistory;
import com.example.duoihinhbatchu.database.MyDatabase;
import com.example.duoihinhbatchu.model.History;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button btnMusicPlay;
    private boolean music = false;
    private SharedPreferences sharedPreferences;
    private ImageView imv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        btnMusicPlay = findViewById(R.id.btnMusicPlay);
        imv = findViewById(R.id.imv);

        sharedPreferences = getSharedPreferences("MuiscManDiem", MODE_PRIVATE);

        music = sharedPreferences.getBoolean("music", false);

        if (music){
            btnMusicPlay.setText("Tắt Nhạc");
            new PlayMusic(this, R.raw.batdau);
        }else {
            btnMusicPlay.setText("Bật Nhạc");
        }
    }

    public void onclickPlay(View view) {
        Intent intent = new Intent(MainActivity.this, MainPlayGameActivity.class);
        startActivity(intent);
    }

    public void onClickMusicPlay(View view) {
        if(btnMusicPlay.getText().equals("Tắt Nhạc")){
            btnMusicPlay.setText("Bật Nhạc");
            music = false;
            luuOnOffMussic();
        }else {
            btnMusicPlay.setText("Tắt Nhạc");
            music = true;
            new PlayMusic(this, R.raw.batdau);
            luuOnOffMussic();
        }
    }

    public void onClickViewHistory(View view){
        Intent intent = new Intent(MainActivity.this, ViewHistoryActivity.class);
        startActivity(intent);
    }

    private void luuOnOffMussic(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("music", music);
        editor.commit();
    }
}
