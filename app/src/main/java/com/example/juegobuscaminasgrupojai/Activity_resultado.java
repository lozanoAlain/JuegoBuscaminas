package com.example.juegobuscaminasgrupojai;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_resultado extends AppCompatActivity {

    private static final int ACTIVITY_MAIN = 1;

    private TextView txtFinish=null;
    private EditText editResultado = null;
    private TextView txtMejor =null;

    private String modo=null;
    private boolean ganar=false;
    private EditText editNombre = null;
    private Button btnGuardar = null;
    private Button btnReinicio = null;
    private Button btnCompartir = null;
    private SQLiteDatabase database=null;
    private String puntuacion=null;
    private String nivel=null;

    private final String DATABASE_NAME= "puntuaciondb";

    private final String USUARIODATA="usuario";
    private final String PUNTUACIONDATA="puntuacion";
    private final String NIVELDATA="nivel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        txtFinish=(TextView)findViewById(R.id.txtFinish);
        editResultado=(EditText) findViewById(R.id.editResultado);
        editNombre=(EditText) findViewById(R.id.editNombre);
        btnGuardar=(Button) findViewById(R.id.btnGuardar);
        btnReinicio=(Button) findViewById(R.id.btnReinicio);
        btnCompartir=(Button) findViewById(R.id.btnCompartir);
        txtMejor=findViewById(R.id.txtMejor);

        Bundle extras = getIntent().getExtras();

        puntuacion = extras.getString("TIEMPO");
        nivel = extras.getString("NIVEL");
        modo=extras.getString("MODO");
        ganar=extras.getBoolean("GANAR");
        editResultado.setEnabled(false);
        if(ganar){
            String ganarTexto=String.valueOf(getText(R.string.ganar));
            editResultado.setText(ganarTexto+" "+puntuacion);

        }else{
            editResultado.setText(getText(R.string.perder));
            btnGuardar.setEnabled(false);
            btnCompartir.setEnabled(false);
            editNombre.setEnabled(false);
        }

        database = openOrCreateDatabase("RankingDB", Context.MODE_PRIVATE,null);
        database.execSQL("CREATE TABLE IF NOT EXISTS " + DATABASE_NAME +" ("+ USUARIODATA+ " VARCHAR, " + NIVELDATA + " VARCHAR, " + PUNTUACIONDATA + " VARCHAR "+" )");


        Cursor cursor=database.rawQuery("SELECT MIN("+PUNTUACIONDATA +") FROM "+ DATABASE_NAME + " WHERE " + NIVELDATA + "= '" + nivel+"'",null);
        if(cursor.getCount()==0){
            txtMejor.setText(puntuacion);
        }else{
            String puntuacionMax=null;
            while(cursor.moveToNext()){
                puntuacionMax=cursor.getString(0);

            }
            txtMejor.setText(puntuacionMax);
        }
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editNombre.getText().toString().trim().isEmpty()){
                    database.execSQL("INSERT INTO " + DATABASE_NAME + "(" + USUARIODATA + "," + NIVELDATA + " ," + PUNTUACIONDATA + ")" + "VALUES ('Invitado','"+nivel+"','"+puntuacion+"')");
                }else{
                    String user=editNombre.getText().toString().trim();
                    database.execSQL("INSERT INTO " + DATABASE_NAME + "(" + USUARIODATA + "," + NIVELDATA + "," + PUNTUACIONDATA + ")" + "VALUES('"+ user +"', '"+ nivel+"','"+puntuacion+"')");
                }
                Toast toast = Toast.makeText(getApplicationContext(),getText(R.string.guardado), Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        btnReinicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_resultado.this, MainActivity.class);
                startActivityForResult(intent, ACTIVITY_MAIN);
                finish();
            }
        });


        btnCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    comprobarNombre(editNombre.getText());
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_SEND);
                        Intent chooser = Intent.createChooser(intent, getText(R.string.escoger));
                        if (chooser.resolveActivity(getPackageManager()) != null) {
                                intent.putExtra(Intent.EXTRA_SUBJECT, getText(R.string.asunto));
                                intent.putExtra(Intent.EXTRA_TEXT, getText(R.string.contenido)+puntuacion);
                                intent.setType((String) getText(R.string.mail_MIME));
                                startActivity(intent);
                        } else {
                            throw new Exception(getString(R.string.error_correo));
                        }

                } catch (Exception e) {
                    AlertDialog alert = new AlertDialog.Builder(Activity_resultado.this).create();
                    alert.setMessage(e.getMessage());
                    alert.show();
                }


                Toast toast = Toast.makeText(getApplicationContext(),getText(R.string.compartido), Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }

    private void comprobarNombre(Editable text) throws Exception{
        if(text.toString().trim().isEmpty()){
            throw new Exception(getString(R.string.errorNombre));
        }
    }
}