package com.wix.gempuzzle.client.api;

public interface Board {
    int getSize();
    Tile getTile(int row, int column);
}
