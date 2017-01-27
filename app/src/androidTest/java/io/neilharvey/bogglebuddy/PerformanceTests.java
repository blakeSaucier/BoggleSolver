package io.neilharvey.bogglebuddy;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class PerformanceTests {

    @Test(timeout=100000)
    public void findsWordsWithinTargetTime() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Dictionary dictionary = new DatabaseDictionary(appContext);
        WordFinder wordFinder = new WordFinder(dictionary, 3);
        char[][] board = new char[][] {
                {'A', 'B', 'C', 'D'},
                {'E', 'F', 'G', 'H'},
                {'I', 'J', 'K', 'L'},
                {'M', 'N', 'O', 'P'}
        };
        String[] words = wordFinder.findWords(board);
    }
}
