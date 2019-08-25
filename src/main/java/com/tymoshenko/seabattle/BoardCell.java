package com.tymoshenko.seabattle;

import lombok.Data;

import java.util.Objects;

@Data
public class BoardCell {

    private final Coordinate coordinate;
    private BoardCellType type;

    public BoardCell(Coordinate coordinate, BoardCellType type) {
        this.coordinate = coordinate;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardCell cell = (BoardCell) o;
        return coordinate.equals(cell.coordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinate);
    }
}
