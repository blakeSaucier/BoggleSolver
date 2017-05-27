# Boggle Solver

An Android app which finds all the valid words in a game of Boggle.

[Boggle](https://en.wikipedia.org/wiki/Boggle) is a game which involves locating possible words in a random 4x4 grid of letters.  Words can be made by constructing letters which are horizontally, vertically or diagonally adjacent.  

This application is a small toy which locates all the possible words in a given grid, which is useful for verifying player's answers or simply for fun at the end of a round to see what players missed.

<img src="https://github.com/neilharvey/boggle-solver/blob/master/Screenshot.png" height="303" width="184"/>

The algorithm works by performing a [depth-first search](https://en.wikipedia.org/wiki/Depth-first_search) of the letter grid and comparing each of the possibilities to a vocabulary of known words.  This is optimised by storing the vocabulary in a [Trie](https://en.wikipedia.org/wiki/Trie) - by navigating the prefix tree and keeping track of the current node as we perform the DFS we can abandon search branches which would not yield any word matches.
