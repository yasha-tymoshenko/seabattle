package com.tymoshenko.seabattle.board;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Board {
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
