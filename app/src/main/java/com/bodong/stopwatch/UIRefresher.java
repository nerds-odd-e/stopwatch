package com.bodong.stopwatch;

import java.util.Timer;
import java.util.TimerTask;

public class UIRefresher {
    private final StopwatchActivity stopwatchActivity;

    public UIRefresher(StopwatchActivity stopwatchActivity) {
        this.stopwatchActivity = stopwatchActivity;
    }

    public void startTimer() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                stopwatchActivity.updateUI();
            }
        }, 0, 300);
    }
}