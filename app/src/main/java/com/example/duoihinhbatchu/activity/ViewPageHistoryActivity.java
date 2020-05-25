package com.example.duoihinhbatchu.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.duoihinhbatchu.R;
import com.example.duoihinhbatchu.adapter.ViewPagerApdater;
import com.example.duoihinhbatchu.database.DBHistory;
import com.example.duoihinhbatchu.model.History;

import java.util.ArrayList;

public class ViewPageHistoryActivity extends AppCompatActivity {
    private ViewPager viewPagerHistory;
    private ArrayList<History> histories;
    private ViewPagerApdater adapter;
    private DBHistory dbHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_view_page_history);

        viewPagerHistory = findViewById(R.id.viewPagerHistory);
        dbHistory = new DBHistory(this);
        histories = new ArrayList<>();
        histories = dbHistory.getAllImage();
        adapter = new ViewPagerApdater(histories, this);
        viewPagerHistory.setAdapter(adapter);

        Intent intent = getIntent();
        int position = intent.getIntExtra("POSITION", -1);
        viewPagerHistory.setCurrentItem(position);

    }
}
