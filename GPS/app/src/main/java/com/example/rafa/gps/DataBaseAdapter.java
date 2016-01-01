package com.example.rafa.gps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Rafa on 21/12/2015.
 */

public class DataBaseAdapter {

    private DataBase DataBaseHelper;
    private String[] allColumns = {DataBase.ID, DataBase.DATE, DataBase.DURATION,
            DataBase.DISTANCE, DataBase.CALORIES};


    public DataBaseAdapter(Context context) {
        DataBaseHelper = new DataBase(context);
    }

    public boolean insert(String data, String duracao, String distancia, String calorias) {
        SQLiteDatabase db = DataBaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBase.DATE, data);
        values.put(DataBase.DURATION, duracao);
        values.put(DataBase.DISTANCE, distancia);
        values.put(DataBase.CALORIES, calorias);
        long insertId = db.insert(DataBase.TABLE_NAME, null, values);
        db.close();
        if(insertId == -1)
            return false;
        else
            return true;
    }

    public Cursor getallhistoric() {
        SQLiteDatabase db = DataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DataBaseHelper.TABLE_NAME, null);// looping through all rows and adding to list
        return cursor;
    }

}