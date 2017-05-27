package io.neilharvey.bogglesolver;

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
