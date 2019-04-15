package com.example.bellosil21.pokertexasholdem.Poker.GameInfo;

import com.example.bellosil21.pokertexasholdem.Game.infoMsg.GameInfo;

import java.io.Serializable;

/**
 * Informs of a check action
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class PokerCheckInfo extends GameInfo implements Serializable {

    private int playerID;

    public PokerCheckInfo(int playerNum) {
        this.playerID = playerNum;
    }

    public int getPlayerID() {
        return playerID;
    }
}
