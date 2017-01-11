package io.neilharvey.bogglebuddy;

import java.util.List;
import java.util.Vector;

public class WordFinder {

    public WordFinder(Dictionary dictionary) {

    }

    public List<String> getWords(Board board, int minLength) {
        Vector<String> words = new Vector<String>();
        String word = "" + board.getLetter(0,0);
        words.add(word);
        return words;
    }

}
