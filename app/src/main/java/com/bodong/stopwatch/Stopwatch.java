package com.bodong.stopwatch;

/**
 * Created by asuka on 15/6/19.
 */
public class Stopwatch {


    private long startTime;
    private final Clock clock;
    private boolean isRunning;
    private int totalTime;

    public Stopwatch() {
        this(new Clock() {
            @Override
            public long getCurrentTimeMillis() {
                return System.currentTimeMillis();
            }
        });
    }

    public Stopwatch(Clock clock) {
        this.clock = clock;
        resume();
    }

    public void pause() {
        totalTime += secondPassed();
        isRunning = false;
    }

    public void resume() {
        isRunning = true;
        startTime = clock.getCurrentTimeMillis();
    }

    public int secondPassed() {
        if (isRunning) {
            return timeFromLastResume() + totalTime;
        } else {
            return totalTime;
        }
    }

    private int timeFromLastResume() {
        return (int) ((clock.getCurrentTimeMillis() - startTime) / 1000);
    }
}
