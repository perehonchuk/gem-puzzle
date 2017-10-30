package com.wix.gempuzzle.game;


import com.wix.gempuzzle.api.Board;
import com.wix.gempuzzle.api.GameStatus;
import com.wix.gempuzzle.api.Move;
import com.wix.gempuzzle.api.MoveResult;
import com.wix.gempuzzle.game.domain.*;
import com.wix.gempuzzle.game.exception.GameWasNotStartedException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultGameTest {
    BoardOperations boardOperations = mock(BoardOperations.class);
    Game game = new DefaultGame(boardOperations);

    BoardDto board = new BoardDto(new TileDto[0][0], 0, 0);

    @Test
    public void shouldCreateAndSaveNewBoard() throws Exception {
        when(boardOperations.createBoard()).thenReturn(board);

        Board createdBoard = game.startNewGame();

        assertThat(createdBoard).isEqualTo(board);
    }

    @Test(expected = GameWasNotStartedException.class)
    public void shouldThrowExceptionIfGameWasNotStarted() throws Exception {
        game.handleMove(Move.UP);
    }

    @Test
    public void shouldNotMoveAndReturnBadActionIfUserMadeWrongAction() throws Exception {
        TileDto[][] tiles = new TileDto[2][2];
        tiles[0][0] = new TileDto(0);
        tiles[1][0] = new TileDto(1);
        BoardDto board = new BoardDto(tiles, 0, 0);

        when(boardOperations.createBoard()).thenReturn(board);
        when(boardOperations.isMoveAllowed(Move.UP, board)).thenReturn(false);

        game.startNewGame();
        MoveResult moveResult = game.handleMove(Move.UP);

        assertThat(moveResult.status).isEqualTo(GameStatus.BAD_MOVE);
        assertThat(moveResult.board).isEqualTo(board);
    }

    @Test
    public void shouldReturnWonStatusIfBoardOperationsReturnsWonStatus() throws Exception {
        TileDto[][] tiles = new TileDto[2][2];
        tiles[0][0] = new TileDto(0);
        tiles[1][0] = new TileDto(1);
        BoardDto board = new BoardDto(tiles, 0, 0);

        TileDto[][] expectedTiles = new TileDto[2][2];
        expectedTiles[0][0] = new TileDto(1);
        expectedTiles[1][0] = new TileDto(0);
        BoardDto expectedBoard = new BoardDto(expectedTiles, 1, 0);

        when(boardOperations.createBoard()).thenReturn(board);
        when(boardOperations.isMoveAllowed(Move.DOWN, board)).thenReturn(true);
        when(boardOperations.move(Move.DOWN, board)).thenReturn(expectedBoard);
        when(boardOperations.isBoardWon(expectedBoard)).thenReturn(true);

        game.startNewGame();
        MoveResult moveResult = game.handleMove(Move.DOWN);

        assertThat(moveResult.status).isEqualTo(GameStatus.WON);
        assertThat(moveResult.board).isEqualTo(expectedBoard);
    }

    @Test
    public void shouldReturnOkStatusAndMoveTile() throws Exception {
        TileDto[][] tiles = new TileDto[2][2];
        tiles[0][0] = new TileDto(0);
        tiles[1][0] = new TileDto(1);
        BoardDto board = new BoardDto(tiles, 0, 0);

        TileDto[][] expectedTiles = new TileDto[2][2];
        expectedTiles[0][0] = new TileDto(1);
        expectedTiles[1][0] = new TileDto(0);
        BoardDto expectedBoard = new BoardDto(expectedTiles, 1, 0);

        when(boardOperations.createBoard()).thenReturn(board);
        when(boardOperations.isMoveAllowed(Move.DOWN, board)).thenReturn(true);
        when(boardOperations.move(Move.DOWN, board)).thenReturn(expectedBoard);
        when(boardOperations.isBoardWon(expectedBoard)).thenReturn(false);

        game.startNewGame();
        MoveResult moveResult = game.handleMove(Move.DOWN);

        assertThat(moveResult.status).isEqualTo(GameStatus.OK_MOVE);
        assertThat(moveResult.board).isEqualTo(expectedBoard);
    }
}