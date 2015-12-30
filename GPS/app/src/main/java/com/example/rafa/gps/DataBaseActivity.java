package com.example.rafa.gps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class DataBaseActivity extends AppCompatActivity {

    protected ListView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base);
        grid = (ListView) findViewById(R.id.listView);    //Debug Lista de Coordenadas


    }

}
