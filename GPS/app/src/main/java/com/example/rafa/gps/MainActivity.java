package com.example.rafa.gps;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Layout
    protected PauseableChronometer cronometro;

    protected TextView velo;
    protected TextView dist;
    protected TextView prec;
    protected TextView velmed;

    protected ListView grid;

    protected Button btStart, btPause, btReset, btMapa;

    // Global Vars
    protected boolean isClickPause = false;
    protected SportsActicity sports;
    protected long REFRESH_TIME = 1000; // refresh GPS 1s em 1s
    protected float REFRESH_DISTANCE = 0;

    protected int flag = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        startGPS();

        cronometro = (PauseableChronometer) findViewById(R.id.chronometer);
        btStart = (Button) findViewById(R.id.btLocalizar);
        btPause = (Button) findViewById(R.id.pause);
        btReset = (Button) findViewById(R.id.stop);
        btMapa = (Button) findViewById(R.id.mapa);


        btStart.setEnabled(true);
        btPause.setEnabled(false);
        btReset.setEnabled(false);
        btMapa.setEnabled(true);

        velo = (TextView) findViewById(R.id.velo);
        velmed = (TextView) findViewById(R.id.velmed);
        dist = (TextView) findViewById(R.id.dist);
        //grid = (ListView) findViewById(R.id.listView);
        prec = (TextView) findViewById(R.id.precisao);

        cronometro.setText("00:00:00");
        cronometro.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {

            @Override
            public void onChronometerTick(Chronometer chronometer) {
                CharSequence text = chronometer.getText();
                if (text.length() == 5) {
                    chronometer.setText("00:" + text);
                } else if (text.length() == 7) {
                    chronometer.setText("0" + text);
                }
            }
        });

        //Botão START
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClickPause) {
                    cronometro.start();
                    isClickPause = false;

                } else {
                    sports = new SportsActicity();
                    cronometro.start();
                }
                flag = 1 ;
                btStart.setEnabled(false);
                btPause.setEnabled(true);
                btReset.setEnabled(false);
            }
        });

        //Botão PAUSE
        btPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cronometro.stop();
                isClickPause = true;
                btPause.setEnabled(false);
                btStart.setEnabled(true);
                btReset.setEnabled(true);
                flag = 0;

            }

        });

        //Botão RESET
        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cronometro.reset();
                btStart.setEnabled(true);
                sports.distance=0;
                dist.setText("");
                flag = 0;
            }
        });

        //Botão ROTA
        btMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //maps = new MapsActivity();
                //Intent intent = new Intent(btrota.getContext(), MapsActivity.class);
                //maps.setLat(latitude);
                //maps.setLon(longitude);
                //maps.setLatLon(latitude, longitude);

                Gson gson = new Gson();
                String currentSport = gson.toJson(sports);

                Intent mapIntent = new Intent(btMapa.getContext(), MapsActivity.class);
                mapIntent.putExtra("currentSport", currentSport);
                startActivity(mapIntent);
            }
        });

    }

    //Método que faz a leitura de fato dos valores recebidos do GPS
    public void startGPS() {
        LocationManager lManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener lListener = new LocationListener() {
            public void onLocationChanged(Location locat) {
                updateView(locat);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {

                Toast.makeText(getBaseContext(), "GPS LIGADO!! ", Toast.LENGTH_SHORT).show();
            }

            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
                Toast.makeText(getBaseContext(), "GPS DESLIGADO!! ", Toast.LENGTH_SHORT).show();
            }
        };
       lManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, REFRESH_TIME, REFRESH_DISTANCE, lListener);
    }



    //  Método que faz a atualização da tela para o utilizador.
    public void updateView(Location locat){
        if (flag == 1) {
            sports.track(locat.getLatitude(), locat.getLongitude(), locat.getAltitude(), locat.getSpeed(), cronometro.getBase());
            //refreshGrid();
            Float velocidade = (locat.getSpeed() * 3600) / 1000;
            String v = String.format("%.1f",velocidade);
            velo.setText(v + " km/h");
            prec.setText(Float.toString(locat.getAccuracy()));
            String s = String.format("%.1f", sports.getTotalDistance()/1000); //Converte o Float em uma string com apenas uma casa decimal de forma a representar a distancia percorrida de 100m em 100m
            dist.setText(s + " km");

            int timeSeconds = (int) ((cronometro.getCurrentTime() / 1000) % 60);
            float media = sports.getTotalDistance() / timeSeconds;
            System.out.println("dist");
            System.out.println(sports.getTotalDistance());
            System.out.println("time");
            System.out.println(timeSeconds);
            if(media != 0) {
                velmed.setText(String.format("%.1f", ((media*3600)/1000)) + " km/h");
            }
        }
    }

    /*Função Debug */
    public void refreshGrid() {
        ArrayList<String> tempList = new ArrayList<String>();

        for(Integer i = 0; i < sports.route.size(); i++) {

            tempList.add(sports.route.get(i).latitude.toString() + " | " + sports.route.get(i).longitude.toString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, tempList);
        grid.setAdapter(adapter);

        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
