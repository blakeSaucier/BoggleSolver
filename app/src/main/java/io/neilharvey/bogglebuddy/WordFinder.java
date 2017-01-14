package io.neilharvey.bogglebuddy;

import java.util.Arrays;
import java.util.Vector;

public class WordFinder {

    private Dictionary dictionary;

    public WordFinder(Dictionary dictionary) {

        this.dictionary = dictionary;
    }

    private static boolean[][] copyOf(boolean[][] original) {
        boolean[][] copy = new boolean[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = Arrays.copyOf(original[i], original.length);
        }
        return copy;
    }

    public String[] findWords(Board board) {

        Vector<String> words = new Vector<>();
        int size = board.size();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Vector<String> wordsFromHere = findWordsFrom(board, i, j, new boolean[size][size], "");
                words.addAll(wordsFromHere);
            }
        }

        return words.toArray(new String[]{});
    }

    private Vector<String> findWordsFrom(Board board, int i, int j, boolean[][] visited, String currentWord) {

        boolean[][] localVisited = WordFinder.copyOf(visited);
        localVisited[i][j] = true;
        currentWord += board.getLetter(i, j);

        Vector<String> words = new Vector<>();
        words.add(currentWord);

        for(int row=i-1; row<=i+1 && row < board.size(); row++) {
            for(int col=j-1; col<= j+1 && col < board.size(); col++) {
                if(row >= 0 && col >= 0 && !localVisited[row][col]) {
                    Vector<String> wordsFromHere = findWordsFrom(board, row, col, localVisited, currentWord);
                    words.addAll(wordsFromHere);
                }
            }
        }

        return words;
    }
}
