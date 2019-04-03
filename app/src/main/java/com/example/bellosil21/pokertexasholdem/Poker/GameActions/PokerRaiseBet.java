package com.example.bellosil21.pokertexasholdem.Poker.GameActions;

import com.example.bellosil21.pokertexasholdem.Game.actionMsg.GameAction;
import com.example.bellosil21.pokertexasholdem.Game.GamePlayer;

/**
 * Class for sending an Instance of a Raise Action
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
*/
public class PokerRaiseBet extends GameAction {

    // Instance variable for amount player wishes to raise
    private int raiseAmount;

    /**
     * constructor for RaiseBet
     *
     * @param player the player who created the action
     */
    public PokerRaiseBet(GamePlayer player, int amount) {
        super(player);
        raiseAmount = amount;
    }

    public int getRaiseAmount() {
        return raiseAmount;
    }
}
