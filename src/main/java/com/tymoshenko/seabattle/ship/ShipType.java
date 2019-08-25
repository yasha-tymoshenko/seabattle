package com.tymoshenko.seabattle.ship;

public enum ShipType {
    SUBMARINE(1, 4),
    DESTROYER(2, 3),
    CRUISER(3, 2),
    DREADNOUGHT(4, 1)
    ;

    private int deckCount;
    private int amountInFleet;

    ShipType(int deckCount, int amountInFleet) {
        this.deckCount = deckCount;
        this.amountInFleet = amountInFleet;
    }

    public int getDeckCount() {
        return deckCount;
    }

    public int getAmountInFleet() {
        return amountInFleet;
    }
}
