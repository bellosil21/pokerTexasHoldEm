package com.example.bellosil21.pokertexasholdem.Poker.GameInfo;

import com.example.bellosil21.pokertexasholdem.Game.infoMsg.GameInfo;

import java.io.Serializable;

/**
 * Informs players that the blinds are increasing
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class PokerIncreasingBlinds extends GameInfo implements Serializable {

    private int newSmallBlind;
    private int newBigBlind;

    public PokerIncreasingBlinds(int newSmallBlind, int newBigBlind) {
        this.newSmallBlind = newSmallBlind;
        this.newBigBlind = newBigBlind;
    }

    public int getNewSmallBlind() {
        return newSmallBlind;
    }

    public int getNewBigBlind() {
        return newBigBlind;
    }
}
