package io.neilharvey.bogglebuddy;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WordFinderTests {

    private static void assertItemsEqual(String[] expected, Set<Word> results) {
        assertEquals(expected.length, results.size());
        for (String string : expected) {
            Word word = new Word(string, null);
            assertTrue(word + " was not found in " + results, results.contains(word));
        }
    }

    @Test
    public void findsAllStringPermutationsInBoard() {
        char[][] board = new char[][]{{'A', 'B'}, {'C', 'D'}};
        String[] expected = {
                "A", "B", "C", "D",
                "AB", "AC", "AD", "BA", "BC", "BD", "CA", "CB", "CD", "DA", "DB", "DC",
                "ABC", "ABD", "ACB", "ACD", "ADB", "ADC", "BAC", "BAD", "BCA", "BCD", "BDA", "BDC",
                "CAB", "CAD", "CBA", "CBD", "CDA", "CDB", "DAB", "DAC", "DBA", "DBC", "DCA", "DCB",
                "ABCD", "ABDC", "ACBD", "ACDB", "ADBC", "ADCB",
                "BACD", "BADC", "BCAD", "BCDA", "BDAC", "BDCA",
                "CABD", "CADB", "CBAD", "CBDA", "CDAB", "CDBA",
                "DABC", "DACB", "DBAC", "DBCA", "DCAB", "DCBA"
        };

        Set<Word> results = findAllWords(board, 1);

        assertItemsEqual(expected, results);
    }

    @Test
    public void onlyDistinctStringsAreReturned() {
        char[][] board = new char[][]{{'A', 'A'}, {'A', 'A'}};
        String[] expected = {"A", "AA", "AAA", "AAAA"};

        Set<Word> results = findAllWords(board, 1);

        assertItemsEqual(expected, results);
    }

    @Test
    public void onlyStringsLongerThanMinLengthAreReturned() {
        char[][] board = new char[][]{{'A', 'B'}, {'C', 'D'}};
        String[] expected = {
                "ABC", "ABD", "ACB", "ACD", "ADB", "ADC", "BAC", "BAD", "BCA", "BCD", "BDA", "BDC",
                "CAB", "CAD", "CBA", "CBD", "CDA", "CDB", "DAB", "DAC", "DBA", "DBC", "DCA", "DCB",
                "ABCD", "ABDC", "ACBD", "ACDB", "ADBC", "ADCB",
                "BACD", "BADC", "BCAD", "BCDA", "BDAC", "BDCA",
                "CABD", "CADB", "CBAD", "CBDA", "CDAB", "CDBA",
                "DABC", "DACB", "DBAC", "DBCA", "DCAB", "DCBA"
        };

        Set<Word> results = findAllWords(board, 3);

        assertItemsEqual(expected, results);
    }

    @Test
    public void onlyWordsInDictionaryAreReturned() {
        char[][] board = new char[][]{{'A', 'B'}, {'C', 'D'}};
        String[] expected = {"BAD", "CAB", "CAD"};
        Vocabulary vocabulary = new ArrayDictionary("BAD", "CAB", "CAD", "ACE");
        WordFinder wordFinder = new WordFinder(vocabulary, 1);

        Set<Word> results = wordFinder.findWords(board);

        assertItemsEqual(expected, results);
    }

    @Test
    public void pathUsedToFindWordIsReturned() {
        char[][] board = new char[][]{{'A', 'B'}, {'C', 'D'}};
        Vocabulary vocabulary = new ArrayDictionary("BAD");
        WordFinder wordFinder = new WordFinder(vocabulary, 3);

        Set<Word> results = wordFinder.findWords(board);

        assertEquals(1, results.size());
        List<Point> path = results.iterator().next().getPath();
        assertEquals(3, path.size());
        assertEquals(new Point(0, 1), path.get(0));
        assertEquals(new Point(0, 0), path.get(1));
        assertEquals(new Point(1, 1), path.get(2));
    }

    private Set<Word> findAllWords(char[][] board, int minLength) {
        Vocabulary vocabulary = new AlwaysTrueDictionary();
        WordFinder finder = new WordFinder(vocabulary, minLength);
        return finder.findWords(board);
    }

    private class AlwaysTrueDictionary implements Vocabulary {

        @Override
        public boolean IsWord(String word) {
            return true;
        }

        @Override
        public boolean IsPrefix(String prefix) {
            return true;
        }
    }

    private class ArrayDictionary implements Vocabulary {

        private final String[] allowedWords;

        public ArrayDictionary(String... allowedWords) {
            this.allowedWords = allowedWords;
        }

        @Override
        public boolean IsWord(String word) {
            return Arrays.asList(allowedWords).contains(word);
        }

        @Override
        public boolean IsPrefix(String prefix) {
            return true;
        }
    }
}