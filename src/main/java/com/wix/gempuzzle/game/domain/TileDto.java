package com.wix.gempuzzle.game.domain;

import com.wix.gempuzzle.client.api.Tile;
import lombok.Value;

@Value
public class TileDto implements Tile {
    public final int value;
}
