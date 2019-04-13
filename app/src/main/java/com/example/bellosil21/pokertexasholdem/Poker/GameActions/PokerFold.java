package com.example.bellosil21.pokertexasholdem.Poker.GameActions;

import com.example.bellosil21.pokertexasholdem.Game.GamePlayer;
import com.example.bellosil21.pokertexasholdem.Game.actionMsg.GameAction;

import java.io.Serializable;

/**
 * Class for sending an Instance of a Fold Action
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class PokerFold extends GameAction implements Serializable {
    /**
     * constructor for Fold
     *
     * @param player the player who created the action
     */
    public PokerFold(GamePlayer player) {
        super(player);
    }
}
