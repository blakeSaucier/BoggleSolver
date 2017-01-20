package io.neilharvey.bogglebuddy;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.view.textservice.TextServicesManager;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class SpellCheckerDictionaryTests {

    @Test
    public void wordsAreRecognised() {

        TextServicesManager textServicesManager = getTextServicesManager();
        SpellCheckerDictionary dictionary = new SpellCheckerDictionary(textServicesManager);

        assertTrue(dictionary.IsWord("dog"));
    }

    private TextServicesManager getTextServicesManager() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        return (TextServicesManager) appContext.getSystemService(Context.TEXT_SERVICES_MANAGER_SERVICE);
    }

}
