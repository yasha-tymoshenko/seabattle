package com.tymoshenko.seabattle.board;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public abstract class Board {
    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;
    static final Random RANDOM = new Random();

    Map<Coordinate, BoardCell> cellMap;
    Map<BoardCellType, List<BoardCell>> cellMapByType;

    public void init() {
        initCells();
    }

    @Override
    public String toString() {
        return printBoard();
    }

    String printBoard() {
        StringBuilder sb = new StringBuilder("\n");
        for (int y = 0; y < HEIGHT; y++) {
            sb.append(String.format("%2d", y)).append(" ");
            for (int x = 0; x < WIDTH; x++) {
                Coordinate coordinate = new Coordinate(x, y);
                BoardCell cell = cellMap.get(coordinate);
                sb.append(cell.getType()).append(" ");
            }
            sb.append("\n");
        }
        sb.append("   ");
        for (char ch = 'A'; ch < 'A' + WIDTH; ch++) {
            sb.append(ch).append(" ");
        }
        sb.append("\n");
        return sb.toString();
    }

    List<BoardCell> findBorderCells(Coordinate coordinate) {
        List<BoardCell> cells = new ArrayList<>();
        findEmptyCellRight(coordinate).ifPresent(cells::add);
        findEmptyCellDown(coordinate).ifPresent(cells::add);
        findEmptyCellLeft(coordinate).ifPresent(cells::add);
        findEmptyCellUp(coordinate).ifPresent(cells::add);
        findEmptyCellDiagonalUpRight(coordinate).ifPresent(cells::add);
        findEmptyCellDiagonalDownRight(coordinate).ifPresent(cells::add);
        findEmptyCellDiagonalDownLeft(coordinate).ifPresent(cells::add);
        findEmptyCellDiagonalUpLeft(coordinate).ifPresent(cells::add);
        return cells;
    }

    private Optional<BoardCell> findEmptyCellUp(Coordinate coordinate) {
        return findEmptyCell(new Coordinate(coordinate.getX(), coordinate.getY() - 1));
    }

    private Optional<BoardCell> findEmptyCellRight(Coordinate coordinate) {
        return findEmptyCell(new Coordinate(coordinate.getX() + 1, coordinate.getY()));
    }

    private Optional<BoardCell> findEmptyCellDown(Coordinate coordinate) {
        return findEmptyCell(new Coordinate(coordinate.getX(), coordinate.getY() + 1));
    }

    private Optional<BoardCell> findEmptyCellLeft(Coordinate coordinate) {
        return findEmptyCell(new Coordinate(coordinate.getX() - 1, coordinate.getY()));
    }

    private Optional<BoardCell> findEmptyCellDiagonalUpRight(Coordinate coordinate) {
        return findEmptyCell(new Coordinate(coordinate.getX() + 1, coordinate.getY() - 1));
    }

    private Optional<BoardCell> findEmptyCellDiagonalDownRight(Coordinate coordinate) {
        return findEmptyCell(new Coordinate(coordinate.getX() + 1, coordinate.getY() + 1));
    }

    private Optional<BoardCell> findEmptyCellDiagonalDownLeft(Coordinate coordinate) {
        return findEmptyCell(new Coordinate(coordinate.getX() - 1, coordinate.getY() + 1));
    }

    private Optional<BoardCell> findEmptyCellDiagonalUpLeft(Coordinate coordinate) {
        return findEmptyCell(new Coordinate(coordinate.getX() - 1, coordinate.getY() - 1));
    }

    private Optional<BoardCell> findEmptyCell(Coordinate coordinate) {
        Optional<BoardCell> optionalBoardCell = Optional.ofNullable(cellMap.get(coordinate));
        if (optionalBoardCell.isPresent()) {
            BoardCell boardCell = optionalBoardCell.get();
            if (boardCell.getType() != BoardCellType.EMPTY) {
                optionalBoardCell = Optional.empty();
            }
        }
        return optionalBoardCell;
    }

    private void initCells() {
        cellMap = new LinkedHashMap<>();
        cellMapByType = new EnumMap<>(BoardCellType.class);
        List<BoardCell> cells = new ArrayList<>();
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                Coordinate coordinate = new Coordinate(x, y);
                BoardCellType empty = BoardCellType.EMPTY;
                BoardCell cell = new BoardCell(coordinate, empty);
                cellMap.put(coordinate, cell);
                cells.add(cell);
                cellMapByType.put(empty, cells);
            }
        }
        for (BoardCellType cellType : BoardCellType.values()) {
            cellMapByType.putIfAbsent(cellType, new ArrayList<>());
        }
    }

}
