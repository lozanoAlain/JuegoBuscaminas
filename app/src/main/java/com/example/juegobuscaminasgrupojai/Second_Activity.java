package com.example.juegobuscaminasgrupojai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class Second_Activity extends AppCompatActivity {

    private static final int ACTIVITY_MAIN = 1;
    private static final int ACTIVITY_RESULT = 3;


    private EditText tfTime = null;
    private EditText tfScore = null;
    private int seconds = 0;
    private int score = 0;
    private Button buttonBack = null;
    private GridLayout gridBuscaminas = null;
    private Timer timer = null;

    private String nivel = null;
    private String modo = null;
    private boolean ganar = false;
    private int numBanderas=0;
    private int numDescubiertas=0;


    private int[][] Tablero;

    Logica logica = new Logica();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        gridBuscaminas = (GridLayout) findViewById(R.id.gridBuscaminas);

        buttonBack = (Button) findViewById(R.id.btnBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Second_Activity.this, MainActivity.class);
                startActivityForResult(intent, ACTIVITY_MAIN);
            }
        });

        tfTime = (EditText) findViewById(R.id.timeField);
        tfTime.setEnabled(false);
        tfScore = (EditText) findViewById(R.id.scoreField);
        tfScore.setEnabled(false);
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                tfTime.setText(String.valueOf(seconds));
                seconds++;
            }
        }, 1000, 1000);

        Bundle extras = getIntent().getExtras();
        nivel = extras.getString("PARAM_1");
        modo = extras.getString("PARAM_2");

        switch (nivel) {
            case "Facil":
                iniciarGridLayout(5);
                break;
            case "Intermedio":
                iniciarGridLayout(6);
                break;
            case "Dificil":
                iniciarGridLayout(7);
                break;
        }

    }

    private void iniciarGridLayout(int i) {
        boolean bandera = false;
        numBanderas=i;

        tfScore.setText(String.valueOf(numBanderas));
        gridBuscaminas.setColumnCount(i);
        gridBuscaminas.setRowCount(i);
        Tablero = logica.meterBombasyNumeros(i);

        for (int y = 0; y < i; y++) {
            for (int x = 0; x < i; x++) {


                ImageButtonPosition button = new ImageButtonPosition(this);
                button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                button.setId(View.generateViewId());
                button.setImageDrawable(getDrawable(R.drawable.sin_descubrir));
                button.setColumna(x);
                button.setFila(y);
                gridBuscaminas.addView(button);
                button.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (button.isBandera()) {
                            button.setImageDrawable(getDrawable(R.drawable.sin_descubrir));
                            button.setBandera(false);
                            numBanderas++;
                        } else {
                            button.setImageDrawable(getDrawable(R.drawable.bandera));
                            button.setBandera(true);
                            numBanderas--;
                        }
                        tfScore.setText(String.valueOf(numBanderas));
                        return true;
                    }
                });
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        button.setEnabled(false);
                        if (button.isBandera()) {
                            Toast toastErrorBandera = Toast.makeText(getApplicationContext(), getText(R.string.errorBandera), Toast.LENGTH_SHORT);
                            toastErrorBandera.show();
                            button.setEnabled(true);
                        } else {
                            if (Tablero[button.getFila()][button.getColumna()] >= 9) {
                                button.setImageDrawable(getDrawable(R.drawable.mina));
                                vaciarTablero(i);
                                timer.cancel();
                                Intent intent = new Intent(Second_Activity.this, Activity_resultado.class);
                                intent.putExtra("NIVEL", nivel);
                                intent.putExtra("MODO", modo);
                                intent.putExtra("TIEMPO", String.valueOf(timer));
                                intent.putExtra("GANAR", ganar);
                                startActivityForResult(intent, ACTIVITY_RESULT);
                            } else {
                                switch (Tablero[button.getFila()][button.getColumna()]) {
                                    case 1:
                                        button.setImageDrawable(getDrawable(R.drawable.uno));
                                        button.setDescubierta(true);
                                        numDescubiertas++;
                                        break;
                                    case 2:
                                        button.setImageDrawable(getDrawable(R.drawable.dos));
                                        button.setDescubierta(true);
                                        numDescubiertas++;
                                        break;
                                    case 3:
                                        button.setImageDrawable(getDrawable(R.drawable.tres));
                                        button.setDescubierta(true);
                                        numDescubiertas++;
                                        break;
                                    case 4:
                                        button.setImageDrawable(getDrawable(R.drawable.cuatro));
                                        button.setDescubierta(true);
                                        numDescubiertas++;
                                        break;
                                    case 5:
                                        button.setImageDrawable(getDrawable(R.drawable.cinco));
                                        button.setDescubierta(true);
                                        numDescubiertas++;
                                        break;
                                    case 6:
                                        button.setImageDrawable(getDrawable(R.drawable.seis));
                                        button.setDescubierta(true);
                                        numDescubiertas++;
                                        break;
                                    case 7:
                                        button.setImageDrawable(getDrawable(R.drawable.siete));
                                        button.setDescubierta(true);
                                        numDescubiertas++;
                                        break;
                                    case 8:
                                        button.setImageDrawable(getDrawable(R.drawable.ocho));
                                        button.setDescubierta(true);
                                        numDescubiertas++;
                                        break;
                                    case 0:
                                        button.setImageDrawable(getDrawable(R.drawable.nothing));
                                        //recorrer( button.getFila(),button.getColumna(), i, button);
                                        button.setEnabled(false);
                                        numDescubiertas++;
                                        break;
                                }
                            }

                        }
                        if(numDescubiertas==i*i-i){
                            ganar=true;
                            vaciarTablero(i);
                            timer.cancel();
                            Intent intent = new Intent(Second_Activity.this, Activity_resultado.class);
                            intent.putExtra("NIVEL", nivel);
                            intent.putExtra("MODO", modo);
                            intent.putExtra("TIEMPO", String.valueOf(seconds));
                            intent.putExtra("GANAR", ganar);
                            startActivityForResult(intent, ACTIVITY_RESULT);
                        }

                    }

                });

            }
        }
    }


    private void vaciarTablero(int i) {
        for (int y = 0; y < i; y++) {
            for (int x = 0; x < i; x++) {
                if (Tablero[x][y] >= 9) {
                    for (int index = 0; index < gridBuscaminas.getChildCount(); index++) {
                        ImageButtonPosition imageButtonPosition = (ImageButtonPosition) gridBuscaminas.getChildAt(index);
                        if (imageButtonPosition.getColumna() == y && imageButtonPosition.getFila() == x) {
                            imageButtonPosition.setImageDrawable(getDrawable(R.drawable.mina));
                        }
                    }

                }
            }
        }
    }

    private void recorrer(int x, int y, int i, ImageButtonPosition button) {
        if (x >= 0 && x < i && y >= 0 && y < i) {
            ImageButtonPosition buttonPosition=(ImageButtonPosition) gridBuscaminas.getChildAt(x * i + y + 1);
            if (!buttonPosition.isDescubierta()){
                if (Tablero[x][y] == 0) {
                    buttonPosition.setImageDrawable(getDrawable(R.drawable.nothing));
                    buttonPosition.setDescubierta(true);
                    buttonPosition.setEnabled(false);
                    if (y == 0) {
                        if (x == 0) {
                            recorrer(x + 1, y, i, buttonPosition);
                            recorrer(x, y + 1, i, buttonPosition);
                            recorrer(x + 1, y + 1, i, buttonPosition);
                        } else if (x == i - 1) {
                            recorrer(x - 1, y, i, buttonPosition);
                            recorrer(x - 1, y + 1, i, buttonPosition);
                            recorrer(x, y + 1, i, buttonPosition);

                        } else {
                            recorrer(x - 1, y, i, buttonPosition);
                            recorrer(x + 1, y, i, buttonPosition);
                            recorrer(x - 1, y + 1, i, buttonPosition);
                            recorrer(x, y + 1, i, buttonPosition);
                            recorrer(x + 1, y + 1, i, buttonPosition);

                        }
                    }else if (y == i - 1) {
                        if (x == i - 1) {
                            recorrer(x - 1, y - 1, i, buttonPosition);
                            recorrer(x, y - 1, i, buttonPosition);
                            recorrer(x - 1, y, i, buttonPosition);

                        } else if (x == 0) {
                            recorrer(x, y - 1, i, buttonPosition);
                            recorrer(x + 1, y - 1, i, buttonPosition);
                            recorrer(x + 1, y, i, buttonPosition);

                        } else {
                            recorrer(x - 1, y - 1, i, buttonPosition);
                            recorrer(x, y - 1, i, buttonPosition);
                            recorrer(x + 1, y - 1, i, buttonPosition);
                            recorrer(x - 1, y, i, buttonPosition);
                            recorrer(x + 1, y, i, buttonPosition);
                        }
                    } else if (x == 0) {
                        recorrer(x, y - 1, i, buttonPosition);
                        recorrer(x + 1, y - 1, i, buttonPosition);
                        recorrer(x + 1, y , i, buttonPosition);
                        recorrer(x + 1, y + 1, i, buttonPosition);
                        recorrer(x, y +1, i, buttonPosition);
                    } else if (x == i - 1) {
                        recorrer(x - 1, y - 1, i, buttonPosition);
                        recorrer(x, y - 1, i, buttonPosition);
                        recorrer(x - 1, y , i, buttonPosition);
                        recorrer(x - 1, y + 1, i, buttonPosition);
                        recorrer(x , y + 1, i, buttonPosition);
                    } else {
                        recorrer(x - 1, y - 1, i, buttonPosition);
                        recorrer(x, y - 1, i, buttonPosition);
                        recorrer(x + 1, y - 1, i, buttonPosition);
                        recorrer(x - 1, y, i, buttonPosition);
                        recorrer(x + 1, y, i, buttonPosition);
                        recorrer(x - 1, y + 1, i, buttonPosition);
                        recorrer(x , y + 1, i, buttonPosition);
                        recorrer(x + 1, y +1, i, buttonPosition);
                    }

                } else if (Tablero[x][y] >= 1 && Tablero[x][y] < 9 ) {
                    buttonPosition.setImageDrawable(getDrawable(R.drawable.uno));
                    buttonPosition.setDescubierta(true);
                    buttonPosition.setEnabled(false);

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

