package org.unibl.etf.projektnikriptografija.algorithms;

public class PlayFair {

    private final char[][] matrix = new char[5][5];
    private static final int SHIFT_UPPERCASE = 32;

    public PlayFair(String key) {
        key = key.replaceAll("\\s", "").replaceAll("j", "i").replaceAll("J", "I");
        String keyWithoutDuplicates = removeDuplicates(key);

        int row = 0, column = 0;
        for (int i = 0; i < keyWithoutDuplicates.length(); i++) {
            matrix[row][column++] = keyWithoutDuplicates.charAt(i);
            if (column == 5) {
                column = 0;
                row++;
            }
        }

        for (char c = 'a'; c <= 'z'; c++) {
            if (c != 'j') {
                if (keyWithoutDuplicates.indexOf(c) == -1 && keyWithoutDuplicates.indexOf(c - SHIFT_UPPERCASE) == -1) {
                    if (keyWithoutDuplicates.indexOf(c) == -1) {
                        matrix[row][column++] = (char) (c - SHIFT_UPPERCASE);
                    } else {
                        matrix[row][column++] = c;
                    }
                    if (column == 5) {
                        column = 0;
                        row++;
                    }
                }
            }
        }
    }

    public String encode(String text) {
        StringBuilder cipher = new StringBuilder();
        text = text.replaceAll("\\s", "").replaceAll("j", "i").replaceAll("J", "I");
        text = addPadding(text, 'X');

        for (int i = 0; i < text.length(); i += 2) {
            char first = text.charAt(i);
            char second = text.charAt(i + 1);

            int[] posFirst = findPosition(first);
            int[] posSecond = findPosition(second);

            int row1 = posFirst[0];
            int col1 = posFirst[1];
            int row2 = posSecond[0];
            int col2 = posSecond[1];

            char encFirst;
            char encSecond;

            if (row1 == row2) {
                encFirst = matrix[row1][(col1 + 1) % 5];
                encSecond = matrix[row2][(col2 + 1) % 5];
            } else if (col1 == col2) {
                encFirst = matrix[(row1 + 1) % 5][col1];
                encSecond = matrix[(row2 + 1) % 5][col2];
            } else {
                encFirst = matrix[row1][col2];
                encSecond = matrix[row2][col1];
            }

            cipher.append(encFirst);
            cipher.append(encSecond);
        }

        return cipher.toString();
    }

    private int[] findPosition(char letter) {
        int[] pos = new int[2];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (matrix[i][j] == letter) {
                    pos[0] = i;
                    pos[1] = j;
                    return pos;
                }
            }
        }
        return pos;
    }

    private static String removeDuplicates(String input) {
        StringBuilder result = new StringBuilder();
        StringBuilder seenChars = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            char lowercaseChar = Character.toLowerCase(currentChar);

            if (seenChars.indexOf(String.valueOf(lowercaseChar)) == -1) {
                result.append(currentChar);
                seenChars.append(lowercaseChar);
            }
        }

        return result.toString();
    }

    private static String addPadding(String text, char padding) {
        StringBuilder textWithPadding = new StringBuilder(text);
        for (int i = 0; i < textWithPadding.length(); i++) {
            if (i % 2 == 0) {
                if (i + 1 == textWithPadding.length()) {
                    textWithPadding.append(padding);
                } else {
                    if (textWithPadding.charAt(i) == textWithPadding.charAt(i + 1)) {
                        textWithPadding.insert(i + 1, padding);
                    }
                }
            }
        }
        return textWithPadding.toString();
    }
}

