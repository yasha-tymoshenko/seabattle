package com.tymoshenko.seabattle;

import com.tymoshenko.seabattle.player.Player;
import com.tymoshenko.seabattle.player.ShootingStrategyRandom;
import org.junit.Test;

public class GameTest {

    @Test
    public void gameTest() {
        Player yakiv = new Player("Yakiv", new ShootingStrategyRandom());
        Player dima = new Player("Dima", new ShootingStrategyRandom());
        Game game = new Game(yakiv, dima);
        game.start();
    }
}