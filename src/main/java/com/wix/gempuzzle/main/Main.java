package com.wix.gempuzzle.main;

import com.wix.gempuzzle.client.Client;
import com.wix.gempuzzle.client.DefaultClient;
import com.wix.gempuzzle.game.*;
import com.wix.gempuzzle.client.ui.ConsoleUI;
import com.wix.gempuzzle.client.ui.UI;

import java.util.Scanner;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] argv) {
        UI ui = new ConsoleUI();
        CollectionsShuffler collectionsShuffler = new CollectionsShuffler();
        BoardOperations boardOperations = new DefaultBoardOperations(collectionsShuffler);
        Game game = new DefaultGame(boardOperations);
        Scanner sc = new Scanner(System.in);
        Supplier<String> userInput = sc::nextLine;
        Client client = new DefaultClient(ui, game, userInput);
        client.playGame();
    }
}
