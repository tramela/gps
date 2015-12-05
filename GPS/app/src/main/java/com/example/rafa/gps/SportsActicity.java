package com.example.rafa.gps;

import android.location.Location;

import java.util.ArrayList;
import java.util.Date;


/**
 * Created by Rafa on 19/11/2015.
 */

public class SportsActicity {

    protected Integer userId;
    protected ArrayList<Position> route = new ArrayList<Position>();
    protected float distance = 0;
    protected float velocidade = 0;


    public SportsActicity() {
        this.userId = 2;
    }

    public void track(Double latitude, Double longitude, Double altitude, float speed, long chron) {
        Date currentDate = new Date();
        Position currentPosition = new Position(currentDate, latitude, longitude, altitude, speed, chron);
        route.add(currentPosition);
    }
    public Float getTotalDistance() {
        distance = 0;
        float [] dist = new float[1];
        if(route.size() > 1) {
            for (Integer i = 0; i < route.size() - 1; i++) {
                dist[0]=0;
                Location.distanceBetween(route.get(i).latitude, route.get(i).longitude, route.get(i + 1).latitude, route.get(i + 1).longitude, dist);
                distance += dist[0];
            }
        }
        return distance/1000;
    }
}

/*

    public double getTotalDistance() {
        double distance = 0;
        if(route.size() > 1) {
            for (Integer i = 0; i < route.size() - 1; i++) {
                distance += test(route.get(i).latitude, route.get(i).longitude, route.get(i + 1).latitude, route.get(i + 1).longitude);
            }
        }
        return distance;
    }

    public Float test(Double lat1, Double lon1, Double lat2, Double lon2) {
        double earthRadius = 6371;  // radius of earth in Km
        double latDiff = Math.toRadians(lat2-lat1);
        double lngDiff = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDiff /2) * Math.sin(latDiff /2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(lngDiff /2) * Math.sin(lngDiff /2);
        double c = 2 *Math.asin(Math.sqrt(a));
        //double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c;
        double meter = dist;

        //int meterConversion = 1609;

        return new Float(meter).floatValue();
    }*/
