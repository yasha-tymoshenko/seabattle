package com.tymoshenko.seabattle.player;

import com.tymoshenko.seabattle.board.BoardCell;
import com.tymoshenko.seabattle.board.Coordinate;
import com.tymoshenko.seabattle.board.EnemyBoard;
import com.tymoshenko.seabattle.exception.IllegalTargetException;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.util.Scanner;
import java.util.stream.Collectors;

@Slf4j
public class ShootingStrategyConsole implements ShootingStrategy, Closeable {
    private static final int MAX_RETRIES = 15;

    private int retries = 0;
    private EnemyBoard enemyBoard;
    private Scanner scanner = new Scanner(System.in);

    @Override
    public Coordinate shoot() {
        int x, y;

        System.out.println("Please enter a coordinate: ");
        String input = scanner.nextLine().trim();

        try {
            if (input.length() == 0) {
                throw new IllegalArgumentException("Input contains no coordinate.");
            }
            char xChar = input.charAt(0);
            if (Character.isAlphabetic(xChar)) {
                if (Character.isLowerCase(xChar)) {
                    xChar = Character.toUpperCase(xChar);
                }
                if (xChar < 'A' || xChar > 'A' + 9) {
                    throw new IllegalTargetException(String.format("Wrong X coordinate: [%s].", xChar));
                }
                x = xChar - 'A';
            } else {
                throw new IllegalTargetException(String.format("Wrong X coordinate: [%s].", xChar));
            }
            String yStr = input.substring(1).trim();
            y = Integer.parseInt(yStr) - 1;
            if (y < 0 || y > 10) {
                throw new IllegalTargetException(String.format("Wrong Y coordinate: [%s].", yStr));
            }
            Coordinate coordinate = new Coordinate(x, y);
            if (!enemyBoard.getFreeCells().stream().map(BoardCell::getCoordinate).collect(Collectors.toSet())
                    .contains(coordinate)) {
                throw new IllegalTargetException(String.format("This target is illegal: [%s].", coordinate));
            }
            return coordinate;
        } catch (IllegalArgumentException | IllegalTargetException e) {
            retries++;
            if (retries <= MAX_RETRIES) {
                log.error("Wrong user input.", e);
                return shoot();
            } else {
                throw e;
            }
        }
    }

    @Override
    public void setEnemyBoard(EnemyBoard enemyBoard) {
        this.enemyBoard = enemyBoard;
    }

    @Override
    public void close() {
        scanner.close();
    }
}
