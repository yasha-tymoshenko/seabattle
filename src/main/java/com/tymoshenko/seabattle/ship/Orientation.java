package com.tymoshenko.seabattle.ship;

import java.util.Random;

public enum Orientation {
    HORIZONTAL, VERTICAL;

    private static final Random RANDOM = new Random();

    public static Orientation random() {
        Orientation[] values = values();
        int randomIndex = RANDOM.nextInt(values.length);
        return values[randomIndex];
    }

    public static Orientation invert(Orientation orientation) {
        if (orientation == HORIZONTAL) {
            return VERTICAL;
        } else {
            return HORIZONTAL;
        }
    }
}
