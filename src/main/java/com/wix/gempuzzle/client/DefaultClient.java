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
    public static final String LEFT_ACTION_STR = "a";
    public static final String RIGHT_ACTION_STR = "d";
    public static final String DOWN_ACTION_STR = "s";
    public static final String UP_CONSTANT_STR = "w";

    private final UI ui;
    private final Game game;
    private final Supplier<String> userInput;

    @Override
    public void playGame() {
        Board board = game.startNewGame();
        while (true) {
            ui.displayBoard(board);
            ui.displayInputMessage();
            Optional<Move> move = getUserMove();
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

    private Optional<Move> getUserMove() {
        String input = userInput.get();
        switch (input) {
            case LEFT_ACTION_STR:
                return Optional.of(Move.LEFT);
            case RIGHT_ACTION_STR:
                return Optional.of(Move.RIGHT);
            case DOWN_ACTION_STR:
                return Optional.of(Move.DOWN);
            case UP_CONSTANT_STR:
                return Optional.of(Move.UP);
            default:
                return Optional.empty();
        }
    }
}
