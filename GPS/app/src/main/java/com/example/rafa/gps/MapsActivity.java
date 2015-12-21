package com.example.rafa.gps;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    protected SportsActicity sports;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        String jsonMyObject = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            jsonMyObject = extras.getString("currentSport");
        }
        this.sports = new Gson().fromJson(jsonMyObject, SportsActicity.class);
    }

    public void zoom (Double latit, Double longit){
        Location start = new Location("");
        start.setLatitude(latit);
        start.setLongitude(longit);

        if (start != null)
        {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(start.getLatitude(), start.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(start.getLatitude(), start.getLongitude()))
                    .zoom(15)                   // Sets the zoom
                    .bearing(0)                // Sets the orientation of the camera to east
                    .tilt(0)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        if(this.sports.flag2 != 0) {
            zoom(this.sports.route.get(0).latitude, this.sports.route.get(0).longitude);
            ArrayList<Position> routesList = this.sports.getRoutes();
            this.handleGetDirectionsResult(routesList);
        }
    }

    public void handleGetDirectionsResult(ArrayList<Position> routesList)
    {

        for(int i = 0 ; i < routesList.size()-1; i++) {
            LatLng prev = new LatLng(routesList.get(i).latitude, routesList.get(i).longitude);
            LatLng current = new LatLng(routesList.get(i+1).latitude, routesList.get(i+1).longitude);

            mMap.addPolyline((new PolylineOptions())
                    .add(prev, current).width(20).color(Color.BLUE)
                    .visible(true));
        }
    }
}
