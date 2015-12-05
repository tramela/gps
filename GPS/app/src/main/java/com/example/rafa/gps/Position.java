package com.example.rafa.gps;

import java.util.Date;

/**
 * Created by Rafa on 19/11/2015.
 */
public class Position {
    protected Date date;
    protected Double latitude;
    protected Double longitude;
    protected Double altitude;
    protected float speed;
    protected long chron;

    public Position(Date date, Double latitude, Double longitude, Double altitude, float speed, long chron) {
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.speed = speed;
        this.chron = chron;
    }



}
