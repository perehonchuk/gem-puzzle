package com.wix.gempuzzle.client.ui;

import com.wix.gempuzzle.client.api.Board;

public class ConsoleUI implements UI {
    @Override
    public void displayBoard(Board board) {
        for (int i = 0; i < board.getSize(); i++) {
            System.out.print("[");
            for (int j = 0; j < board.getSize(); j++) {
                System.out.print(" " + board.getTile(i, j).getValue());
            }
            System.out.println(" ]");
        }
    }

    @Override
    public void displayCongratulationMessage() {
        System.out.println("You won!!!");
    }

    @Override
    public void displayInputMessage() {
        System.out.print("Choose where to move: a = ←, w = ↑, d = →, s = ↓: ");
    }

    @Override
    public void displayWrongMoveMessage() {
        System.out.println("Can't perform this move");
    }

    @Override
    public void displayUnknownMoveMessage() {
        System.out.println("Unknown move, supported actions is a = ←, w = ↑, d = →, s = ↓: ");
    }
}
