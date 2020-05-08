package com.example.duoihinhbatchu.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.duoihinhbatchu.model.History;

import java.util.ArrayList;
import java.util.List;

public class DBHistory extends SQLiteOpenHelper {
    private final static String NAMEDB = "History.db";

    private final static String NAMETABLE = "History";
    private final static String id = "id";
    private final static String time = "time";
    private final static String image = "image";

    private SQLiteDatabase db;


    public DBHistory(Context context) {
        super(context, NAMEDB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS " +
                NAMETABLE + " ( " + id + " TEXT PRIMARY KEY, " + time + " TEXT, " + image + " BLOB )";
        sqLiteDatabase.execSQL(sql);
    }

    public void addImageHistory(History h){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(id, h.getId());
        values.put(time, h.getTime());
        values.put(image, h.getImage());
        db.insert(NAMETABLE, null, values);
        db.close();
    }

    public ArrayList<History> getAllImage(){
        db = this.getReadableDatabase();
        ArrayList<History> histories = new ArrayList<>();
        String select = "SELECT * FROM " + NAMETABLE;
        Cursor cursor = db.rawQuery(select, null);
        if (cursor.moveToFirst()){
            do {
                History h = new History(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getBlob(2)
                );
                histories.add(h);

            }while (cursor.moveToNext());
        }
        db.close();
        return histories;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
