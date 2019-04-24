package com.example.bellosil21.pokertexasholdem.Poker.GameState;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the TurnTracker
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class TurnTrackerTest {

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

}
