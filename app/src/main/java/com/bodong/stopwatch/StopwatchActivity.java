package com.bodong.stopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class StopwatchActivity extends Activity {

    TextView tv;
    Stopwatch mStopwatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        tv = (TextView) findViewById(R.id.textview);


    }

    public void updateUI() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv.setText(mStopwatch.secondPassed() + "s");
            }
        });
    }

    public void start(View view){
        mStopwatch = new Stopwatch();
        new UIRefresher(this).startTimer();
    }
}
