package io.neilharvey.bogglebuddy;

public class Board {

    private int size;
    private char[][] letters;

    public Board(int size) {
        this.size = size;
        this.letters = new char[size][size];
    }

    public static Board fromString(String value) {
        int size = (int) Math.sqrt(value.length());
        Board board = new Board(size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board.setLetter(i, j, value.charAt(j + (size * i)));
            }
        }
        return board;
    }

    public int getSize() {
        return size;
    }

    public char getLetter(int x, int y) {
        return letters[x][y];
    }

    public void setLetter(int x, int y, char letter) {
        letters[x][y] = letter;
    }
}
