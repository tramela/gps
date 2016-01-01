package com.example.rafa.gps;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DataBaseActivity extends AppCompatActivity {

    protected ListView grid;
    protected DataBaseAdapter db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base);
        grid = (ListView) findViewById(R.id.db);    //Debug Lista de Coordenadas
        DataBaseAdapter db = new DataBaseAdapter(this);
        Cursor cursor = db.getallhistoric();
            if(cursor.getCount() == 0){
                Toast.makeText(DataBaseActivity.this, "Nothing to Show", Toast.LENGTH_LONG).show();
                return;
            }
            StringBuffer buffer = new StringBuffer();
            while (cursor.moveToNext()){
                buffer.append("Data :"+ cursor.getString(1) + "\n");
                buffer.append("Duração :"+ cursor.getString(2) + "\n");
                buffer.append("Distancia :"+ cursor.getString(3) + "\n");
                buffer.append("Calorias :"+ cursor.getString(4) + "\n\n");
            }
        ArrayList<String> tempList = new ArrayList<String>();
        tempList.add(buffer.toString());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, tempList);
        grid.setAdapter(adapter);

        }


    }
