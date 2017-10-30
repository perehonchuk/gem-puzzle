package com.wix.gempuzzle.game.domain;

import com.wix.gempuzzle.api.Board;
import lombok.Value;

import java.util.Arrays;

@Value
public class BoardDto implements Board {
    private final TileDto[][] tiles;
    private final int zeroRow;
    private final int zeroColumn;

    public int getSize() {
        return tiles.length;
    }

    public TileDto getTile(int row, int column) {
        return tiles[row][column];
    }

    public TileDto getZeroTile() {
        return getTile(zeroRow, zeroColumn);
    }

    public TileDto[][] getTiles() {
        TileDto[][] copy = new TileDto[getSize()][getSize()];
        for (int i = 0; i < getSize(); i++) {
            copy[i] = Arrays.copyOf(tiles[i], getSize());
        }
        return copy;
    }
}
