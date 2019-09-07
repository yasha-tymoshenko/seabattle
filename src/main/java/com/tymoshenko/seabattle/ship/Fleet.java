package com.tymoshenko.seabattle.ship;

import com.tymoshenko.seabattle.board.Coordinate;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public class Fleet {
    private Set<Ship> ships;

    public Fleet() {
        ships = new LinkedHashSet<>();
    }

    public void addShip(Ship ship) {
        ship.setBuilt(true);
        ships.add(ship);
    }

    public Optional<Ship> getShipByCoordinate(Coordinate coordinate) {
        return ships.stream()
                .filter(ship -> !ship.isDestroyed())
                .filter(ship -> ship.getCoordinates().contains(coordinate))
                .findFirst();
    }

    public boolean isDestroyed() {
        return ships.stream().allMatch(Ship::isDestroyed);
    }
}
