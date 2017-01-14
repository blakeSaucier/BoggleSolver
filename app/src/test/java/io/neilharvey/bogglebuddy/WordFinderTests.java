package io.neilharvey.bogglebuddy;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

public class WordFinderTests {

    @Test
    public void findsAllStringPermutationsInBoard() {
        Board board = Board.fromString("ABCD");
        Dictionary dictionary = new AllStringsAreWordsDictionary();
        WordFinder finder = new WordFinder(dictionary);
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

        String[] results = finder.findWords(board);

        Arrays.sort(expected);
        Arrays.sort(results);
        assertArrayEquals(expected, results);
    }

//    @Test
//    public void onlyDistinctStringsAreReturned() {
//    }
//
//    @Test
//    public void onlyStringsLongerThanMinLengthAreReturned() {;
//    }
//
//    @Test
//    public void onlyWordsInDictionaryAreReturned() {
//
//    }

    private class AllStringsAreWordsDictionary implements Dictionary {

        @Override
        public boolean IsWord(String word) {
            return true;
        }
    }
}