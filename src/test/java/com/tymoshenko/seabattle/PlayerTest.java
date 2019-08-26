package com.tymoshenko.seabattle;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PlayerTest {
    private static final Logger LOG = LoggerFactory.getLogger(PlayerTest.class);

    @Test
    public void testPlayerCreation() {
        Player player = new Player("Yakiv");
        LOG.info(player.toString());
    }
}