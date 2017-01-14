package io.neilharvey.bogglebuddy;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

public class WordFinderTests {

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
        String[] expected = {"A", "AA", "AAA", "AAAA" };

        String[] results = findAllWords(board, 1);

        assertItemsEqual(expected, results);
    }

    @Test
    public void onlyStringsLongerThanMinLengthAreReturned() {
        char[][] board = new char[][] {{'A', 'B'},{'C','D'}};
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

//    @Test
//    public void onlyWordsInDictionaryAreReturned() {
//
//    }


    private static void assertItemsEqual(String[] expected, String[] results) {
        Arrays.sort(expected);
        Arrays.sort(results);
        assertArrayEquals(expected, results);
    }

    private String[] findAllWords(char[][] board, int minLength) {
        Dictionary dictionary = new AllStringsAreWordsDictionary();
        WordFinder finder = new WordFinder(dictionary, minLength);
        return finder.findWords(board);
    }

    private class AllStringsAreWordsDictionary implements Dictionary {

        @Override
        public boolean IsWord(String word) {
            return true;
        }
    }
}