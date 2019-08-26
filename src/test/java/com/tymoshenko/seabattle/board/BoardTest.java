package com.tymoshenko.seabattle.board;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BoardTest {
    private static final Logger LOG = LoggerFactory.getLogger(BoardTest.class);

    @Test
    public void shouldInitBoard() {
        Board board = new PlayerBoard();
        board.init();
        LOG.debug(board.toString());
    }

}