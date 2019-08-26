package com.tymoshenko.seabattle.board;

import lombok.AllArgsConstructor;
import lombok.Value;


@Value
@AllArgsConstructor
public class Coordinate {
    private final int x, y;

    @Override
    public String toString() {
        char dx = (char) ('A' + x);
        int dy = y + 1;
        return String.format("(%s, %2d)", dx, dy);
    }
}
