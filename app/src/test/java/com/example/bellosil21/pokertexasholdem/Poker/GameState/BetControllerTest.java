package com.example.bellosil21.pokertexasholdem.Poker.GameState;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class BetControllerTest {

    @Test
    public void copyConstructor() {
        BetController controller = new BetController(2, 100, 10, 20);
        controller.allIn(0);
        BetController newController = new BetController(controller);
        assertEquals(controller.getBigBlind(), newController.getBigBlind());
        controller = new BetController(2, 100, 1, 20);;
        assertNotEquals(controller.getSmallBlind(), newController.getSmallBlind());
    }

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

    @Test
    public void forceBlindsNoAllIn() {
        int smallBlind = 10;
        int bigBlind = 20;
        int startChips = 100;
        int bigID = 0;
        int smallID = 1;

        BetController controller = new BetController(2, startChips, smallBlind, bigBlind);

        boolean allIn = controller.forceSmallBlinds(smallID);
        assertEquals(false, allIn);
        assertEquals(startChips - smallBlind,
                controller.getPlayerChips(smallID));

        allIn = controller.forceBigBlinds(bigID);
        assertEquals(false, allIn);
        assertEquals(startChips - bigBlind, controller.getPlayerChips(bigID));

    }

    @Test
    public void forceBlindsAllIn() {
        int smallBlind = 10;
        int bigBlind = 100;
        int startChips = 100;
        int bigID = 0;
        int smallID = 1;

        BetController controller = new BetController(2, startChips, smallBlind, bigBlind);

        controller.getPlayers().get(smallID).removeChips(startChips - 1);

        boolean allIn = controller.forceSmallBlinds(smallID);
        assertEquals(true, allIn);
        assertEquals(0,
                controller.getPlayerChips(smallID));

        allIn = controller.forceBigBlinds(bigID);
        assertEquals(true, allIn);
        assertEquals(0, controller.getPlayerChips(bigID));

    }
}
