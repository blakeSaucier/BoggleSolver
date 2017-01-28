package io.neilharvey.bogglebuddy;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TrieDictionaryTests {

    private TrieDictionary dictionary;

    public TrieDictionaryTests() throws IOException {
        Context appContext = InstrumentationRegistry.getTargetContext();
        dictionary = new TrieDictionary();
        dictionary.loadWords(appContext);
    }

    @Test
    public void definedWordsAreRecognised() {

        assertTrue(dictionary.IsWord("zoosperm"));
    }

    @Test
    public void undefinedWordsAreNotRecognised() {

        assertFalse(dictionary.IsWord("abe"));
    }

    @Test
    public void validPrefixesAreRecognised() {

        assertTrue(dictionary.IsPrefix("zoospe"));
    }

    @Test
    public void invalidPrefixesAreNotRecognised() {

        assertFalse(dictionary.IsPrefix("xyz"));
    }
}
