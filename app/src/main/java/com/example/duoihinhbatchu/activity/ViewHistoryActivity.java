package com.example.duoihinhbatchu.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.duoihinhbatchu.R;
import com.example.duoihinhbatchu.adapter.HistoryAdapter;
import com.example.duoihinhbatchu.database.DBHistory;
import com.example.duoihinhbatchu.model.History;

import java.util.ArrayList;

public class ViewHistoryActivity extends AppCompatActivity {

    private HistoryAdapter adapter;
    private ArrayList<History> histories;
    private DBHistory db;
    private GridView gridView;
    private int mPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_view_history);


        gridView = findViewById(R.id.gridViewHistory);
        registerForContextMenu(gridView);
        db = new DBHistory(this);
        histories = new ArrayList<>();
        histories = db.getAllImage();

        if (histories.size() != 0) {
            adapter = new HistoryAdapter(this, histories);
            gridView.setAdapter(adapter);
        }

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int position, long l) {
                mPosition = position;
                return false;
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ViewHistoryActivity.this, ViewPageHistoryActivity.class);
                intent.putExtra("POSITION", i);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.item_history, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuDelete:
                final AlertDialog.Builder builder = new AlertDialog.Builder(ViewHistoryActivity.this);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có muốn xóa ảnh này không");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.deleteImageHistory(histories.get(mPosition).getId());
                        adapter.removePositionData(mPosition);
                        adapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

                builder.show();

        }
        return super.onContextItemSelected(item);
    }
}
