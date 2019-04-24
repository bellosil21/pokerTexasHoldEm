package com.example.bellosil21.pokertexasholdem.Poker.GameState;

import com.example.bellosil21.pokertexasholdem.Poker.Money.PlayerChipCollection;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests the BetController methods
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class BetControllerTest {

    /**
     * Tests the copy constructor by copying the state and changing the
     * original and checking if the two differ
     */
    @Test
    public void copyConstructor() {
        BetController controller = new BetController(2, 100, 10, 20);
        controller.allIn(0);
        BetController newController = new BetController(controller);
        assertEquals(controller.getBigBlind(), newController.getBigBlind());
        controller = new BetController(2, 100, 1, 20);;
        assertNotEquals(controller.getSmallBlind(), newController.getSmallBlind());
    }

    /**
     * Tests the allIn() method. Asserts the new pots created and the chips
     * taken from the player.
     */
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

    /**
     * Tests the forceBlinds methods when the players do not go all in. This
     * is when the blinds are less than their total amount; thus, the
     * forceBlinds methods should return false.
     */
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

    /**
     * Tests the forceBlinds methods when the players go all in. This is when
     * the blinds are equal to or greater than their total amount; thus, the
     * forceBlinds methods should return true.
     */
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

    /**
     * Tests the startPhase() method. This method should reset the player's last
     * bets to 0 and set the maxBet to 0.
     */
    @Test
    public void startPhase() {
        BetController controller = new BetController(4, 100, 10, 20);
        controller.startPhase();

        startPhaseAsserter(controller);

        // make everyone call
        for (int i = 0; i < controller.getPlayers().size(); i++) {
            controller.check(i);
        }

        controller.startPhase();
        startPhaseAsserter(controller);

        // make everyone all in
        for (int i = 0; i < controller.getPlayers().size(); i++) {
            controller.allIn(i);
        }

        controller.startPhase();
        startPhaseAsserter(controller);

    }

    /**
     * Helper method for asserting the variables after startPhase() has been
     * called on a BetController
     * @param controller the BetController
     */
    private void startPhaseAsserter(BetController controller) {
        // the max bet should be reset to 0 after startPhase()
        assertEquals(0, controller.getMaxBet());

        // all player last bets should be reset to 0
        for (PlayerChipCollection p : controller.getPlayers()) {
            assertEquals(0, p.getLastBet());
        }
    }

    /**
     * Tests a combination of forceBlinds, call, allIn, and reset methods.
     */
    @Test
    public void betActions() {
        BetController bets = new BetController(4, 100, 10, 20);
        bets.forceSmallBlinds(0);
        bets.forceBigBlinds(1);
        bets.call(2);

        // assert player 2's call

        // the pots should have the player's call amount and the player should
        // the amount withdrawn from their funds
        assertEquals(0, bets.getCallAmount(2));
        assertEquals(80, bets.getPlayerChips(2));
        assertEquals(2, bets.getPots().size());
        assertEquals(50, bets.getTotalAmount());
        assertEquals(20, bets.getMaxBet());

        bets.asynchronousReset();
        // the pots should be cleared, the max bet should be reset, the
        // total amount contributed should be reset, and the player's last
        // contributed pot and last bet should be reset
        assertEquals(0, bets.getPots().size());
        assertEquals(0, bets.getMaxBet());
        assertEquals(0, bets.getTotalAmount());

        for (int i = 0; i < bets.getPlayers().size(); i++) {
            assertEquals(0, bets.getWinnings()[i]);
            assertEquals(-1, bets.getPlayers().get(i).getLastContributedPot());
            assertEquals(0, bets.getPlayers().get(i).getLastBet());
        }
    }

    /**
     * Tests the distributePot() method after players go all in at different
     * amounts.
     */
    @Test
    public void distributePot() {
        BetController bets = new BetController(4, 100, 10, 20);

        // make everyone have different amounts of chips
        ArrayList<PlayerChipCollection> players = bets.getPlayers();
        players.get(2).addChips(100);
        players.get(1).addChips(200);
        players.get(0).addChips(300);

        // make everyone go all in
        for (int i = 0; i < bets.getPlayers().size(); i++) {
            bets.allIn(i);
        }

        // player 3 and 2 tied, player 1 got second, player 0 got last
        int[] rankings = {3,2,1,1};
        bets.distributePots(rankings);

        //check the player chip amounts
        assertEquals(200, bets.getPlayers().get(3).getChips());
        assertEquals(500, bets.getPlayers().get(2).getChips());
        assertEquals(200, bets.getPlayers().get(1).getChips());
        assertEquals(100, bets.getPlayers().get(0).getChips());

        //check the winnings
        assertEquals(-300, bets.getWinnings()[0]);
        assertEquals(-100, bets.getWinnings()[1]);
        assertEquals(300, bets.getWinnings()[2]);
        assertEquals(100, bets.getWinnings()[3]);
    }

}
