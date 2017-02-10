package io.neilharvey.bogglebuddy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

public class WordFinder {

    private final Vocabulary vocabulary;
    private final int minWordLength;

    public WordFinder(Vocabulary vocabulary, int minWordLength) {

        this.vocabulary = vocabulary;
        this.minWordLength = minWordLength;
    }

    private static boolean[][] copyOf(boolean[][] original) {
        boolean[][] copy = new boolean[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = Arrays.copyOf(original[i], original.length);
        }
        return copy;
    }

    public Set<Word> findWords(char[][] board) {

        HashSet<Word> words = new HashSet<>();
        int size = board.length;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Vector<Point> path = new Vector<>();
                path.add(new Point(i, j));
                HashSet<Word> wordsFromHere = findWordsFrom(board, i, j, new boolean[size][size], "", path);
                words.addAll(wordsFromHere);
            }
        }

        return words;
    }

    private HashSet<Word> findWordsFrom(char[][] board, int i, int j, boolean[][] visited, String currentWord, List<Point> path) {

        boolean[][] localVisited = WordFinder.copyOf(visited);
        localVisited[i][j] = true;
        currentWord += board[i][j];

        HashSet<Word> words = new HashSet<>();

        if (!vocabulary.IsPrefix(currentWord)) {
            return words;
        }

        if (currentWord.length() >= minWordLength && vocabulary.IsWord(currentWord)) {
            words.add(new Word(currentWord, path));
        }

        for (int row = i - 1; row <= i + 1 && row < board.length; row++) {
            for (int col = j - 1; col <= j + 1 && col < board.length; col++) {
                if (row >= 0 && col >= 0 && !localVisited[row][col]) {
                    Vector<Point> pathFromHere = new Vector<>(path);
                    pathFromHere.add(new Point(row, col));
                    HashSet<Word> wordsFromHere = findWordsFrom(board, row, col, localVisited, currentWord, pathFromHere);
                    words.addAll(wordsFromHere);
                }
            }
        }

        return words;
    }
}
