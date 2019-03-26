package com.example.bellosil21.pokertexasholdem.Poker.GameActions;

import com.example.bellosil21.pokertexasholdem.Game.actionMsg.GameAction;
import com.example.bellosil21.pokertexasholdem.Game.GamePlayer;

public class PokerCall extends GameAction {
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public PokerCall(GamePlayer player) {
        super(player);
    }
}
