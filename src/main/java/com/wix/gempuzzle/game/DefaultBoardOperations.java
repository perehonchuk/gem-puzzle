package com.wix.gempuzzle.game;

import com.wix.gempuzzle.client.api.Move;
import com.wix.gempuzzle.game.domain.BoardDto;
import com.wix.gempuzzle.game.domain.TileDto;
import com.wix.gempuzzle.game.exception.MoveNotAllowedException;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class DefaultBoardOperations implements BoardOperations {
    private final int boardSize = 4;
    private final BoardDto wonBoard;
    private final Function<List<Integer>, List<Integer>> shuffleFunction;

    public DefaultBoardOperations(Function<List<Integer>, List<Integer>> shuffleFunction) {
        this.shuffleFunction = shuffleFunction;
        wonBoard = createWonBoard();
    }

    @Override
    public BoardDto createBoard() {
        BoardDto board;
        do {
            int zeroRow = 0;
            int zeroColumn = 0;
            TileDto[][] tiles = new TileDto[boardSize][boardSize];
            List<Integer> tileValues = IntStream.range(0, boardSize * boardSize).boxed().collect(toList());
            tileValues = shuffleFunction.apply(tileValues);
            for (int i = 0, k = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++, k++) {
                    int n = tileValues.get(k);
                    if (n == 0) {
                        zeroRow = i;
                        zeroColumn = j;
                    }
                    tiles[i][j] = new TileDto(n);
                }
            }
            board = new BoardDto(tiles, zeroRow, zeroColumn);
        } while (!isBoardSolvable(board));
        return board;
    }

    @Override
    public boolean isBoardWon(BoardDto board) {
        return wonBoard.equals(board);
    }

    @Override
    //https://stackoverflow.com/questions/34570344/check-if-15-puzzle-is-solvable
    public boolean isBoardSolvable(BoardDto board) {
        int []tileValues = new int[boardSize * boardSize];
        for (int i = 0, k = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++, k++) {
                tileValues[k] = board.getTile(i, j).value;
            }
        }
        int parity = 0;
        for (int i = 0; i < tileValues.length; i++) {
            for (int j = i + 1; j < tileValues.length; j++) {
                if (tileValues[i] > tileValues[j] && tileValues[j] != board.getZeroTile().value) {
                    parity++;
                }
            }
        }

        if ((board.getZeroRow() + 1) % 2 == 0) {
            return parity % 2 == 0;
        } else {
            return parity % 2 != 0;
        }
    }

    @Override
    public BoardDto move(Move move, BoardDto board) {
        if (!isMoveAllowed(move, board)) {
            throw new MoveNotAllowedException();
        }
        TileDto[][] tiles = board.getTiles();
        int newZeroRow = computeNewZeroRow(move, board);
        int newZeroColumn = computeNewZeroColumn(move, board);
        TileDto tileToMove = board.getTile(newZeroRow, newZeroColumn);
        tiles[newZeroRow][newZeroColumn] = board.getZeroTile();
        tiles[board.getZeroRow()][board.getZeroColumn()] = tileToMove;
        return new BoardDto(tiles, newZeroRow, newZeroColumn);
    }

    @Override
    public boolean isMoveAllowed(Move move, BoardDto board) {
        int newZeroRow = computeNewZeroRow(move, board);
        int newZeroColumn = computeNewZeroColumn(move, board);
        return newZeroColumn >= 0 && newZeroColumn < board.getSize()
                && newZeroRow >= 0 && newZeroRow < board.getSize();
    }

    private int computeNewZeroColumn(Move move, BoardDto board) {
        return board.getZeroColumn() + move.columnShift;
    }

    private int computeNewZeroRow(Move move, BoardDto board) {
        return board.getZeroRow() + move.rowShift;
    }

    private BoardDto createWonBoard() {
        TileDto[][] tiles = new TileDto[boardSize][boardSize];
        for (int i = 0, k = 1; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++, k++) {
                tiles[i][j] = new TileDto(k);
            }
        }
        tiles[boardSize - 1][boardSize - 1] = new TileDto(0);
        return new BoardDto(tiles, boardSize - 1, boardSize - 1);
    }
}
