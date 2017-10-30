package com.wix.gempuzzle.game;

import com.wix.gempuzzle.client.api.Move;
import com.wix.gempuzzle.game.domain.BoardDto;

public interface BoardOperations {
    BoardDto createBoard();
    boolean isBoardWon(BoardDto board);
    boolean isBoardSolvable(BoardDto board);
    BoardDto move(Move move, BoardDto board);
    boolean isMoveAllowed(Move move, BoardDto board);
}
