package com.wix.gempuzzle.game;

import com.wix.gempuzzle.client.api.GameStatus;
import com.wix.gempuzzle.client.api.Move;
import com.wix.gempuzzle.client.api.MoveResult;
import com.wix.gempuzzle.game.domain.*;
import com.wix.gempuzzle.game.exception.GameWasNotStartedException;

public class DefaultGame implements Game {
    private final BoardOperations boardOperations;
    private BoardDto board;

    public DefaultGame(BoardOperations boardOperations) {
        this.boardOperations = boardOperations;
    }

    @Override
    public BoardDto startNewGame() {
        this.board = boardOperations.createBoard();
        return this.board;
    }

    @Override
    public MoveResult handleMove(Move move) {
        throwExceptionIfBoardIsMissing();
        BoardDto newBoard = board;
        GameStatus gameStatus;
        if (boardOperations.isMoveAllowed(move, board)) {
            newBoard = boardOperations.move(move, board);
            if (boardOperations.isBoardWon(newBoard)) {
                gameStatus = GameStatus.WON;
            } else {
                gameStatus = GameStatus.OK_MOVE;
            }
        } else {
            gameStatus = GameStatus.BAD_MOVE;
        }
        this.board = newBoard;
        return new MoveResult(newBoard, gameStatus);
    }

    private void throwExceptionIfBoardIsMissing() {
        if (board == null) {
            throw new GameWasNotStartedException();
        }
    }
}
