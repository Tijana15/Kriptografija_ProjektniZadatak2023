package org.unibl.etf.projektnikriptografija.algorithms;

public class RailFence {
    private String plain;
    private String cipher;
    private int rows;

    public RailFence() {
        this.plain = "";
        this.cipher = "";
        this.rows = 0;
    }

    public RailFence(String plain, int rows) {
        this.plain = plain.replaceAll("\\s+", ""); // Uklanjanje razmaka
        this.cipher = "";
        this.rows = rows;
    }

    public String encrypt() {
        if (this.rows == 1) {
            return this.plain;
        }

        char[][] rail = new char[this.rows][this.plain.length()];

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.plain.length(); j++) {
                rail[i][j] = '\n';
            }
        }

        boolean dirDown = false;
        int row = 0, col = 0;

        for (int i = 0; i < this.plain.length(); i++) {
            //promjena kad se dostigne granica
            if (row == 0 || row == this.rows - 1) {
                dirDown = !dirDown;
            }

            rail[row][col++] = this.plain.charAt(i);

            if (dirDown) {
                row++;
            } else {
                row--;
            }
        }

        // šifrat
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.plain.length(); j++) {
                if (rail[i][j] != '\n') {
                    result.append(rail[i][j]);
                }
            }
        }

        this.cipher = result.toString();
        return this.cipher;
    }
}
