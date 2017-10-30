package com.wix.gempuzzle.game;

import com.wix.gempuzzle.api.Board;
import com.wix.gempuzzle.api.Move;
import com.wix.gempuzzle.game.domain.BoardDto;
import com.wix.gempuzzle.game.domain.TileDto;
import com.wix.gempuzzle.game.exception.MoveNotAllowedException;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

public class DefaultBoardOperationsTest {
    Function<List<Integer>, List<Integer>> shuffleFunction = mock(Function.class);
    BoardOperations boardOperations = new DefaultBoardOperations(shuffleFunction);

    @Test(timeout = 1000)
    public void shouldCreateNewBoard() throws Exception {
        List<Integer> shuffledResult = IntStream.range(1, 17).boxed().collect(toList());
        shuffledResult.set(15, 0);
        when(shuffleFunction.apply(anyList())).thenReturn(shuffledResult);

        BoardDto board = boardOperations.createBoard();

        assertThat(board).isEqualTo(wonBoard());
    }

    @Test(timeout = 1000)
    public void shouldCreateNewBoardWhileBoardIsNotSolvable() throws Exception {
        List<Integer> okResult = IntStream.range(1, 17).boxed().collect(toList());
        okResult.set(15, 0);
        List<Integer> badResult = IntStream.range(0, 16).boxed().collect(toList());
        when(shuffleFunction.apply(anyList())).thenReturn(badResult, okResult);

        BoardDto board = boardOperations.createBoard();

        assertThat(board).isEqualTo(wonBoard());

        verify(shuffleFunction, times(2)).apply(IntStream.range(0, 16).boxed().collect(toList()));
    }

    @Test
    public void shouldReturnFalseIfMoveIsNotAllowed() throws Exception {
        TileDto[][] tiles = new TileDto[1][1];
        tiles[0][0] = new TileDto(0);
        BoardDto board = new BoardDto(tiles, 0,0);

        assertThat(boardOperations.isMoveAllowed(Move.LEFT, board)).isFalse();
        assertThat(boardOperations.isMoveAllowed(Move.RIGHT, board)).isFalse();
        assertThat(boardOperations.isMoveAllowed(Move.UP, board)).isFalse();
        assertThat(boardOperations.isMoveAllowed(Move.DOWN, board)).isFalse();
    }

    @Test(expected = MoveNotAllowedException.class)
    public void shouldThrowExceptionIfMoveButMoveIsNotAllowed() throws Exception {
        TileDto[][] tiles = new TileDto[1][1];
        tiles[0][0] = new TileDto(0);
        BoardDto board = new BoardDto(tiles, 0,0);

        boardOperations.move(Move.LEFT, board);
    }

    @Test
    public void shouldReturnFalseIfMoveIsAllowed() throws Exception {
        assertThat(boardOperations.isMoveAllowed(Move.LEFT, oddSolvableBoard())).isTrue();
    }


    @Test
    public void shouldMoveZeroTileLeft() throws Exception {
        TileDto[][] tiles = new TileDto[2][2];
        tiles[0][0] = new TileDto(1);
        tiles[0][1] = new TileDto(0);
        BoardDto board = new BoardDto(tiles, 0, 1);

        TileDto[][] expectedTiles = new TileDto[2][2];
        expectedTiles[0][0] = new TileDto(0);
        expectedTiles[0][1] = new TileDto(1);
        BoardDto expectedBoard = new BoardDto(expectedTiles, 0, 0);

        Board result = boardOperations.move(Move.LEFT, board);

        assertThat(result).isEqualTo(expectedBoard);
    }

    @Test
    public void shouldMoveZeroTileRight() throws Exception {
        TileDto[][] tiles = new TileDto[2][2];
        tiles[0][0] = new TileDto(0);
        tiles[0][1] = new TileDto(1);
        BoardDto board = new BoardDto(tiles, 0, 0);

        TileDto[][] expectedTiles = new TileDto[2][2];
        expectedTiles[0][0] = new TileDto(1);
        expectedTiles[0][1] = new TileDto(0);
        BoardDto expectedBoard = new BoardDto(expectedTiles, 0, 1);

        Board result = boardOperations.move(Move.RIGHT, board);

        assertThat(result).isEqualTo(expectedBoard);
    }

    @Test
    public void shouldMoveZeroTileUp() throws Exception {
        TileDto[][] tiles = new TileDto[2][2];
        tiles[0][0] = new TileDto(1);
        tiles[1][0] = new TileDto(0);
        BoardDto board = new BoardDto(tiles, 1, 0);

        TileDto[][] expectedTiles = new TileDto[2][2];
        expectedTiles[0][0] = new TileDto(0);
        expectedTiles[1][0] = new TileDto(1);
        BoardDto expectedBoard = new BoardDto(expectedTiles, 0, 0);

        Board result = boardOperations.move(Move.UP, board);

        assertThat(result).isEqualTo(expectedBoard);
    }

