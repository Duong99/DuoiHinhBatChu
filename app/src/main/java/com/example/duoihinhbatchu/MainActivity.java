package com.example.duoihinhbatchu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.duoihinhbatchu.database.MyDatabase;
import com.example.duoihinhbatchu.service.MyService;

import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private Button btnMusicPlay;
    private int music = -1;
    private SharedPreferences sharedPreferences;
    private MyDatabase myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        myDatabase = new MyDatabase(this);


        btnMusicPlay = (Button) findViewById(R.id.btnMusicPlay);

        sharedPreferences = getSharedPreferences("onoffmusic", MODE_PRIVATE);
        music = sharedPreferences.getInt("music", 1);

        Intent intent = new Intent(MainActivity.this, MyService.class);

        if(music == 1){
            btnMusicPlay.setText("Tắt Nhạc");
            startService(intent);
        }
        if(music == 0){
            btnMusicPlay.setText("Bật Nhạc");
            stopService(intent);
        }
    }

    public void onclickPlay(View view) {
        Intent intent = new Intent(MainActivity.this, MainPlayGameActivity.class);
        startActivity(intent);

    }

    public void onClickMusicPlay(View view) {
        if(btnMusicPlay.getText().equals("Tắt Nhạc")){
            btnMusicPlay.setText("Bật Nhạc");
            music = 0;
            luuOnOffMussic();
        }else {
            btnMusicPlay.setText("Tắt Nhạc");
            music = 1;
            luuOnOffMussic();
        }
    }

    private void luuOnOffMussic(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("music", music);
        editor.commit();
    }
}
