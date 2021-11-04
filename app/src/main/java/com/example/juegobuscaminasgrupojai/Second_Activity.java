package com.example.juegobuscaminasgrupojai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

public class Second_Activity extends AppCompatActivity {

    private static final int ACTIVITY_MAIN = 1;
    private static final int ACTIVITY_RESULT = 3;



    private EditText tfTime =null;
    private EditText tfScore=null;
    private int time=0;
    private int score=0;
    private Button buttonBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        buttonBack= (Button) findViewById(R.id.btnBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Second_Activity.this, MainActivity.class);
                startActivityForResult(intent, ACTIVITY_MAIN);
            }
        });

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

/*
Intent intent = new Intent(Second_Activity.this, Activity_resultado.class);
                startActivityForResult(intent, ACTIVITY_RESULT);

*/
    }


}