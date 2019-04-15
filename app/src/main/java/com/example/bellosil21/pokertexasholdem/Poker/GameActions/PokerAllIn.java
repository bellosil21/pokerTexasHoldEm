package com.example.bellosil21.pokertexasholdem.Poker.GameActions;

import com.example.bellosil21.pokertexasholdem.Game.actionMsg.GameAction;
import com.example.bellosil21.pokertexasholdem.Game.GamePlayer;
import com.example.bellosil21.pokertexasholdem.Poker.GameInfo.PokerAllInInfo;

import java.io.Serializable;

/**
 * Class for sending an Instance of a All In Action
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class PokerAllIn extends GameAction implements Serializable {
    /**
     * constructor for PokerAllIn
     *
     * @param player the player who created the action
     */
    public PokerAllIn(GamePlayer player) {
        super(player);
    }

    public PokerAllInInfo getGameInfo(int playerID) {
        return new PokerAllInInfo(playerID);
    }

}
