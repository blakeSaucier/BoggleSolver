package io.neilharvey.bogglesolver;

import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WordFinderTests {

    private static void assertWordsEqual(String[] expected, Set<Word> results) {
        assertEquals(expected.length, results.size());
        for (String string : expected) {
            Word word = new Word(string, null);
            assertTrue(word + " was not found in " + results, results.contains(word));
        }
    }

    @Test
    public void findsAllStringPermutationsInboard() {
        char[][] board = new char[][]{{'a', 'b'}, {'c', 'd'}};
        String[] expected = {
                "a", "b", "c", "d",
                "ab", "ac", "ad", "ba", "bc", "bd", "ca", "cb", "cd", "da", "db", "dc",
                "abc", "abd", "acb", "acd", "adb", "adc", "bac", "bad", "bca", "bcd", "bda", "bdc",
                "cab", "cad", "cba", "cbd", "cda", "cdb", "dab", "dac", "dba", "dbc", "dca", "dcb",
                "abcd", "abdc", "acbd", "acdb", "adbc", "adcb",
                "bacd", "badc", "bcad", "bcda", "bdac", "bdca",
                "cabd", "cadb", "cbad", "cbda", "cdab", "cdba",
                "dabc", "dacb", "dbac", "dbca", "dcab", "dcba"
        };

        Set<Word> results = findAllWords(board, 1);

        assertWordsEqual(expected, results);
    }

    @Test
    public void onlyDistinctStringsareReturned() {
        char[][] board = new char[][]{{'a', 'a'}, {'a', 'a'}};
        String[] expected = {"a", "aa", "aaa", "aaaa"};

        Set<Word> results = findAllWords(board, 1);

        assertWordsEqual(expected, results);
    }

    @Test
    public void onlyStringsLongerThanMinLengthareReturned() {
        char[][] board = new char[][]{{'a', 'b'}, {'c', 'd'}};
        String[] expected = {
                "abc", "abd", "acb", "acd", "adb", "adc", "bac", "bad", "bca", "bcd", "bda", "bdc",
                "cab", "cad", "cba", "cbd", "cda", "cdb", "dab", "dac", "dba", "dbc", "dca", "dcb",
                "abcd", "abdc", "acbd", "acdb", "adbc", "adcb",
                "bacd", "badc", "bcad", "bcda", "bdac", "bdca",
                "cabd", "cadb", "cbad", "cbda", "cdab", "cdba",
                "dabc", "dacb", "dbac", "dbca", "dcab", "dcba"
        };

        Set<Word> results = findAllWords(board, 3);

        assertWordsEqual(expected, results);
    }

    @Test
    public void onlyWordsInDictionaryAreReturned() {
        char[][] board = new char[][]{{'a', 'b'}, {'c', 'd'}};
        String[] expected = {"bad", "cab", "cad"};
        TrieNode trie = createVocabulary("bad", "cab", "cad", "ace");
        WordFinder wordFinder = new WordFinder(trie, 1);

        Set<Word> results = wordFinder.findWords(board);

        assertWordsEqual(expected, results);
    }

    @Test
    public void pathUsedToFindWordIsReturned() {
        char[][] board = new char[][]{{'a', 'b'}, {'c', 'd'}};
        TrieNode vocabulary = createVocabulary("bad");
        WordFinder wordFinder = new WordFinder(vocabulary, 3);

        Set<Word> results = wordFinder.findWords(board);

        assertEquals(1, results.size());
        List<Point> path = results.iterator().next().getPath();
        assertEquals(3, path.size());
        assertEquals(new Point(0, 1), path.get(0));
        assertEquals(new Point(0, 0), path.get(1));
        assertEquals(new Point(1, 1), path.get(2));
    }

    @Test
    public void qIsTreatedAsQu() {
        char[][] board = new char[][]{{'q','e'},{'e','n'}};
        TrieNode vocabulary = createVocabulary("queen");
        WordFinder wordFinder = new WordFinder(vocabulary, 3);

        Set<Word> results = wordFinder.findWords(board);

        assertWordsEqual(new String[] {"queen"}, results);
    }

    @Test
    public void wordsAreReturnedInAlphabeticalOrder() {
        char[][] board = new char[][] {{'d','c'},{'b','a'}};
        TrieNode vocabulary = createVocabulary("bad", "cab", "cad", "abcd");
        WordFinder wordFinder = new WordFinder(vocabulary, 3);

        Set<Word> results = wordFinder.findWords(board);

        Iterator<Word> it = results.iterator();
        assertEquals("abcd", it.next().getWord());
        assertEquals("bad", it.next().getWord());
        assertEquals("cab", it.next().getWord());
        assertEquals("cad", it.next().getWord());
    }

    private Set<Word> findAllWords(char[][] board, int minLength) {
        TrieNode vocabulary = new GreedyTrie();
        WordFinder finder = new WordFinder(vocabulary, minLength);
        return finder.findWords(board);
    }

    private TrieNode createVocabulary(String... words) {
        TrieNode root = new TrieNode();
        for (String word : words) {
            root.add(word);
        }
        return root;
    }

    private class GreedyTrie extends TrieNode
    {
        public GreedyTrie()
        {
            super();
        }

        public GreedyTrie(char letter, GreedyTrie parent) {
            super(letter, parent);
        }

        @Override
        public boolean isWord() {
            return true;
        }

        @Override
        public TrieNode getChild(char value) {
            return new GreedyTrie(value, this);
        }
    }
}