package com.wix.gempuzzle.api;

public interface Board {
    int getSize();
    Tile getTile(int row, int column);
}
