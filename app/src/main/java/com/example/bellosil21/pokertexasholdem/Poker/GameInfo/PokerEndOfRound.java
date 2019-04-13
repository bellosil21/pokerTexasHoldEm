package com.example.bellosil21.pokertexasholdem.Poker.GameInfo;

import com.example.bellosil21.pokertexasholdem.Game.infoMsg.GameInfo;
import java.io.Serializable;

/**
 * Stores the differences of the players' chip collections
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class PokerEndOfRound extends GameInfo implements Serializable {

    private int[] winnings;

    /**
     * Constructor
     *
     * Create a copy of winnings since the game state will reset it soon
     *
     * @param winnings an int[] containing the net chip collections on the
     *                 players
     */
    public PokerEndOfRound(int[] winnings) {
        this.winnings = new int[winnings.length];
        for (int i = 0; i < winnings.length; i++) {
            this.winnings[i] = winnings[i];
        }
    }

    public int[] getWinnings() {
        return winnings;
    }

}
