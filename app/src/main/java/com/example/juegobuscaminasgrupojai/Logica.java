package com.example.juegobuscaminasgrupojai;

import java.lang.reflect.Array;

public class Logica {

    private static int[][] Minas;

    public static int[][] meterBombasyNumeros(int i) {
        Minas = new int[i][i];

        for (int x = 0; x < i; x++) {
            for (int y = 0; y < i; y++) {
                Minas[x][y] = 0;
            }
        }

        int minasColocadas = 0;

        while (minasColocadas < i) {
            int fila = (int) (Math.random() * (i - 1 - 0 + 1) + 0);
            int columna = (int) (Math.random() * (i - 1 - 0 + 1) + 0);
            if (Minas[fila][columna] == 9) {
                fila = (int) (Math.random() * (i - 1 - 0 + 1) + 0);
                columna = (int) (Math.random() * (i - 1 - 0 + 1) + 0);
            }
            Minas[fila][columna] = 9;
            minasColocadas++;
        }

        for (int x = 0; x < i; x++) {
            for (int y = 0; y < i; y++) {
                if (Minas[x][y] >= 9) {
                    if (y == 0) {
                        if (x == 0) {
                            Minas[x + 1][y]++;
                            Minas[x][y + 1]++;
                            Minas[x + 1][y + 1]++;
                        } else if (x == i - 1) {
                            Minas[x - 1][y]++;
                            Minas[x - 1][y + 1]++;
                            Minas[x][y + 1]++;

                        } else {
                            Minas[x - 1][y]++;
                            Minas[x + 1][y]++;
                            Minas[x - 1][y + 1]++;
                            Minas[x][y + 1]++;
                            Minas[x + 1][y + 1]++;

                        }
                    } else if (y == i - 1) {
                        if (x == i - 1) {
                            Minas[x - 1][y - 1]++;
                            Minas[x][y - 1]++;
                            Minas[x - 1][y]++;

                        } else if (x == 0) {
                            Minas[x][y - 1]++;
                            Minas[x + 1][y - 1]++;
                            Minas[x + 1][y]++;

                        } else {
                            Minas[x - 1][y - 1]++;
                            Minas[x][y - 1]++;
                            Minas[x + 1][y - 1]++;
                            Minas[x - 1][y]++;
                            Minas[x + 1][y]++;
                        }

                    } else if (x == 0) {
                        Minas[x][y - 1]++;
                        Minas[x + 1][y - 1]++;
                        Minas[x + 1][y]++;
                        Minas[x + 1][y + 1]++;
                        Minas[x][y + 1]++;

                    } else if (x == i - 1) {
                        Minas[x - 1][y - 1]++;
                        Minas[x][y - 1]++;
                        Minas[x - 1][y]++;
                        Minas[x - 1][y + 1]++;
                        Minas[x][y + 1]++;
                    } else {

                        Minas[x - 1][y - 1]++;
                        Minas[x][y - 1]++;
                        Minas[x + 1][y - 1]++;
                        Minas[x - 1][y]++;
                        Minas[x + 1][y]++;
                        Minas[x - 1][y + 1]++;
                        Minas[x][y + 1]++;
                        Minas[x + 1][y + 1]++;
                    }

                }
            }
        }
        return Minas;
    }




}

