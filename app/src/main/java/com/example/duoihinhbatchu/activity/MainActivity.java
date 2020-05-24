package com.example.duoihinhbatchu.activity;

import android.content.Intent;
import android.content.SharedPreferences;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.duoihinhbatchu.PlayMusic;
import com.example.duoihinhbatchu.R;

public class MainActivity extends AppCompatActivity {

    private Button btnMusicPlay;
    private boolean music;
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

        music = sharedPreferences.getBoolean("music", true);

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
