package io.neilharvey.bogglebuddy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

public class WordFinder {

    private final TrieNode trie;
    private final int minWordLength;

    public WordFinder(TrieNode trie, int minWordLength) {
        this.trie = trie;
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

        TreeSet<Word> words = new TreeSet<>();
        int size = board.length;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Vector<Point> path = new Vector<>();
                path.add(new Point(i, j));
                TreeSet<Word> wordsFromHere = findWords(board, i, j, new boolean[size][size], trie, path);
                words.addAll(wordsFromHere);
            }
        }

        return words;
    }

    private TreeSet<Word> findWords(char[][] board, int i, int j, boolean[][] visited, TrieNode node, List<Point> path) {

        boolean[][] localVisited = WordFinder.copyOf(visited);
        localVisited[i][j] = true;
        char letter = board[i][j];
        TrieNode child = node.getChild(letter);
        TreeSet<Word> words = new TreeSet<>();

        if(child != null && letter == 'q') {
            child = child.getChild('u');
        }

        if (child == null) {
            return words;
        }

        if(child.isWord())
        {
            String currentWord = child.toString();
            if(currentWord.length() >= minWordLength) {
                words.add(new Word(currentWord, path));
            }
        }

        for (int row = i - 1; row <= i + 1 && row < board.length; row++) {
            for (int col = j - 1; col <= j + 1 && col < board.length; col++) {
                if (row >= 0 && col >= 0 && !localVisited[row][col]) {
                    Vector<Point> pathFromHere = new Vector<>(path);
                    pathFromHere.add(new Point(row, col));
                    TreeSet<Word> wordsFromHere = findWords(board, row, col, localVisited, child, pathFromHere);
                    words.addAll(wordsFromHere);
                }
            }
        }

        return words;
    }
}
