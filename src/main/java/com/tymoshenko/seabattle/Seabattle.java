package com.tymoshenko.seabattle;

import com.tymoshenko.seabattle.player.Player;
import com.tymoshenko.seabattle.player.ShootingStrategyConsole;
import com.tymoshenko.seabattle.player.ShootingStrategyRandom;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Seabattle {
    public static void main(String[] args) {
        ShootingStrategyConsole shootingStrategyConsole = new ShootingStrategyConsole();
        try {
            Player human = new Player("Human", shootingStrategyConsole);
            Player computer = new Player("Computer", new ShootingStrategyRandom());
            Game game = new Game(human, computer);
            game.start();
        } finally {
            try {
                shootingStrategyConsole.close();
            } catch (Throwable throwable) {
                log.error("Cannot close input scanner.", throwable);
            }
        }
    }
}
