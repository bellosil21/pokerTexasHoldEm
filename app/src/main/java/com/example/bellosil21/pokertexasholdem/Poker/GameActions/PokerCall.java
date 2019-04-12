package com.example.bellosil21.pokertexasholdem.Poker.GameActions;

import com.example.bellosil21.pokertexasholdem.Game.actionMsg.GameAction;
import com.example.bellosil21.pokertexasholdem.Game.GamePlayer;

import java.io.Serializable;

/**
 * Class for sending an Instance of a Call Action
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class PokerCall extends GameAction implements Serializable {
    /**
     * constructor for PokerCall
     *
     * @param player the player who created the action
     */
    public PokerCall(GamePlayer player) {
        super(player);
    }
}
