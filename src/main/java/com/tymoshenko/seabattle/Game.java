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
        int move = 1;
        final int maxMoves = 1000;
        do {
            move = playerMove(player1, player2, move);
            if (player2.isFleetDestroyed()) {
                break;
            }
            move = playerMove(player2, player1, move);
        } while (!(player1.isFleetDestroyed() || player2.isFleetDestroyed()) && move < maxMoves);
        Player winner;
        if (player1.isFleetDestroyed()) {
            winner = player2;
        } else {
            winner = player1;
        }
        log.info("\n\nThe winner is: \t[{}]!\n", winner.getName());
    }

    private int playerMove(Player player, Player enemy, int move) {
        Coordinate target;
        ShotResult shotResult;
        do {
            target = player.move();
            shotResult = enemy.processEnemyShot(target);
            player.drawOwnShotResult(shotResult);
            log.info("----- Move #{}. ----- {}", move, player.toString());
            move++;
        } while (!enemy.isFleetDestroyed() && (shotResult.getBoardCellType() == BoardCellType.DAMAGED ||
                shotResult.getBoardCellType() == BoardCellType.DESTROYED));
        return move;
    }

}
