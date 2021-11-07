package com.example.juegobuscaminasgrupojai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

public class Second_Activity extends AppCompatActivity {

    private static final int ACTIVITY_MAIN = 1;
    private static final int ACTIVITY_RESULT = 3;


    private EditText tfTime =null;
    private EditText tfScore=null;
    private int seconds =0;
    private int score=0;
    private Button buttonBack;
    private GridLayout gridBuscaminas;
    private Timer timer;



    private int[][] Tablero;

    Logica logica = new Logica();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        gridBuscaminas=(GridLayout) findViewById(R.id.gridBuscaminas);

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
        timer=new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                tfTime.setText(String.valueOf(seconds));
                seconds++;
            }
        }, 1000, 1000);

        Bundle extras = getIntent().getExtras();
        String nivel = extras.getString("PARAM_1");
        String modo = extras.getString("PARAM_2");

        switch (nivel){
            case "Facil":
                iniciarGridLayout(5);
                break;
            case  "Intermedio":
                iniciarGridLayout(6);
                break;
            case "Dificil":
                iniciarGridLayout(7);
                break;
        }



    }
    private void iniciarGridLayout(int i) {
        gridBuscaminas.setColumnCount(i);
        gridBuscaminas.setRowCount(i);
        Tablero =logica.meterBombasyNumeros(i);


        for (int y = 0; y < i; y++) {
            for (int x = 0; x < i; x++) {

                    ImageButtonPosition button= new ImageButtonPosition(this);
                    button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                    button.setId(View.generateViewId());
                    button.setImageDrawable(getDrawable(R.drawable.sin_descubrir));
                    button.setColumna(y);
                    button.setFila(x);
                    gridBuscaminas.addView(button);
                    button.setOnClickListener(new View.OnClickListener() {
                    @Override
                        public void onClick(View view) {
                        button.setEnabled(false);
                        if(Tablero[button.getFila()][button.getColumna()]>=9){
                                    button.setImageDrawable(getDrawable(R.drawable.mina));
                                    vaciarTablero(i);
                                    timer.cancel();

                                }else{
                                    switch (Tablero[button.getFila()][button.getColumna()]){
                                        case 1:
                                            button.setImageDrawable(getDrawable(R.drawable.uno));
                                            break;
                                        case 2:
                                            button.setImageDrawable(getDrawable(R.drawable.dos));
                                            break;
                                        case 3:
                                            button.setImageDrawable(getDrawable(R.drawable.tres));
                                            break;
                                        case 4:
                                            button.setImageDrawable(getDrawable(R.drawable.cuatro));
                                            break;
                                        case 5:
                                            button.setImageDrawable(getDrawable(R.drawable.cinco));
                                            break;
                                        case 6:
                                            button.setImageDrawable(getDrawable(R.drawable.seis));
                                            break;
                                        case 7:
                                            button.setImageDrawable(getDrawable(R.drawable.siete));
                                            break;
                                        case 8:
                                            button.setImageDrawable(getDrawable(R.drawable.ocho));
                                            break;
                                        default:
                                            button.setImageDrawable(getDrawable(R.drawable.nothing));



                                    }
                                }

                            }



                        });

            }
        }


    }

    private void vaciarTablero(int i) {
        for (int y = 0; y < i; y++) {
            for (int x = 0; x < i; x++) {
                if(Tablero[x][y]>=9){
                    for(int index=0;index<gridBuscaminas.getChildCount();index++){
                        ImageButtonPosition imageButtonPosition= (ImageButtonPosition) gridBuscaminas.getChildAt(index);
                        if(imageButtonPosition.getColumna()==y && imageButtonPosition.getFila()==x){
                            imageButtonPosition.setImageDrawable(getDrawable(R.drawable.mina));
                        }
                    }

                }
            }
        }
    }
}


/*
Intent intent = new Intent(Second_Activity.this, Activity_resultado.class);
                startActivityForResult(intent, ACTIVITY_RESULT);

if(Tablero[x][y]>=9){
                    ImageButton button= new ImageButton(this);
                    button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                    button.setId(View.generateViewId());
                    button.setImageDrawable(getDrawable(R.drawable.mina));
                    gridBuscaminas.addView(button);

                }
*/

