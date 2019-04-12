package com.example.bellosil21.pokertexasholdem.Poker.GameState;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BetControllerTest {

    @Test
    public void allIn() {
        BetController controller = new BetController(4, 100, 10, 20);
        int bigBlindID = 0;
        controller.forceBigBlinds(bigBlindID);

        assertEquals(1, controller.getPots().size());
        assertEquals(bigBlindID,
                (int) controller.getPots().get(0).getContributors().get(0));
        assertEquals(20, controller.getPots().get(0).getContribution());
    }
}