    @Test
    public void shouldMoveZeroTileDown() throws Exception {
        TileDto[][] tiles = new TileDto[2][2];
        tiles[0][0] = new TileDto(0);
        tiles[1][0] = new TileDto(1);
        BoardDto board = new BoardDto(tiles, 0, 0);

        TileDto[][] expectedTiles = new TileDto[2][2];
        expectedTiles[0][0] = new TileDto(1);
        expectedTiles[1][0] = new TileDto(0);
        BoardDto expectedBoard = new BoardDto(expectedTiles, 1, 0);

        Board result = boardOperations.move(Move.DOWN, board);

        assertThat(result).isEqualTo(expectedBoard);
    }

    @Test
    public void shouldReturnTrueIfGameIsWon() throws Exception {
        assertThat(boardOperations.isBoardWon(wonBoard())).isTrue();
    }

    @Test
    public void shouldReturnFalseIfGameIsNotWon() throws Exception {
        BoardDto board = oddSolvableBoard();
        assertThat(boardOperations.isBoardWon(board)).isFalse();
    }

    @Test
    public void shouldReturnTrueIfGameIsSolvableAndZeroIsOdd() throws Exception {
        assertThat(boardOperations.isBoardSolvable(oddSolvableBoard())).isTrue();
    }

    @Test
    public void shouldReturnTrueIfGameIsSolvableAndZeroIsEven() throws Exception {
        assertThat(boardOperations.isBoardSolvable(wonBoard())).isTrue();
    }

    @Test
    public void shouldReturnFalseIfGameIsNotSolvable() throws Exception {
        assertThat(boardOperations.isBoardSolvable(notSolvableBoard())).isFalse();
    }

    BoardDto oddSolvableBoard() {
        TileDto[][] tiles = new TileDto[4][4];
        tiles[0][0] = new TileDto(1);
        tiles[0][1] = new TileDto(2);
        tiles[0][2] = new TileDto(3);
        tiles[0][3] = new TileDto(4);
        tiles[1][0] = new TileDto(5);
        tiles[1][1] = new TileDto(6);
        tiles[1][2] = new TileDto(7);
        tiles[1][3] = new TileDto(8);
        tiles[2][0] = new TileDto(9);
        tiles[2][1] = new TileDto(10);
        tiles[2][2] = new TileDto(11);
        tiles[2][3] = new TileDto(0);
        tiles[3][0] = new TileDto(12);
        tiles[3][1] = new TileDto(13);
        tiles[3][2] = new TileDto(14);
        tiles[3][3] = new TileDto(5);
        return new BoardDto(tiles, 2,3);
    }

    BoardDto wonBoard() {
        TileDto[][] tiles = new TileDto[4][4];
        tiles[0][0] = new TileDto(1);
        tiles[0][1] = new TileDto(2);
        tiles[0][2] = new TileDto(3);
        tiles[0][3] = new TileDto(4);
        tiles[1][0] = new TileDto(5);
        tiles[1][1] = new TileDto(6);
        tiles[1][2] = new TileDto(7);
        tiles[1][3] = new TileDto(8);
        tiles[2][0] = new TileDto(9);
        tiles[2][1] = new TileDto(10);
        tiles[2][2] = new TileDto(11);
        tiles[2][3] = new TileDto(12);
        tiles[3][0] = new TileDto(13);
        tiles[3][1] = new TileDto(14);
        tiles[3][2] = new TileDto(15);
        tiles[3][3] = new TileDto(0);
        return new BoardDto(tiles, 3,3);
    }

    BoardDto notSolvableBoard() {
        TileDto[][] tiles = new TileDto[4][4];
        tiles[0][0] = new TileDto(3);
        tiles[0][1] = new TileDto(9);
        tiles[0][2] = new TileDto(1);
        tiles[0][3] = new TileDto(15);
        tiles[1][0] = new TileDto(14);
        tiles[1][1] = new TileDto(11);
        tiles[1][2] = new TileDto(4);
        tiles[1][3] = new TileDto(6);
        tiles[2][0] = new TileDto(13);
        tiles[2][1] = new TileDto(0);
        tiles[2][2] = new TileDto(10);
        tiles[2][3] = new TileDto(12);
        tiles[3][0] = new TileDto(2);
        tiles[3][1] = new TileDto(7);
        tiles[3][2] = new TileDto(8);
        tiles[3][3] = new TileDto(5);
        return new BoardDto(tiles, 2,1);
    }
}