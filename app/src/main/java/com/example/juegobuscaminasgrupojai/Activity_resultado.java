package com.example.juegobuscaminasgrupojai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_resultado extends AppCompatActivity {


    private TextView txtFinished = null;
    private EditText editResultado = null;
    private int puntuacion = 0;
    private EditText editNombre = null;
    private Button btnGuardar = null;
    private Button btnReinicio = null;
    private Button btnCompartir = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        txtFinished.findViewById(R.id.txtFinished);
        editResultado.findViewById(R.id.editResultado);
        editNombre.findViewById(R.id.editNombre);
        editNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comprobarNombre(editNombre.getText());
            }
        });
        btnGuardar.findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comprobarNombre(editNombre.getText());

                Toast toast = Toast.makeText(getApplicationContext(),getText(R.string.guardado), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        btnReinicio.findViewById(R.id.btnReinicio);
        btnReinicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_resultado.this, MainActivity.class);
                finish();
            }
        });

        btnCompartir.findViewById(R.id.btnCompartir);
        btnCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(),getText(R.string.compartido), Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        Bundle extras = getIntent().getExtras();
        puntuacion = extras.getInt("PUNTUACION");
        editResultado.setText(puntuacion);
    }

    private void comprobarNombre(Editable text) {
        if(text.toString().trim().isEmpty()){
            editNombre.setError(getText(R.string.errorNombre));
        }
    }
}