package com.tymoshenko.seabattle.board;

import com.tymoshenko.seabattle.exception.CantPlaceShipException;
import com.tymoshenko.seabattle.exception.IllegalTargetException;
import com.tymoshenko.seabattle.player.ShotResult;
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

    public Fleet getFleet() {
        return fleet;
    }

    public ShotResult processEnemyShot(Coordinate targetCoordinate) {
        BoardCell targetBoardCell = cellMap.get(targetCoordinate);
        BoardCellType shotResultType;
        Ship destroyedShip = null;
        switch (targetBoardCell.getType()) {
            case EMPTY:
            case SHIP_BORDER_WATERS:
                shotResultType = BoardCellType.MISSED_HIT;
                drawMissed(targetBoardCell);
                break;
            case SHIP: {
                // Damaged or destroyed
                Ship shipUnderAttack = fleet.getShipByCoordinate(targetCoordinate);
                shipUnderAttack.damage(targetCoordinate);
                if (shipUnderAttack.isDestroyed()) {
                    shotResultType = BoardCellType.DESTROYED;
                    drawDamaged(targetBoardCell);
                    drawDestroyed(shipUnderAttack);
                    shipUnderAttack.setDestroyed(true);
                    destroyedShip = shipUnderAttack;
                } else {
                    shotResultType = BoardCellType.DAMAGED;
                    drawDamaged(targetBoardCell);
                }
            }
            break;
            default:
                throw new IllegalTargetException(String.format("Cannot target [%s] cell at [%s].",
                        targetBoardCell.getType(), targetBoardCell.getCoordinate()));
        }
        return new ShotResult(targetCoordinate, shotResultType, destroyedShip);
    }

    private void placeShips() throws CantPlaceShipException {
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
        boolean conflict;
        Set<Coordinate> coordinates = ship.getCoordinates();
        conflict = coordinates.stream()
                .map(shipCoordinate -> cellMap.get(shipCoordinate))
                .anyMatch(plannedShipCell -> BoardCellType.EMPTY != plannedShipCell.getType());
        return !conflict;
    }

    private BoardCell findRandomEmptyCell() {
        List<BoardCell> emptyCells = cellMapByType.get(BoardCellType.EMPTY);
        return emptyCells.get(RANDOM.nextInt(emptyCells.size()));
    }

    private void drawMissed(BoardCell targetBoardCell) {
        cellMapByType.get(BoardCellType.EMPTY).remove(targetBoardCell);
        cellMapByType.get(BoardCellType.SHIP_BORDER_WATERS).remove(targetBoardCell);
        targetBoardCell.setType(BoardCellType.MISSED_HIT);
        cellMapByType.get(BoardCellType.MISSED_HIT).add(targetBoardCell);
    }

    private void drawDamaged(BoardCell targetBoardCell) {
        cellMapByType.get(BoardCellType.SHIP).remove(targetBoardCell);
        targetBoardCell.setType(BoardCellType.DAMAGED);
        cellMapByType.get(BoardCellType.DAMAGED).add(targetBoardCell);
    }

    private void drawDestroyed(Ship ship) {
        List<BoardCell> damagedCells = cellMapByType.get(BoardCellType.DAMAGED);
        List<BoardCell> destroyedCells = cellMapByType.get(BoardCellType.DESTROYED);
        for (Coordinate coordinate : ship.getCoordinates()) {
            BoardCell cell = cellMap.get(coordinate);
            damagedCells.remove(cell);
            cell.setType(BoardCellType.DESTROYED);
            destroyedCells.add(cell);
        }
    }
}
