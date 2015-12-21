package com.example.rafa.gps;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.Chronometer;

public class PauseableChronometer extends Chronometer {

    private long timeWhenStopped = 0;

    public PauseableChronometer(Context context) {
        super(context);
    }
    public PauseableChronometer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public PauseableChronometer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    public void start() {
        setBase(SystemClock.elapsedRealtime()+timeWhenStopped);
        super.start();
    }
    @Override
    public void stop() {
        super.stop();
        timeWhenStopped = getBase() - SystemClock.elapsedRealtime();
    }
    public void reset() {
        stop();
        setBase(SystemClock.elapsedRealtime());
        this.setText("00:00:00");
        timeWhenStopped = 0;
    }
    public void update() {
        timeWhenStopped = SystemClock.elapsedRealtime() - getBase();
    }
    public long getCurrentTime() {
        this.update();
        return timeWhenStopped;
    }
    public void setCurrentTime(long time) {
        timeWhenStopped = time;
        setBase(SystemClock.elapsedRealtime()+timeWhenStopped);
    }
}