package com.example.bellosil21.pokertexasholdem.Poker.GameActions;

import com.example.bellosil21.pokertexasholdem.Game.actionMsg.GameAction;
import com.example.bellosil21.pokertexasholdem.Game.GamePlayer;

public class PokerRaiseBet extends GameAction {

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public PokerRaiseBet(GamePlayer player) {
        super(player);
    }
}
