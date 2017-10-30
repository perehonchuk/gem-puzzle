package com.wix.gempuzzle.api;

public enum Move {
    LEFT(0, -1),
    RIGHT(0, 1),
    UP(-1, 0),
    DOWN(1, 0);

    public final int rowShift;
    public final int columnShift;

    Move(int rowShift, int columnShift) {
        this.rowShift = rowShift;
        this.columnShift = columnShift;
    }
}
