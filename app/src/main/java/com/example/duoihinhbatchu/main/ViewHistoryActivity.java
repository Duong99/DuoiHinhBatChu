package com.example.duoihinhbatchu.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.duoihinhbatchu.R;
import com.example.duoihinhbatchu.adapter.HistoryAdapter;
import com.example.duoihinhbatchu.database.DBHistory;
import com.example.duoihinhbatchu.model.History;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class ViewHistoryActivity extends AppCompatActivity {

    private HistoryAdapter adapter;
    private ArrayList<History> histories;
    private DBHistory db;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_history);

        gridView = findViewById(R.id.gridViewHistory);
        db = new DBHistory(this);
        histories = new ArrayList<>();
        histories = db.getAllImage();

        if (histories.size() != 0){
            adapter = new HistoryAdapter(this, histories);
            gridView.setAdapter(adapter);
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ViewHistoryActivity.this, ViewPageHistoryActivity.class);
                startActivity(intent);
            }
        });
    }
}
