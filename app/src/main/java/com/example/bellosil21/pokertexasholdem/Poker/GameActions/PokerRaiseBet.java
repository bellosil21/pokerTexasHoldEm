package com.example.bellosil21.pokertexasholdem.Poker.GameActions;

import com.example.bellosil21.pokertexasholdem.Game.actionMsg.GameAction;
import com.example.bellosil21.pokertexasholdem.Game.GamePlayer;
import java.io.Serializable;

/**
 * Class for sending an Instance of a Raise Action
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
*/
public class PokerRaiseBet extends GameAction implements Serializable {

    // Instance variable for amount player wishes to raise
    private int raiseAmount;
    private int callAmount;


    /**
     * constructor for RaiseBet
     *
     * @param player the player who created the action
     */
    public PokerRaiseBet(GamePlayer player, int amount, int callAmount) {
        super(player);
        this.raiseAmount = amount;
        this.callAmount = callAmount;
    }

    public int getRaiseAmount() {
        return raiseAmount;
    }

    /**
     * Gives the net contribution to the pot from the last bet and current
     * amount the player is betting
     *
     * @return the difference between the raise amount and the call amount
     */
    public int netRaise() {
        return  raiseAmount - callAmount;
    }
}
