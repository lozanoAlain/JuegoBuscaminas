package com.example.juegobuscaminasgrupojai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


public class MainActivity extends AppCompatActivity {

    private Spinner spinnerNivel;
    private Spinner spinnerModo;
    private Button buttonStart;
    String nivel ;
    String modo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
                /*
                Intent intent = new Intent(MainActivity.this, Preguntas2.class);
                intent.putExtra("PARAM_1", puntos);
                intent.putExtra("PARAM_2", nombre);
                startActivityForResult(intent, ACTIVITY_PREGUNTAS2);
                */

            }
        });
    }

}