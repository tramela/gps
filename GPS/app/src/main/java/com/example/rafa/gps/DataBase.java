package com.example.rafa.gps;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "iseprunning.db";
    public static final String TABLE_NAME = "historico";
    private static final int DATABASE_VERSION = 1;
    public static final String ID = "_id";
    public static final String DATE = "data";
    public static final String DURATION = "duracao";
    public static final String DISTANCE = "distancia";
    public static final String CALORIES = "calorias";
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "( " + ID
            + " integer primary key autoincrement, " + DATE
            + " text not null, " + DURATION + " text not null, "
            + DISTANCE+" text not null" +
            ", "+ CALORIES +" text not null);";

    public int student_ID;

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DataBase.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
