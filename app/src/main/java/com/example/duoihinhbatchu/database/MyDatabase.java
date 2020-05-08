package com.example.duoihinhbatchu.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.duoihinhbatchu.BuildConfig;
import com.example.duoihinhbatchu.model.Question;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MyDatabase extends SQLiteOpenHelper {

    private Context context;
    private String DB_PATH = "data/data/" + BuildConfig.APPLICATION_ID + "/databases/";
    private final static String DB_NAME ="dhbc.db";
    private final static String TABLE_QUESTION = "Question";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    public MyDatabase(Context context){
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.context = context;

        boolean dbexis = checkdatabase();

        if(!dbexis){
            System.out.println("Database doesn't exist!");
            createDatabase();
        }
    }

    public void updateQuestion(Question question){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id" , question.getId());
        values.put("content", question.getContent());
        values.put("giainghia", question.getGiaiNghia());
        values.put("ok", 1);
        db.update(TABLE_QUESTION, values, "id = ?",
                new String[]{String.valueOf(question.getId())});
        db.close();
    }

    public ArrayList<Question> getQuestionOk(int ok){
        db = this.getReadableDatabase();
        ArrayList<Question> questions = new ArrayList<>();
        String select = "SELECT * FROM question where ok = " + ok;
        Cursor cursor = db.rawQuery(select, null);
        if (cursor.moveToFirst()){
            do {
                Question question = new Question(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3));
                questions.add(question);
            }while (cursor.moveToNext());
        }
        db.close();
        return questions;
    }

    public Question getQuestionDB(int id){
        db = this.getReadableDatabase();

        Question question = null;

        String select = "SELECT * FROM "+ TABLE_QUESTION + " where id = " + id;

        Cursor cursor = db.rawQuery(select, null);

        if(cursor.moveToFirst()){
            do {
                question = new Question(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3));

            }while (cursor.moveToNext());
        }
        db.close();
        return question;
    }

    private boolean checkdatabase() {
        boolean checkdb = false;
        try{
            String myPath = DB_PATH + DB_NAME;
            File dbFile = new File(myPath);
            checkdb = dbFile.exists();
        }catch (SQLException e){
            System.out.println("Database doesn's exist");
        }
        return checkdb;
    }

    private void createDatabase() {
        this.getReadableDatabase();

        try {
            copyDatabase();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void copyDatabase() throws IOException {
        db = this.getWritableDatabase();
        AssetManager dirPath = context.getAssets();
        InputStream myInput = context.getAssets().open("databases/" + DB_NAME);
        File file = new File((DB_PATH));
        file.mkdirs();

        OutputStream myOutput = new FileOutputStream(DB_PATH + DB_NAME);

        byte[] buffer = new byte[1024];
        int length;

        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
