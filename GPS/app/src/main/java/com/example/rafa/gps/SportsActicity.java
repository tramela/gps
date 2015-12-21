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
    protected int flag2 = 0;


    public SportsActicity() {
        this.userId = 1;
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
                flag2=1;
                dist[0]=0;
                Location.distanceBetween(route.get(i).latitude, route.get(i).longitude, route.get(i + 1).latitude, route.get(i + 1).longitude, dist);
                distance += dist[0];
            }
        }
        return distance;
    }

    public ArrayList<Position> getRoutes() {
        return this.route;
    }
}
