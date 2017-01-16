package io.neilharvey.bogglebuddy;

import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SpellCheckerSession;
import android.view.textservice.SpellCheckerSession.SpellCheckerSessionListener;
import android.view.textservice.SuggestionsInfo;
import android.view.textservice.TextInfo;
import android.view.textservice.TextServicesManager;

public class SpellCheckerDictionary implements Dictionary, SpellCheckerSessionListener {

    private final TextServicesManager textServicesManager;
    private SpellCheckerSession session;
    private boolean result;

    public SpellCheckerDictionary(TextServicesManager textServicesManager) {
        this.textServicesManager = textServicesManager;
    }

    @Override
    public boolean IsWord(String word) {

        boolean result = false;
        session = textServicesManager.newSpellCheckerSession(null, null, this, true);
        TextInfo info = new TextInfo(word);
        session.getSentenceSuggestions(new TextInfo[]{info}, 1);
        return result;
    }

    @Override
    public void onGetSuggestions(SuggestionsInfo[] results) {

    }

    @Override
    public void onGetSentenceSuggestions(SentenceSuggestionsInfo[] results) {
        String a = "";
    }
}
