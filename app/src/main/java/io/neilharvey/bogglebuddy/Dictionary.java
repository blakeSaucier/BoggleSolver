package io.neilharvey.bogglebuddy;

public interface Dictionary {

    boolean IsWord(String word);

    boolean IsPrefix(String prefix);
}
