package com.bodong.stopwatch;

/**
 * Created by asuka on 15/6/19.
 */
public class Stopwatch {

    private final long startTime;
    private final Clock clock;

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
    }

    public int secondPassed() {
        return (int) ((clock.getCurrentTimeMillis() - startTime) / 1000);
    }
}
