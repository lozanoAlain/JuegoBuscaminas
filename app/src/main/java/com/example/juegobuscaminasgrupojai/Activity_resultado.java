package com.example.juegobuscaminasgrupojai;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

public class Activity_resultado extends AppCompatActivity {

    private static final int ACTIVITY_MAIN = 1;
    private static final int ACTIVITY_FOTO = 2;
    private final String DATABASE_NAME = "puntuaciondb";
    private EditText editResultado = null;
    private final String USUARIODATA = "usuario";
    private final String PUNTUACIONDATA = "puntuacion";
    private final String NIVELDATA = "nivel";
    private EditText editNombre = null;
    private Button btnGuardar = null;
    private Button btnReinicio = null;
    private Button btnCompartir = null;
    private final String IMAGEDATA = "imagen";
    private TextView txtFinish = null;
    private TextView txtMejor = null;
    private final String modo = null;
    private final boolean ganar = true;
    private SQLiteDatabase database = null;
    private final int puntuacion = 0;
    private final String nivel = null;
    private Button buttonFoto;
    private byte[] blob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        txtFinish = findViewById(R.id.txtFinish);
        editResultado = findViewById(R.id.editResultado);
        editNombre = findViewById(R.id.editNombre);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnReinicio = findViewById(R.id.btnReinicio);
        btnCompartir = findViewById(R.id.btnCompartir);
        buttonFoto = findViewById(R.id.btnFoto);
        txtMejor = findViewById(R.id.txtMejor);

        Bundle extras = getIntent().getExtras();

        //puntuacion = extras.getInt("TIEMPO");
        //nivel = extras.getString("NIVEL");
        //modo = extras.getString("MODO");
        //ganar = extras.getBoolean("GANAR");
        editResultado.setEnabled(false);
        if (ganar) {
            String ganarTexto = String.valueOf(getText(R.string.ganar));
            editResultado.setText(ganarTexto + " " + puntuacion);

        } else {
            editResultado.setText(getText(R.string.perder));
            btnGuardar.setEnabled(false);
            btnCompartir.setEnabled(false);
            editNombre.setEnabled(false);


        }

        database = openOrCreateDatabase("RankingDB", Context.MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS " + DATABASE_NAME + " (" + USUARIODATA + " VARCHAR, " + NIVELDATA + " VARCHAR, " + PUNTUACIONDATA + " INTEGER, " + IMAGEDATA + " BLOB " + ")");


        Cursor cursor = database.rawQuery("SELECT MIN(" + PUNTUACIONDATA + ") FROM " + DATABASE_NAME + " WHERE " + NIVELDATA + "= '" + nivel + "'", null);
        if (cursor.getCount() == 0) {
            txtMejor.setText(puntuacion);
        } else {
            String puntuacionMax = null;
            while (cursor.moveToNext()) {
                puntuacionMax = cursor.getString(0);

            }
            txtMejor.setText(puntuacionMax);
        }
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (editNombre.getText().toString().trim().isEmpty()) {
                        Bitmap imageBitmap = ((BitmapDrawable)getResources().getDrawable(R.drawable.user)).getBitmap();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream(20480);
                        imageBitmap.compress(Bitmap.CompressFormat.PNG, 0, baos);
                        blob = baos.toByteArray();
                        database.execSQL("INSERT INTO " + DATABASE_NAME + "(" + USUARIODATA + "," + NIVELDATA + " ," + PUNTUACIONDATA + "," + IMAGEDATA + ")" + "VALUES ('Invitado','" + nivel + "','" + puntuacion + "','" + blob + "')");
                    } else {
                        if (blob == null) {
                            throw new Exception(getString(R.string.error_foto));
                        }
                        String user = editNombre.getText().toString().trim();
                        database.execSQL("INSERT INTO " + DATABASE_NAME + "(" + USUARIODATA + "," + NIVELDATA + "," + PUNTUACIONDATA + "," + IMAGEDATA + ")" + "VALUES('" + user + "', '" + nivel + "','" + puntuacion + "','" + blob + "')");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast toast = Toast.makeText(getApplicationContext(), getText(R.string.guardado), Toast.LENGTH_SHORT);
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
                        intent.putExtra(Intent.EXTRA_TEXT, getText(R.string.contenido) + String.valueOf(puntuacion));
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


                Toast toast = Toast.makeText(getApplicationContext(), getText(R.string.compartido), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        Intent hacerFotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        buttonFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hacerFotoIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(hacerFotoIntent, ACTIVITY_FOTO);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), getText(R.string.error_camara), Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_FOTO && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream(20480);
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 0, baos);
            blob = baos.toByteArray();
        }
    }

    private void comprobarNombre(Editable text) throws Exception {
        if (text.toString().trim().isEmpty()) {
            throw new Exception(getString(R.string.errorNombre));
        }
    }
}