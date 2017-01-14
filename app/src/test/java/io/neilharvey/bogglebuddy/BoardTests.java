package io.neilharvey.bogglebuddy;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoardTests {

    @Test
    public void BoardCanBeCreatedFromSquareString() {
        Board board = Board.fromString("ABCD");

        assertEquals(2, board.size());
        assertEquals('A', board.getLetter(0, 0));
        assertEquals('B', board.getLetter(0, 1));
        assertEquals('C', board.getLetter(1, 0));
        assertEquals('D', board.getLetter(1,1));
    }
}
