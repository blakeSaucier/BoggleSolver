package io.neilharvey.bogglebuddy;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TrieBuilder {

    public static TrieNode buildTrie(Context context) throws IOException {
        TrieNode root = new TrieNode();
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

        return root;
    }
}
