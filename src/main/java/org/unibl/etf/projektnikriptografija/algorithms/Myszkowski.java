package org.unibl.etf.projektnikriptografija.algorithms;

import java.util.*;

public class Myszkowski {
    private final String plain;
    private String cipher;
    private final String key;

    public Myszkowski() {
        this.plain = "";
        this.cipher = "";
        this.key = "";
    }

    public Myszkowski(String plain, String key) {
        this.plain = plain.replaceAll("\\s+", "").toLowerCase();
        this.cipher = "";
        this.key = key.toLowerCase();
    }

    public String encrypt() {
        List<String> keyChars = new ArrayList<>();
        for (String s : this.key.split("")) {
            keyChars.add(s);
        }

        List<String> sortedKeyChars = new ArrayList<>(keyChars);
        Collections.sort(sortedKeyChars);

        int numCols = this.key.length();
        int numRows = (int) Math.ceil((double) this.plain.length() / numCols);

        // Kreiranje matrice
        char[][] matrix = new char[numRows][numCols];
        for (char[] row : matrix) {
            Arrays.fill(row, '\0');
        }

        // Popunjavanje matrice karakterima iz plain teksta
        for (int i = 0; i < this.plain.length(); i++) {
            matrix[i / numCols][i % numCols] = this.plain.charAt(i);
        }

        // Kreiranje šifrovanog teksta iz matrice
        StringBuilder result = new StringBuilder();
        for (String s : sortedKeyChars) {
            List<Integer> indexes = new ArrayList<>();
            int index = -1;
            while ((index = this.key.indexOf(s, index + 1)) != -1) {
                indexes.add(index);
            }

            for (int idx : indexes) {
                for (int i = 0; i < numRows; i++) {
                    if (matrix[i][idx] != '\0') {
                        result.append(matrix[i][idx]);
                    }
                }
            }
        }

        this.cipher = result.toString();
        return this.cipher;
    }
}