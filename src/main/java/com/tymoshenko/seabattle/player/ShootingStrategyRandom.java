package com.tymoshenko.seabattle.player;

import com.tymoshenko.seabattle.board.BoardCell;
import com.tymoshenko.seabattle.board.Coordinate;
import com.tymoshenko.seabattle.board.EnemyBoard;

import java.util.List;
import java.util.Random;

public class ShootingStrategyRandom implements ShootingStrategy {
    private final Random random;
    private EnemyBoard enemyBoard;

    public ShootingStrategyRandom() {
        random = new Random();
    }

    @Override
    public Coordinate shoot() {
        List<BoardCell> freeCells = enemyBoard.getFreeCells();
        return freeCells.get(random.nextInt(freeCells.size())).getCoordinate();
    }

    @Override
    public void setEnemyBoard(EnemyBoard enemyBoard) {
        this.enemyBoard = enemyBoard;
    }
}
