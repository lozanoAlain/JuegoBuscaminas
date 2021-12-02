package com.example.juegobuscaminasgrupojai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    private static final int ACTIVITY_PREGUNTAS2 = 1;

    private Spinner spinnerNivel;
    private Spinner spinnerModo;
    private Button buttonStart;
    private Button buttonTutorial;
    private VideoView videoView;
    private MediaController mediaPlayer;
    private TextView textStart;

    String nivel ;
    String modo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textStart=(TextView) findViewById(R.id.textStart);
        Animation animation_Text= AnimationUtils.loadAnimation(this,R.anim.text_anim);
        textStart.startAnimation(animation_Text);
        videoView=(VideoView) findViewById(R.id.videoViewTut);
        videoView.setVisibility(View.INVISIBLE);
        spinnerNivel= (Spinner) findViewById(R.id.spinnerNivel);
        ArrayAdapter <CharSequence> adapterNivel = ArrayAdapter.createFromResource(this,R.array.nivel, android.R.layout.simple_spinner_item);
        adapterNivel.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerNivel.setAdapter(adapterNivel);
        spinnerNivel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                nivel = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerModo= (Spinner) findViewById(R.id.spinnerModo);
        ArrayAdapter <CharSequence> adapterModo = ArrayAdapter.createFromResource(this,R.array.modo, android.R.layout.simple_spinner_item);
        adapterModo.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerModo.setAdapter(adapterModo);
        spinnerModo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                modo = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        buttonStart=(Button) findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(modo.equals("Supervivencia")){
                    Toast toast = Toast.makeText(getApplicationContext(),getText(R.string.noImplementado), Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    Intent intent = new Intent(MainActivity.this, Second_Activity.class);
                    intent.putExtra("PARAM_1", nivel);
                    intent.putExtra("PARAM_2", modo);
                    startActivityForResult(intent, ACTIVITY_PREGUNTAS2);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_in_left);
                    finish();
                }

            }
        });
        buttonTutorial=(Button) findViewById(R.id.buttonTutorial);
        mediaPlayer=new MediaController(this);
        buttonTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    spinnerModo.setVisibility(View.INVISIBLE);
                    spinnerNivel.setVisibility(View.INVISIBLE);
                    videoView.setVisibility(View.VISIBLE);
                    videoView.setMediaController(mediaPlayer);
                    mediaPlayer.setAnchorView(videoView);
                    buttonStart.setEnabled(false);
                    videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.tutorial));
                    videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        Toast.makeText(getBaseContext(),"El video esta preparado",Toast.LENGTH_LONG).show();
                        mediaPlayer.show(20000);
                        videoView.start();
                    }
                });
                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        Toast.makeText(getBaseContext(),"El video ha terminado",Toast.LENGTH_LONG).show();
                        mediaPlayer.show(20000);
                        videoView.setVisibility(View.INVISIBLE);
                        spinnerModo.setVisibility(View.VISIBLE);
                        spinnerNivel.setVisibility(View.VISIBLE);
                        buttonStart.setEnabled(true);
                    }
                });
            }
        });
    }

}