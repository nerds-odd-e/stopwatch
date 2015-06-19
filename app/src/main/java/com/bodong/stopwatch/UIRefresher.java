package com.bodong.stopwatch;

import java.util.Timer;
import java.util.TimerTask;

public class UIRefresher {
    private final StopwatchActivity stopwatchActivity;
    private Timer timer = new Timer();

    public UIRefresher(StopwatchActivity stopwatchActivity) {
        this.stopwatchActivity = stopwatchActivity;
    }

    public void startTimer() {
        timer.cancel();

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                stopwatchActivity.updateUI();
            }
        }, 0, 300);
    }
}