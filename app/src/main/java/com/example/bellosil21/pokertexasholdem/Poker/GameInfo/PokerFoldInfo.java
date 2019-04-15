package com.example.bellosil21.pokertexasholdem.Poker.GameInfo;

import com.example.bellosil21.pokertexasholdem.Game.infoMsg.GameInfo;

import java.io.Serializable;

/**
 * Informs of a fold action
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class PokerFoldInfo extends GameInfo implements Serializable {

    private int playerID;

    public PokerFoldInfo(int numPlayer) {
        playerID = numPlayer;
    }

    public int getPlayerID() {
        return playerID;
    }
}
