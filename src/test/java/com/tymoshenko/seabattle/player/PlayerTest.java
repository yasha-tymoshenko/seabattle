package com.tymoshenko.seabattle.player;

import com.tymoshenko.seabattle.board.BoardCellType;
import com.tymoshenko.seabattle.board.Coordinate;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PlayerTest {
    private static final Logger LOG = LoggerFactory.getLogger(PlayerTest.class);

    @Test
    public void testPlayerCreation() {
        Player player1 = new Player("Yakiv", new ShootingStrategyRandom());
        Player player2 = new Player("Dima", new ShootingStrategyRandom());

        int moves = 1;
        final int maxMoves = 100;
        ShotResult shotResult1;
        ShotResult shotResult2;
        do {
            Coordinate move = player1.move();
            shotResult1 = player2.processEnemyShot(move);
            player1.drawOwnShotResult(shotResult1);

            move = player2.move();
            shotResult2 = player1.processEnemyShot(move);
            player2.drawOwnShotResult(shotResult2);

            moves+=2;
        } while (shotResult1.getBoardCellType() != BoardCellType.DESTROYED &&
                shotResult2.getBoardCellType() != BoardCellType.DESTROYED && moves <= maxMoves);

        LOG.info(player1.toString());
        LOG.info(player2.toString());
    }
}