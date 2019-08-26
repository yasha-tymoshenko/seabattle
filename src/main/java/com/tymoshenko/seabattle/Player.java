package com.tymoshenko.seabattle;

import com.tymoshenko.seabattle.board.EnemyBoard;
import com.tymoshenko.seabattle.board.PlayerBoard;

public class Player {
    private String name;
    private PlayerBoard boardOwn;
    private EnemyBoard boardEnemy;

    public Player(String name) {
        this.name = name;
        boardOwn = new PlayerBoard();
        boardEnemy = new EnemyBoard();
        boardOwn.init();
        boardEnemy.init();
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
