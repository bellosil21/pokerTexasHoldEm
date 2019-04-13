package com.example.bellosil21.pokertexasholdem.Poker.GameInfo;

import com.example.bellosil21.pokertexasholdem.Game.infoMsg.GameInfo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Informs players a player is out of funds
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class PokerPlayerOutOfFunds extends GameInfo implements Serializable {

    private ArrayList<Integer> playerIDs;

    public PokerPlayerOutOfFunds(ArrayList<Integer> playerIDs) {
        this.playerIDs = new ArrayList<>(playerIDs);
    }

    public ArrayList<Integer> getPlayerIDs() {
        return playerIDs;
    }
}
