package com.tymoshenko.seabattle;

import com.tymoshenko.seabattle.player.Player;
import com.tymoshenko.seabattle.player.ShootingStrategyConsole;
import com.tymoshenko.seabattle.player.ShootingStrategyRandom;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SeaBattle {

    public static void main(String[] args) {
        startConsoleGameAgainstComputer();
    }

    private static void startConsoleGameAgainstComputer() {
        ShootingStrategyConsole shootingStrategyConsole = new ShootingStrategyConsole();
        try {
            startGame(shootingStrategyConsole);
        } finally {
            closeConsole(shootingStrategyConsole);
        }
    }

    private static void startGame(ShootingStrategyConsole shootingStrategyConsole) {
        Player human = new Player("Human", shootingStrategyConsole);
        Player computer = new Player("Computer", new ShootingStrategyRandom());
        Game game = new Game(human, computer);
        game.start();
    }

    private static void closeConsole(ShootingStrategyConsole shootingStrategyConsole) {
        try {
            shootingStrategyConsole.close();
        } catch (Throwable throwable) {
            log.error("Cannot close input scanner.", throwable);
        }
    }

}
