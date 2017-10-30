package com.wix.gempuzzle.game;

import com.wix.gempuzzle.api.Board;
import com.wix.gempuzzle.api.Move;
import com.wix.gempuzzle.api.MoveResult;

public interface Game {
    Board startNewGame();
    MoveResult handleMove(Move move);
}
