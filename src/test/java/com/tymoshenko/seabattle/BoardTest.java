package com.tymoshenko.seabattle;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BoardTest {
    private static final Logger LOG = LoggerFactory.getLogger(BoardTest.class);

    @Test
    public void shouldInitBoard() {
        Board board = new Board();
        board.init();
        LOG.debug(board.toString());
    }

}