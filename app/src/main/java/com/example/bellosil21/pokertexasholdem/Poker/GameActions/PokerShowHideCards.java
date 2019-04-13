package com.example.bellosil21.pokertexasholdem.Poker.GameActions;

import com.example.bellosil21.pokertexasholdem.Game.actionMsg.GameAction;
import com.example.bellosil21.pokertexasholdem.Game.GamePlayer;

import java.io.Serializable;

/**
 * Class for sending an Instance of a Show/Hide Cards Action
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
*/
public class PokerShowHideCards extends GameAction implements AnytimeAction,
        Serializable {

    private boolean isShown;

    /**
     * constructor for ShowHideCards
     *
     * @param player the player who created the action
     * @param isShown true if the players wants their cards to be shown
     */
    public PokerShowHideCards(GamePlayer player, boolean isShown) {
        super(player);
        this.isShown = isShown;
    }

    public boolean isShown() {
        return isShown;
    }
}
