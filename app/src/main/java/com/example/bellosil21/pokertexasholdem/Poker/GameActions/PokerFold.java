package com.example.bellosil21.pokertexasholdem.Poker.GameActions;

import com.example.bellosil21.pokertexasholdem.Game.GamePlayer;
import com.example.bellosil21.pokertexasholdem.Game.actionMsg.GameAction;

public class PokerFold extends GameAction {
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public PokerFold(GamePlayer player) {
        super(player);
    }
}
