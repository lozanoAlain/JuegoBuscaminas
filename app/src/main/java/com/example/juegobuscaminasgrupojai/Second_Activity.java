package com.example.juegobuscaminasgrupojai;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

public class Second_Activity extends AppCompatActivity {

private EditText tfTime =null;
private EditText tfScore=null;
    private int time=0;
    private int score=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        tfTime = (EditText) findViewById( R.id.timeField);
        tfScore = (EditText) findViewById(R.id.scoreField);
        Timer T=new Timer();
        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                tfTime.setText(String.valueOf(time));
                time++;
            }
        }, 1000, 1000);



    }


}