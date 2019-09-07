package com.tymoshenko.seabattle.ship;

import com.tymoshenko.seabattle.board.Coordinate;
import lombok.Data;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;

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
        this.decks = initDecks(type, orientation, firstDeckCoordinate);
        this.orientation = orientation;
        this.coordinates = Collections.unmodifiableSet(
                decks.stream().map(Deck::getCoordinate).collect(Collectors.toSet()));
        isBuilt = false;
        isDestroyed = false;
    }


    public Deck getFirstDeck() {
        return decks.get(0);
    }

    public Deck getLastDeck() {
        return decks.get(decks.size() - 1);
    }

    public void damage(Coordinate coordinate) {
        decks.stream()
                .filter(deck -> deck.getCoordinate().equals(coordinate))
                .findFirst()
                .ifPresent(deck -> deck.setHit(true));
    }

    public boolean isDestroyed() {
        return decks.stream().allMatch(Deck::isHit);
    }

    private List<Deck> initDecks(ShipType type, Orientation orientation, Coordinate firstDeckCoordinate) {
        List<Deck> decks = new ArrayList<>();
        for (int deckNumber = 0; deckNumber < type.getDeckCount(); deckNumber++) {
            addDeck(decks, firstDeckCoordinate, deckNumber, orientation);
        }
        return Collections.unmodifiableList(decks);
    }

    private void addDeck(List<Deck> decks, Coordinate firstDeckCoordinate, int deckNumber, Orientation orientation) {
        Coordinate coordinate;
        if (orientation == Orientation.HORIZONTAL) {
            coordinate = new Coordinate(firstDeckCoordinate.getX() + deckNumber, firstDeckCoordinate.getY());
        } else {
            coordinate = new Coordinate(firstDeckCoordinate.getX(), firstDeckCoordinate.getY() + deckNumber);
        }
        decks.add(new Deck(coordinate));
    }

}
