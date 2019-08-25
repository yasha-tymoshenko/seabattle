package com.tymoshenko.seabattle.ship;

import com.tymoshenko.seabattle.ship.Ship;
import com.tymoshenko.seabattle.ship.ShipType;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Fleet {
    private Map<ShipType, List<Ship>> shipsByType;

    public Fleet() {
        shipsByType = new EnumMap<>(ShipType.class);
    }


}
