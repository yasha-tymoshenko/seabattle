package com.tymoshenko.seabattle.ship;

import com.tymoshenko.seabattle.board.Coordinate;
import lombok.Data;
import lombok.ToString;

import java.util.*;

@Data
public class Ship {
    private final ShipType type;
    private final Orientation orientation;
    private final List<Deck> decks;
    @ToString.Exclude
    private final Set<Coordinate> coordinates;
    private boolean isBuilt;
    private boolean isDestroyed;

    public Ship(ShipType type, Orientation orientation, Coordinate firstDeckCoordinate) {
        this.type = type;
        List<Deck> decks = new ArrayList<>();
        for (int i = 0; i < type.getDeckCount(); i++) {
            Coordinate coordinate;
            if (orientation == Orientation.HORIZONTAL) {
                coordinate = new Coordinate(firstDeckCoordinate.getX() + i, firstDeckCoordinate.getY());
            } else {
                coordinate = new Coordinate(firstDeckCoordinate.getX(), firstDeckCoordinate.getY() + i);
            }
            decks.add(new Deck(coordinate));
        }
        this.decks = Collections.unmodifiableList(decks);
        this.orientation = orientation;
        Set<Coordinate> coordinates = new HashSet<>();
        for (Deck deck : decks) {
            coordinates.add(deck.getCoordinate());
        }
        this.coordinates = Collections.unmodifiableSet(coordinates);
        isBuilt = false;
        isDestroyed = false;
    }

    public Deck getFirstDeck() {
        return decks.get(0);
    }

    public Deck getLastDeck() {
        return decks.get(decks.size() - 1);
    }
}
