package com.tymoshenko.seabattle;

import com.tymoshenko.seabattle.board.BoardCellType;
import com.tymoshenko.seabattle.board.Coordinate;
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
        int moveNumber = 1;
        do {
            moveNumber = playerMove(player1, player2, moveNumber);
            if (player2.isFleetDestroyed()) {
                break;
            }
            moveNumber = playerMove(player2, player1, moveNumber);
        } while (!player1.isFleetDestroyed() && !player2.isFleetDestroyed());
        Player winner;
        if (player1.isFleetDestroyed()) {
            winner = player2;
        } else {
            winner = player1;
        }
        log.info("\n\nThe winner is: \t[{}]!\n", winner.getName());
    }

    private int playerMove(Player player, Player enemy, int moveNumber) {
        ShotResult shotResult;
        do {
            Coordinate target = player.move();
            shotResult = enemy.processEnemyShot(target);
            player.drawOwnShotResult(shotResult);
            log.info("----- Move #{}. ----- {}", moveNumber, player.toString());
            moveNumber++;
        } while (!enemy.isFleetDestroyed() && (shotResult.getBoardCellType() == BoardCellType.DAMAGED ||
                shotResult.getBoardCellType() == BoardCellType.DESTROYED));
        return moveNumber;
    }

}
