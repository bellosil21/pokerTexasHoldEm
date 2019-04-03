package com.example.bellosil21.pokertexasholdem.Poker.GameActions;

import com.example.bellosil21.pokertexasholdem.Game.actionMsg.GameAction;
import com.example.bellosil21.pokertexasholdem.Game.GamePlayer;

/**
 * Class for sending an Instance of a Sit Out Action
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class PokerSitOut extends GameAction {
    /**
     * constructor for SitOut
     *
     * @param player the player who created the action
     */
    public PokerSitOut(GamePlayer player) {
        super(player);
    }
}
