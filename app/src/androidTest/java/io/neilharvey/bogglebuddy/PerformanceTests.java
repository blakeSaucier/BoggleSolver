package io.neilharvey.bogglebuddy;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;

@RunWith(AndroidJUnit4.class)
public class PerformanceTests {

    private static void assertItemsEqual(String[] expected, Set<Word> results) {
        Assert.assertEquals(expected.length, results.size());
        for (String string : expected) {
            Word word = new Word(string, null);
            Assert.assertTrue(word + " was not found in " + results, results.contains(word));
        }
    }

    @Test(timeout = 5000)
    public void findsWordsWithinTargetTime() throws IOException {
        Context appContext = InstrumentationRegistry.getTargetContext();
        TrieNode vocabulary = TrieBuilder.buildTrie(appContext);
        WordFinder wordFinder = new WordFinder(vocabulary, 3);
        char[][] board = new char[][]{
                {'a', 'b', 'c', 'd'},
                {'e', 'f', 'g', 'h'},
                {'i', 'j', 'k', 'l'},
                {'m', 'n', 'o', 'p'}
        };
        Set<Word> words = wordFinder.findWords(board);
        String[] expected = {"knop", "fin", "ink", "jink", "pol", "fink", "jin", "nim",
                "glop", "kop", "plonk", "knife", "fino", "lop", "fie", "mink"};
        assertItemsEqual(expected, words);
    }
}
