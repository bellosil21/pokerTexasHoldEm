package com.example.bellosil21.pokertexasholdem.Poker.GameActions;

import com.example.bellosil21.pokertexasholdem.Game.actionMsg.GameAction;
import com.example.bellosil21.pokertexasholdem.Game.GamePlayer;

/**
 * Class for sending an Instance of a Show/Hide Cards Action
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
*/
public class PokerShowHideCards extends GameAction {
    /**
     * constructor for ShowHideCards
     *
     * @param player the player who created the action
     */
    public PokerShowHideCards(GamePlayer player) {
        super(player);
    }
}
