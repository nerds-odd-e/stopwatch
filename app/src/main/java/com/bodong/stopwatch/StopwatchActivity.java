package com.bodong.stopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class StopwatchActivity extends Activity {

    TextView tv;
    Stopwatch mStopwatch;
    UIRefresher uiRefresher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        tv = (TextView) findViewById(R.id.textview);

        uiRefresher = new UIRefresher(this);
    }

    public void updateUI() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv.setText(mStopwatch.secondPassed() + "s");
            }
        });
    }

    public void start(View view) {
        if(mStopwatch == null){
            mStopwatch = new Stopwatch();
            uiRefresher.startTimer();
        }else{
            mStopwatch.resume();
        }
    }

    public void pause(View view) {
        mStopwatch.pause();
    }
}
