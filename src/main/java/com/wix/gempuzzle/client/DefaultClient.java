package com.wix.gempuzzle.client;

import com.wix.gempuzzle.api.Board;
import com.wix.gempuzzle.api.Move;
import com.wix.gempuzzle.api.MoveResult;
import com.wix.gempuzzle.client.ui.UI;
import com.wix.gempuzzle.game.Game;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.function.Supplier;

@AllArgsConstructor
public class DefaultClient implements Client {

    private final UI ui;
    private final Game game;
    private final Supplier<Optional<Move>> userInput;

    @Override
    public void playGame() {
        Board board = game.startNewGame();
        while (true) {
            ui.displayBoard(board);
            ui.displayInputMessage();
            Optional<Move> move = userInput.get();
            if (!move.isPresent()) {
                ui.displayUnknownMoveMessage();
                continue;
            }
            MoveResult moveResult = game.handleMove(move.get());
            switch (moveResult.status) {
                case WON:
                    ui.displayCongratulationMessage();
                    return;
                case BAD_MOVE:
                    ui.displayWrongMoveMessage();
                    break;
                case OK_MOVE:
                    board = moveResult.board;
                    break;
            }
        }
    }
}
