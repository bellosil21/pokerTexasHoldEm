package com.example.bellosil21.pokertexasholdem.Poker.GameState;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the TurnTracker methods
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class TurnTrackerTest {

    /**
     * Tests the deepCopy of the TurnTracker by looping through the activePlayerID's of two
     * TurnTrackers and then comparing their values
     */
    @Test
    public void deepCopy() {
        TurnTracker tt = new TurnTracker(4, 0);
        TurnTracker ntt = new TurnTracker(tt);

        for (int i = 0; i < 4; i++) {
            int a = tt.getActivePlayerID();
            int b = ntt.getActivePlayerID();

            assertEquals(a, b);

            tt.nextTurn();
            ntt.nextTurn();
        }
    }
    /**
     * Tests the isRoundOver() method by creating a TurnTracker instance with 4 players. It then
     * loops through 4 players and forces all of them to fold. The method then tests to see if
     * the amount of active players in tt is 0.
     */
    @Test
    public void isRoundOver() {
        TurnTracker tt = new TurnTracker(4, 0);
        for(int i=0; i<4; i++){
            tt.fold();
        }
        assertEquals(0, tt.getActivePlayers().length);
    }

    /**
     * Tests the checkIfGameOver() method by creating a TurnTracker instance tt with 1 player and 0
     * dealers. It then checks to see if the game is over. The method then updates tt to have 2
     * players and checks to see if the method checkIfGameOver fails
     */
    @Test
    public void checkIfGameOver(){
        TurnTracker tt = new TurnTracker(1, 0);
        assertEquals(0, tt.checkIfGameOver());
        tt = new TurnTracker(2,0);
        assertEquals(-1, tt.checkIfGameOver());
    }

    /**
     * Tests the determineBlind() method by checking to see if the roles of big/small blind has
     * shifted.
     */
    @Test
    public void determineBlinds(){
        TurnTracker tt = new TurnTracker(4, 3);
        tt.determineBlinds();
        assertEquals(0,tt.getDealerID());
    }

    /**
     * Tests the nextRound() method by setting the TurnTracker to have 3 folded players and 1
     * active. It then calls nextRound() and checks to see if the game now has 4 active players.
     */
    @Test
    public void nextRound(){
        TurnTracker tt = new TurnTracker(4, 0);
        for(int i=0; i<3; i++){
            tt.fold();
        }
        assertEquals(1, tt.getActivePlayers().length);
        tt.nextRound();
        assertEquals(4, tt.getActivePlayers().length);
    }

    /**
     * Tests the isPhaseOver() method by folding 3/4 players.
     */
    @Test
    public void isPhaseOver(){
        TurnTracker tt = new TurnTracker(4, 0);
        for(int i=0; i<3; i++){
            tt.fold();
        }
        assertEquals(true, tt.isPhaseOver());
    }
}
