package io.neilharvey.bogglebuddy;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TrieDictionary implements Dictionary {

    private TrieNode root;

    public TrieDictionary() {
        root = new TrieNode();
    }

    public void loadWords(Context context) throws IOException {
        final Resources resources = context.getResources();
        InputStream inputStream = resources.openRawResource(R.raw.wordlist);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String word;
            while ((word = reader.readLine()) != null) {
                root.add(word);
            }
        } finally {
            reader.close();
        }
    }

    @Override
    public boolean IsWord(String word) {
        return root.contains(word, true);
    }

    @Override
    public boolean IsPrefix(String prefix) {
        return root.contains(prefix, false);
    }
}
