package com.example.juegobuscaminasgrupojai;

import android.content.Context;
import android.widget.ImageButton;

public class ImageButtonPosition extends androidx.appcompat.widget.AppCompatImageButton {

    public ImageButtonPosition(Context context) {
        super(context);
    }

    private int fila;
    private int columna;
    private boolean bandera;
    private boolean descubierta;

    public boolean isDescubierta() {
        return descubierta;
    }

    public void setDescubierta(boolean descubierta) {
        this.descubierta = descubierta;
    }

    public boolean isBandera() {
        return bandera;
    }

    public void setBandera(boolean bandera) {
        this.bandera = bandera;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

}
