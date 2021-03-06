package com.tymoshenko.seabattle.player;

import com.tymoshenko.seabattle.board.BoardCellType;
import com.tymoshenko.seabattle.board.Coordinate;
import com.tymoshenko.seabattle.board.EnemyBoard;
import com.tymoshenko.seabattle.board.PlayerBoard;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Player {
    private String name;
    private PlayerBoard boardOwn;
    private EnemyBoard boardEnemy;
    private ShootingStrategy shootingStrategy;

    public Player(String name, ShootingStrategy shootingStrategy) {
        this.name = name;
        this.shootingStrategy = shootingStrategy;
        boardOwn = new PlayerBoard();
        boardEnemy = new EnemyBoard();
        boardOwn.init();
        boardEnemy.init();
        shootingStrategy.setEnemyBoard(boardEnemy);
    }

    public Coordinate move() {
        return shootingStrategy.shoot();
    }

    public void drawOwnShotResult(ShotResult shotResult) {
        if (shotResult.getBoardCellType() == BoardCellType.DESTROYED) {
            String shipType = shotResult.getDestroyedShip().isPresent()
                    ? shotResult.getDestroyedShip().get().getType().name()
                    : "unknown";
            log.info("Player [{}] [{}] a [{}] at [{}].",
                    name, shotResult.getBoardCellType().name(), shipType, shotResult.getCoordinate());
        } else {
            log.info("Player [{}] [{}] at [{}].",
                    name, shotResult.getBoardCellType().name(), shotResult.getCoordinate());
        }
        boardEnemy.drawShot(shotResult);
    }

    public ShotResult processEnemyShot(Coordinate coordinate) {
        return boardOwn.processEnemyShot(coordinate);
    }

    public boolean isFleetDestroyed() {
        return boardOwn.getFleet().isDestroyed();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(String.format("\n\n%30s", "Player:\t")).append(name).append("\n");
        String boardOwnStr = boardOwn.toString();
        String boardEnemyStr = boardEnemy.toString();

        String[] ownTokens = boardOwnStr.split("\n");
        String[] enemyTokens = boardEnemyStr.split("\n");

        for (int tokenIndex = 0; tokenIndex < ownTokens.length; tokenIndex++) {
            sb.append(ownTokens[tokenIndex]).append("\t\t\t").append(enemyTokens[tokenIndex]).append("\n");
        }

        return sb.toString();
    }
}
