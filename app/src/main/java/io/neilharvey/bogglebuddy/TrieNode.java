package io.neilharvey.bogglebuddy;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class TrieNode {

    private TrieNode parent;
    private TrieNode[] children;
    private boolean isLeaf;
    private boolean isWord;
    private char letter;

    public TrieNode() {
        this.children = new TrieNode[26];
        isLeaf = true;
        isWord = false;
    }

    protected TrieNode(final char letter, TrieNode parent) {
        this();
        this.letter = letter;
        this.parent = parent;
    }

    public void add(String word) {
        isLeaf = false;
        char letter = word.charAt(0);
        int position = letter - 'a';

        if (children[position] == null) {
            children[position] = new TrieNode(letter, this);
        }

        if (word.length() > 1) {
            children[position].add(word.substring(1));
        } else {
            children[position].isWord = true;
        }
    }

//    public List<String> getWords() {
//        ArrayList<String> words = new ArrayList<>();
//
//        if (isWord) {
//            words.add(toString());
//        }
//
//        if (!isLeaf) {
//            for (int i = 0; i < children.length; i++) {
//                if (children[i] != null) {
//                    words.addAll(children[i].getWords());
//                }
//            }
//        }
//
//        return words;
//    }

//    public boolean contains(final String value, boolean isWord)  {
//
//        TrieNode node = this;
//        char[] letters = value.toCharArray();
//        int i = 0;
//
//        while (i < letters.length && node.children[letters[i] - 'a'] != null) {
//            node = node.children[letters[i] - 'a'];
//            i++;
//        }
//
//        return i == letters.length && (node.isWord || !isWord);
//    }

    public TrieNode getChild(final char value) {
        return this.children[value - 'a'];
    }

    public boolean isWord() {
        return this.isWord;
    }

    @Override
    public String toString() {
        if (parent == null) {
            return "";
        } else {
            return parent.toString() + new String(new char[]{letter});
        }
    }
}
