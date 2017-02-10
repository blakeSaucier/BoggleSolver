package io.neilharvey.bogglebuddy;

public class Point {

    private final int row;
    private final int col;

    public Point(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public boolean equals(Object obj) {
        Point point = (Point) obj;
        return point.getRow() == this.getRow() && point.getCol() == this.getCol();
    }

    @Override
    public String toString() {
        return "[" + row + "][" + col + "]";
    }
}
