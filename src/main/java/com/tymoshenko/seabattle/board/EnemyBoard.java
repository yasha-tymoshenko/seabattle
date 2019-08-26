package com.tymoshenko.seabattle.board;

import com.tymoshenko.seabattle.player.ShotResult;
import com.tymoshenko.seabattle.ship.Ship;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class EnemyBoard extends Board {

    @Override
    public void init() {
        super.init();
    }

    public void drawShot(ShotResult shotResult) {
        BoardCell targetBoardCell = cellMap.get(shotResult.getCoordinate());
        cellMapByType.get(BoardCellType.EMPTY).remove(targetBoardCell);
        targetBoardCell.setType(shotResult.getBoardCellType());

        List<BoardCell> missedCells = cellMapByType.get(BoardCellType.MISSED_HIT);
        List<BoardCell> damagedCells = cellMapByType.get(BoardCellType.DAMAGED);

        switch (shotResult.getBoardCellType()) {
            case MISSED_HIT: {
                missedCells.add(targetBoardCell);
            }
            break;
            case DAMAGED: {
                damagedCells.add(targetBoardCell);
            }
            break;
            case DESTROYED: {
                Ship destroyedShip = shotResult.getDestroyedShip().orElseThrow(() -> new IllegalStateException(
                        String.format("No destroyed ship was found at [%s].", shotResult.getCoordinate())));
                drawDestroyed(destroyedShip);
            }
            break;
            default:
                throw new IllegalArgumentException(String.format("Wrong shot result: [%s].", shotResult));
        }
    }

    public List<BoardCell> getFreeCells() {
        return cellMapByType.get(BoardCellType.EMPTY);
    }

    private void drawDestroyed(Ship ship) {
        List<BoardCell> damagedCells = cellMapByType.get(BoardCellType.DAMAGED);
        List<BoardCell> destroyedCells = cellMapByType.get(BoardCellType.DESTROYED);
        List<BoardCell> missedCells = cellMapByType.get(BoardCellType.MISSED_HIT);
        List<BoardCell> emptyCells = cellMapByType.get(BoardCellType.EMPTY);

        for (Coordinate shipCoordinate : ship.getCoordinates()) {
            BoardCell cell = cellMap.get(shipCoordinate);
            damagedCells.remove(cell);
            cell.setType(BoardCellType.DESTROYED);
            destroyedCells.add(cell);

            findBorderCells(shipCoordinate).forEach(boardCell -> {
                boardCell.setType(BoardCellType.MISSED_HIT);
                missedCells.add(boardCell);
                emptyCells.remove(boardCell);
            });
        }
    }
}
