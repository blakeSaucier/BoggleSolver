package io.neilharvey.bogglebuddy;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WordFinderTests {

    @Test
    public void findsAllStringPermutationsInBoard() {
        Board board = Board.fromString("ABCD");
        Dictionary dictionary = new AllStringsAreWordsDictionary();
        WordFinder finder = new WordFinder(dictionary);

        List<String> results = finder.getWords(board, 4);

        assertEquals(32, results.size());
    }

    @Test
    public void onlyDistinctStringsAreReturned() {
        assertTrue(false);
    }

    @Test
    public void onlyStringsLongerThanMinLengthAreReturned() {
        assertTrue(false);
    }

    @Test
    public void onlyWordsInDictionaryAreReturned() {
        assertTrue(false);
    }

    private class AllStringsAreWordsDictionary implements Dictionary {

        @Override
        public boolean IsWord(String word) {
            return true;
        }
    }
}