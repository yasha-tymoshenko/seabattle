package com.tymoshenko.seabattle.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
public class BoardCell {

    private final Coordinate coordinate;
    @EqualsAndHashCode.Exclude
    private BoardCellType type;

}
