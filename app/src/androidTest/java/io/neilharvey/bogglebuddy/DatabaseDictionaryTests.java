package io.neilharvey.bogglebuddy;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class DatabaseDictionaryTests {

    private DatabaseDictionary dictionary;

    public DatabaseDictionaryTests() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        dictionary = new DatabaseDictionary(appContext);
    }

    @Test
    public void definedWordsAreRecognised() {

        assertTrue(dictionary.IsWord("zoosperm"));
    }

    @Test
    public void undefinedWordsAreNotRecognised() {

        assertFalse(dictionary.IsWord("asdf"));
    }

    @Test
    public void validPrefixesAreRecognised() {

        assertTrue(dictionary.IsPrefix("zoo"));
    }

    @Test
    public void invalidPrefixesAreNotRecognised() {

        assertFalse(dictionary.IsPrefix("xyz"));
    }
}
