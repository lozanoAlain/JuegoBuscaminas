package com.example.juegobuscaminasgrupojai;

public class RankingTable {

    private String nombre;
    private int tiempo;
    private String nivel;

    public RankingTable(String nombre, String nivel, int tiempo) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.tiempo = tiempo;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
}
