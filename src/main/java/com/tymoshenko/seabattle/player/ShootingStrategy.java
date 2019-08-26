package com.tymoshenko.seabattle.player;

import com.tymoshenko.seabattle.board.Coordinate;
import com.tymoshenko.seabattle.board.EnemyBoard;

public interface ShootingStrategy {
    Coordinate shoot();

    void setEnemyBoard(EnemyBoard enemyBoard);
}
