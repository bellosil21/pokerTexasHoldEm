package com.example.bellosil21.pokertexasholdem.Poker.GameActions;

import com.example.bellosil21.pokertexasholdem.Game.GamePlayer;
import com.example.bellosil21.pokertexasholdem.Game.actionMsg.GameAction;

/**
 * Class for sending a quit action
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class PokerQuit extends GameAction implements AnytimeAction {

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public PokerQuit(GamePlayer player) {
        super(player);
    }

    @Override
    public String toString() {
        return "Quit";
    }
}
