package com.tymoshenko.seabattle.ship;

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
