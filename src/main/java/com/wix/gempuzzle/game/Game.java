package com.wix.gempuzzle.game;

import com.wix.gempuzzle.client.api.Board;
import com.wix.gempuzzle.client.api.Move;
import com.wix.gempuzzle.client.api.MoveResult;
import com.wix.gempuzzle.game.domain.BoardDto;

public interface Game {
    Board startNewGame();
    MoveResult handleMove(Move move);
}
