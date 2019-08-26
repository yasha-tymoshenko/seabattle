package com.tymoshenko.seabattle.board;

public enum BoardCellType {
    EMPTY('~'),
    SHIP_BORDER_WATERS('.'),
    SHIP('S'),
    DAMAGED('x'),
    DESTROYED('X'),
    MISSED_HIT('*')
    ;

    private char sign;

    BoardCellType(char sign) {
        this.sign = sign;
    }

    public char getSign() {
        return sign;
    }


    @Override
    public String toString() {
        return String.valueOf(sign);
    }

    public static BoardCellType fromString(String s) {
        if (s == null || s.trim().equals("")) {
            throw new IllegalArgumentException("BoardCellType cannot be instantiated from given string.");
        }
        for (BoardCellType boardCellType : values()) {
            if (s.charAt(0) == boardCellType.sign) {
                return boardCellType;
            }
        }
        throw new IllegalArgumentException("No such enum constant: " + s);
    }
}
