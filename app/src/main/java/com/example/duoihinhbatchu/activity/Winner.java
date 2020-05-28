package com.example.duoihinhbatchu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.duoihinhbatchu.R;
import com.example.duoihinhbatchu.database.MyDatabase;

public class Winner extends AppCompatActivity {

    private Button btnPlayAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_winner);

        btnPlayAgain = findViewById(R.id.btnPlayAgain);
        MyDatabase myDatabase = new MyDatabase(this);
        myDatabase.updateQuestionPlayAgain();

        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Winner.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Winner.this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
