package com.tymoshenko.seabattle;

import com.tymoshenko.seabattle.board.BoardCellType;
import com.tymoshenko.seabattle.board.Coordinate;
import com.tymoshenko.seabattle.exception.GameOverException;
import com.tymoshenko.seabattle.player.Player;
import com.tymoshenko.seabattle.player.ShotResult;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Game {
    private final Player player1;
    private final Player player2;

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public void start() {
        try {
            play();
        } catch (GameOverException e) {
            Player winner = player1.isFleetDestroyed() ? player2 : player1;
            log.info("\n\nThe winner is: \t[{}]!\n", winner.getName());
        }

    }

    private void play() throws GameOverException {
        int moveNumber = 1;
        while (moveNumber <= 2 * 10 * 10) {
            moveNumber = playerMove(player1, player2, moveNumber);
            moveNumber = playerMove(player2, player1, moveNumber);
        }
    }

    private int playerMove(Player player, Player enemy, int moveNumber) throws GameOverException {
        ShotResult shotResult;
        do {
            Coordinate target = player.move();
            shotResult = enemy.processEnemyShot(target);
            player.drawOwnShotResult(shotResult);
            log.info("----- Move #{}. ----- {}", moveNumber, player.toString());
            moveNumber++;
            if (enemy.isFleetDestroyed()) {
                throw new GameOverException(String.format("Player's [%s] fleet has been destroyed!", enemy.getName()));
            }
        } while (isEnemyHit(shotResult));
        return moveNumber;
    }

    private boolean isEnemyHit(ShotResult shotResult) {
        BoardCellType shotResultType = shotResult.getBoardCellType();
        return shotResultType == BoardCellType.DAMAGED || shotResultType == BoardCellType.DESTROYED;
    }

}
