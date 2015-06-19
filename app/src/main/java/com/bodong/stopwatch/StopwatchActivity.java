package com.bodong.stopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class StopwatchActivity extends Activity {

    TextView tv;
    Stopwatch mStopwatch = new Stopwatch();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        tv = (TextView) findViewById(R.id.textview);

        new UIRefresher(this).startTimer();
    }

    public void updateUI() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv.setText(mStopwatch.secondPassed()+"s");
            }
        });
    }
}
