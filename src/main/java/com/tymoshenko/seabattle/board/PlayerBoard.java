package com.tymoshenko.seabattle.board;

import com.tymoshenko.seabattle.exception.CantPlaceShipException;
import com.tymoshenko.seabattle.ship.Fleet;
import com.tymoshenko.seabattle.ship.Orientation;
import com.tymoshenko.seabattle.ship.Ship;
import com.tymoshenko.seabattle.ship.ShipType;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class PlayerBoard extends Board {
    private Fleet fleet;

    @Override
    public void init() {
        super.init();
        try {
            placeShips();
        } catch (CantPlaceShipException e) {
            log.error("Failed to init board.", e);
        }
    }

    public void placeShips() throws CantPlaceShipException {
        fleet = new Fleet();
        List<ShipType> shipTypes = Arrays.asList(ShipType.values());
        Collections.reverse(shipTypes);
        for (ShipType shipType : shipTypes) {
            for (int i = 0; i < shipType.getAmountInFleet(); i++) {
                try {
                    placeShipRandom(shipType);
                } catch (CantPlaceShipException e) {
                    log.warn("Can't place random ship.", e);
                    boolean placed = false;
                    for (int retry = 0; retry < 200; retry++) {
                        try {
                            placeShipRandom(shipType);
                            placed = true;
                        } catch (CantPlaceShipException ex) {
                            log.warn("Retry #{}.", retry, ex);
                            continue;
                        }
                        break;
                    }
                    if (!placed) {
                        throw new CantPlaceShipException(String.format(
                                "Can't place ship of type=[%s]. Max number of retries exceeded.", shipType));
                    }
                }
            }
        }
    }

    private void placeShipRandom(ShipType type) throws CantPlaceShipException {
        Orientation orientation = Orientation.random();
        Coordinate randomFirstDeckCoordinate = findRandomEmptyCell().getCoordinate();
        try {
            placeShip(type, orientation, randomFirstDeckCoordinate);
        } catch (CantPlaceShipException e) {
            log.warn("Can't place random ship.", e);
            placeShip(type, Orientation.invert(orientation), randomFirstDeckCoordinate);
        }
    }

    private void placeShip(ShipType type, Orientation orientation, Coordinate firstDeckCoordinate)
            throws CantPlaceShipException {
        Ship ship = new Ship(type, orientation, firstDeckCoordinate);
        if (!canPlaceShip(ship)) {
            throw new CantPlaceShipException(String.format("Cannot place ship: [%s].", ship));
        }
        List<BoardCell> emptyCells = cellMapByType.get(BoardCellType.EMPTY);
        List<BoardCell> shipCells = cellMapByType.get(BoardCellType.SHIP);
        List<BoardCell> borderWatersCells = cellMapByType.get(BoardCellType.SHIP_BORDER_WATERS);
        for (Coordinate shipCoordinate : ship.getCoordinates()) {
            // Draw Ship
            BoardCell shipCell = cellMap.get(shipCoordinate);
            shipCell.setType(BoardCellType.SHIP);
            shipCells.add(shipCell);
            borderWatersCells.remove(shipCell);
            emptyCells.remove(shipCell);
            // Draw border waters
            findBorderCells(shipCoordinate).forEach(boardCell -> {
                boardCell.setType(BoardCellType.SHIP_BORDER_WATERS);
                borderWatersCells.add(boardCell);
                emptyCells.remove(boardCell);
            });
        }
        fleet.addShip(ship);
        log.info("Ship placed: {}", ship);
        log.info("Board: \n{}", printBoard());
    }

    private boolean canPlaceShip(Ship ship) {
        return isWithinTheBoard(ship) && isNoConflicts(ship);
    }

    private boolean isWithinTheBoard(Ship ship) {
        return cellMap.containsKey(ship.getFirstDeck().getCoordinate()) &&
                cellMap.containsKey(ship.getLastDeck().getCoordinate());
    }

    private boolean isNoConflicts(Ship ship) {
        boolean conflict = false;
        Set<Coordinate> coordinates = ship.getCoordinates();
        for (Coordinate shipCoordinate : coordinates) {
            BoardCell plannedShipCell = cellMap.get(shipCoordinate);
            if (BoardCellType.EMPTY != plannedShipCell.getType()) {
                conflict = true;
                break;
            }
        }
        return !conflict;
    }

    private List<BoardCell> findBorderCells(Coordinate coordinate) {
        List<BoardCell> cells = new ArrayList<>();
        findEmptyCellRight(coordinate).ifPresent(cells::add);
        findEmptyCellDown(coordinate).ifPresent(cells::add);
        findEmptyCellLeft(coordinate).ifPresent(cells::add);
        findEmptyCellUp(coordinate).ifPresent(cells::add);
        findEmptyCellDiagonalUpRight(coordinate).ifPresent(cells::add);
        findEmptyCellDiagonalDownRight(coordinate).ifPresent(cells::add);
        findEmptyCellDiagonalDownLeft(coordinate).ifPresent(cells::add);
        findEmptyCellDiagonalUpLeft(coordinate).ifPresent(cells::add);
        return cells;
    }

    private Optional<BoardCell> findEmptyCellUp(Coordinate coordinate) {
        return findEmptyCell(new Coordinate(coordinate.getX(), coordinate.getY() - 1));
    }

    private Optional<BoardCell> findEmptyCellRight(Coordinate coordinate) {
        return findEmptyCell(new Coordinate(coordinate.getX() + 1, coordinate.getY()));
    }

    private Optional<BoardCell> findEmptyCellDown(Coordinate coordinate) {
        return findEmptyCell(new Coordinate(coordinate.getX(), coordinate.getY() + 1));
    }

    private Optional<BoardCell> findEmptyCellLeft(Coordinate coordinate) {
        return findEmptyCell(new Coordinate(coordinate.getX() - 1, coordinate.getY()));
    }

    private Optional<BoardCell> findEmptyCellDiagonalUpRight(Coordinate coordinate) {
        return findEmptyCell(new Coordinate(coordinate.getX() + 1, coordinate.getY() - 1));
    }

    private Optional<BoardCell> findEmptyCellDiagonalDownRight(Coordinate coordinate) {
        return findEmptyCell(new Coordinate(coordinate.getX() + 1, coordinate.getY() + 1));
    }

    private Optional<BoardCell> findEmptyCellDiagonalDownLeft(Coordinate coordinate) {
        return findEmptyCell(new Coordinate(coordinate.getX() - 1, coordinate.getY() + 1));
    }

    private Optional<BoardCell> findEmptyCellDiagonalUpLeft(Coordinate coordinate) {
        return findEmptyCell(new Coordinate(coordinate.getX() - 1, coordinate.getY() - 1));
    }

    private Optional<BoardCell> findEmptyCell(Coordinate coordinate) {
        Optional<BoardCell> optionalBoardCell = Optional.ofNullable(cellMap.get(coordinate));
        if (optionalBoardCell.isPresent()) {
            BoardCell boardCell = optionalBoardCell.get();
            if (boardCell.getType() != BoardCellType.EMPTY) {
                optionalBoardCell = Optional.empty();
            }
        }
        return optionalBoardCell;
    }

    private BoardCell findRandomEmptyCell() {
        List<BoardCell> emptyCells = cellMapByType.get(BoardCellType.EMPTY);
        return emptyCells.get(RANDOM.nextInt(emptyCells.size()));
    }
}