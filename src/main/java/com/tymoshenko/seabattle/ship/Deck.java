package com.tymoshenko.seabattle.ship;

import com.tymoshenko.seabattle.board.Coordinate;
import lombok.Data;

@Data
public class Deck {
    private final Coordinate coordinate;
    private boolean hit;

    Deck(Coordinate coordinate) {
        this.coordinate = coordinate;
        hit = false;
    }
}
