package com.wix.gempuzzle.client;

import com.wix.gempuzzle.api.Move;

import java.util.Optional;
import java.util.function.Supplier;

public class DefaultUserInput implements Supplier<Optional<Move>> {
    public static final String LEFT_ACTION_STR = "a";
    public static final String RIGHT_ACTION_STR = "d";
    public static final String DOWN_ACTION_STR = "s";
    public static final String UP_CONSTANT_STR = "w";

    private final Supplier<String> input;

    public DefaultUserInput(Supplier<String> input) {
        this.input = input;
    }

    @Override
    public Optional<Move> get() {
        String move = input.get();
        switch (move) {
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
