package com.example.juegobuscaminasgrupojai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class Activity_Ranking2 extends AppCompatActivity {

    private static final int ACTIVITY_RESULT = 3;
    private Button btnBack = null;
    private TableLayout tableLayout;
    private SQLiteDatabase database = null;

    private String nombre;
    private String tiempo;
    private String nivel;
    private String modo;

    private final String DATABASE_NAME = "puntuaciondb";
    private final String USUARIODATA = "Nombre";
    private final String TIEMPODATA = "Tiempo";
    private final String NIVELDATA = "Nivel";
    private final String COLUMN_IMAGE = "Foto";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking2);
        tableLayout = findViewById(R.id.tableLayout);


        Bundle extras = getIntent().getExtras();
        nombre = extras.getString("NOMBRE");
        tiempo = extras.getString("TIEMPO");
        nivel = extras.getString("NIVEL");
        modo = extras.getString("MODO");

        database = openOrCreateDatabase("RankingDB", Context.MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS " + DATABASE_NAME + " (" + USUARIODATA + " VARCHAR, " + NIVELDATA + " VARCHAR, " + TIEMPODATA + " INTEGER " + " )");

        database.execSQL("INSERT INTO " + DATABASE_NAME + "(" + USUARIODATA + "," + NIVELDATA + "," + TIEMPODATA + ")" + "VALUES('" + nombre + "', '" + nivel + "','" + tiempo + "')");

        Cursor cursor = database.rawQuery("SELECT * FROM " + DATABASE_NAME + " WHERE " + NIVELDATA + "= '" + nivel + "'" + " ORDER BY " + TIEMPODATA + " DESC LIMIT 10", null);

        ArrayList<RankingTable> rankingTable = new ArrayList<>();
        while(cursor.moveToNext())
            rankingTable.add(new RankingTable(cursor.getString(0), cursor.getString(1), cursor.getInt(2)));
        cursor.close();

        for(int i = 0; i < rankingTable.size(); i++){
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            //ADD THE NAME COLUMN
            TextView nombreCol = new TextView(this);
            nombreCol.setText(rankingTable.get(i).getNombre());
            nombreCol.setGravity(Gravity.CENTER);
            //ADD THE COLUMN TO THE ROW
            row.addView(nombreCol);

            //ADD THE NIVEL COLUMN
            TextView nivelCol = new TextView(this);
            nivelCol.setGravity(Gravity.CENTER);
            nivelCol.setText(rankingTable.get(i).getNivel());
            //ADD THE NIVEL TO THE ROW
            row.addView(nivelCol);

            //ADD THE TIME COLUMN
            TextView timeCol = new TextView(this);
            timeCol.setGravity(Gravity.CENTER);
            timeCol.setText(String.valueOf(rankingTable.get(i).getTiempo()));
            //ADD THE SCORE TO THE ROW
            row.addView(timeCol);

            //ADD THE ROW TO THE TABLE
            tableLayout.addView(row);

            database.close();
        }

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Ranking2.this, Activity_resultado.class);
                startActivityForResult(intent, ACTIVITY_RESULT);
            }
        });
    }
}