package com.bodong.stopwatch;

/**
 * Created by asuka on 15/6/19.
 */
public class Stopwatch {

    private final long startTime;
    private final Clock clock;
    private boolean isRunning;
    private int pauseTime;

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
        this.startTime = clock.getCurrentTimeMillis();
        isRunning = true;
    }

    public void pause() {
        pauseTime = secondPassed();
        isRunning = false;
    }

    public int secondPassed() {
        int second = (int) ((clock.getCurrentTimeMillis() - startTime) / 1000);
        return isRunning ? second : pauseTime;
    }
}
