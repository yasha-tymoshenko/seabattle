package com.tymoshenko.seabattle.ship;

import com.tymoshenko.seabattle.board.Coordinate;

import java.util.LinkedHashSet;
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

    public Ship getShipByCoordinate(Coordinate coordinate) {
        Ship result = null;
        for (Ship ship : ships) {
            if (ship.isDestroyed()) {
                continue;
            }
            if (ship.getCoordinates().contains(coordinate)) {
                result = ship;
                break;
            }
        }
        return result;
    }

    public boolean isDestroyed() {
        boolean destroyed = true;
        for (Ship ship : ships) {
            if (!ship.isDestroyed()) {
                destroyed = false;
                break;
            }
        }
        return destroyed;
    }
}
