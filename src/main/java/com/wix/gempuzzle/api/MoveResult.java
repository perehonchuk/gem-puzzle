package com.wix.gempuzzle.api;

import lombok.Value;

@Value
public class MoveResult {
    public final Board board;
    public final GameStatus status;
}
