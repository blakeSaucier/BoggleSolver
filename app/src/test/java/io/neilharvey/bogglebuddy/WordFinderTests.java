package io.neilharvey.bogglebuddy;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

public class WordFinderTests {

    private static void assertItemsEqual(String[] expected, String[] results) {
        Arrays.sort(expected);
        Arrays.sort(results);
        assertArrayEquals(expected, results);
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

        String[] results = findAllWords(board, 1);

        assertItemsEqual(expected, results);
    }

    @Test
    public void onlyDistinctStringsAreReturned() {
        char[][] board = new char[][]{{'A', 'A'}, {'A', 'A'}};
        String[] expected = {"A", "AA", "AAA", "AAAA"};

        String[] results = findAllWords(board, 1);

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

        String[] results = findAllWords(board, 3);

        assertItemsEqual(expected, results);
    }

    @Test
    public void onlyWordsInDictionaryAreReturned() {
        char[][] board = new char[][]{{'A', 'B'}, {'C', 'D'}};
        String[] expected = {"BAD", "CAB", "CAD"};
        Dictionary dictionary = new ArrayDictionary("BAD", "CAB", "CAD", "ACE");
        WordFinder wordFinder = new WordFinder(dictionary, 1);

        String[] results = wordFinder.findWords(board);

        assertItemsEqual(expected, results);
    }

    private String[] findAllWords(char[][] board, int minLength) {
        Dictionary dictionary = new AlwaysTrueDictionary();
        WordFinder finder = new WordFinder(dictionary, minLength);
        return finder.findWords(board);
    }

    private class AlwaysTrueDictionary implements Dictionary {

        @Override
        public boolean IsWord(String word) {
            return true;
        }
    }

    private class ArrayDictionary implements Dictionary {

        private final String[] allowedWords;

        public ArrayDictionary(String... allowedWords) {
            this.allowedWords = allowedWords;
        }

        @Override
        public boolean IsWord(String word) {
            return Arrays.asList(allowedWords).contains(word);
        }
    }
}