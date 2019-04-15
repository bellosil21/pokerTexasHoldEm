package com.example.bellosil21.pokertexasholdem.Poker.GameInfo;

import com.example.bellosil21.pokertexasholdem.Game.infoMsg.GameInfo;

import java.io.Serializable;

/**
 * Informs of a raise action
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class PokerRaiseBetInfo extends GameInfo implements Serializable {

    private int playerID;
    private int netRaise;

    public PokerRaiseBetInfo(int playerNum, int netRaise) {
        playerID = playerNum;
        this.netRaise = netRaise;
    }

    public int getPlayerID() {
        return playerID;
    }

    public int getNetRaise() {
        return netRaise;
    }
}
