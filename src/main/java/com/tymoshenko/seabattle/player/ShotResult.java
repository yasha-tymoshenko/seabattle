package com.tymoshenko.seabattle.player;

import com.tymoshenko.seabattle.board.BoardCellType;
import com.tymoshenko.seabattle.board.Coordinate;
import com.tymoshenko.seabattle.ship.Ship;
import lombok.Value;

import java.util.Optional;

@Value
public class ShotResult {
    private final Coordinate coordinate;
    private final BoardCellType boardCellType;
    private Ship destroyedShip;

    public Optional<Ship> getDestroyedShip() {
        return Optional.ofNullable(destroyedShip);
    }
}
