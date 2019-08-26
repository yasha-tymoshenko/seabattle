package com.tymoshenko.seabattle.board;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Coordinate {
    private final int x, y;
}
