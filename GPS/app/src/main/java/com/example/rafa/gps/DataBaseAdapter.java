package com.example.rafa.gps;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Rafa on 21/12/2015.
 */

public class DataBaseAdapter {

    private SQLiteDatabase database;
    private DataBase DataBaseHelper;
    private String[] allColumns = {DataBase.ID, DataBase.DATE, DataBase.DURATION,
            DataBase.DISTANCE, DataBase.CALORIES};


    public DataBaseAdapter(Context context) {
        DataBaseHelper = new DataBase(context);
    }

    public int createContacto(String data, String duracao, String distancia, String calorias) {
        ContentValues values = new ContentValues();
        values.put(DataBase.DATE, data);
        values.put(DataBase.DURATION, duracao);
        values.put(DataBase.DISTANCE, distancia);
        values.put(DataBase.CALORIES, calorias);
        long insertId = database.insert(DataBase.TABLE_NAME, null, values);
        database.close();
        return (int) insertId;
    }


}