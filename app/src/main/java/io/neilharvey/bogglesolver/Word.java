package io.neilharvey.bogglesolver;

import java.util.List;

public class Word implements Comparable<Word> {

    private final String word;
    private final List<Point> path;

    public Word(String word, List<Point> path) {
        this.word = word;
        this.path = path;
    }

    public String getWord() {
        return word;
    }

    public List<Point> getPath() {
        return path;
    }

    @Override
    public String toString() {
        return word;
    }

    @Override
    public boolean equals(Object obj) {
        return word.equals(obj.toString());
    }

    @Override
    public int hashCode() {
        return word.hashCode();
    }

    @Override
    public int compareTo(Word word) {
        return getWord().compareTo(word.getWord());
    }
}
