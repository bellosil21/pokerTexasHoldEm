package com.example.bellosil21.pokertexasholdem.Poker.Player;

import android.util.Log;

import com.example.bellosil21.pokertexasholdem.Game.GameComputerPlayer;
import com.example.bellosil21.pokertexasholdem.Game.infoMsg.GameInfo;
import com.example.bellosil21.pokertexasholdem.Game.infoMsg.NotYourTurnInfo;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerAllIn;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerCall;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerShowHideCards;
import com.example.bellosil21.pokertexasholdem.Poker.GameState.PokerGameState;

import java.io.Serializable;

/**
 * Another "dumb" AI for Poker
 * This player mostly goes all in.
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class PokerAllInComputer extends GameComputerPlayer implements Serializable {
    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public PokerAllInComputer(String name) {
        super(name);
    }

    /**
     * Updates the state of the other players and the game
     * <p>
     * This dumb AI shows their cards and always sends a call action.
     *
     * @param info about the game
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        /*checking for null object */
        if (info == null) {
            //this should never happen right? does it make sense?
            Log.i("AllIn", "GameInfo object is null.");
        }
        if (info instanceof NotYourTurnInfo) {
            return;
        }
        if (info instanceof PokerGameState) {
            boolean showCards =
                    ((PokerGameState) info).getHands().get(this.playerNum).isShowCards();
            if (!showCards) {
                game.sendAction(new PokerShowHideCards(this, true));
            }

            sleep(1000); // slow the computer down so the human player
            // can see what is going on

            if (Math.random() > 0.25) {
                game.sendAction(new PokerAllIn(this));
            } else {
                game.sendAction(new PokerCall(this));
            }
        }// if
    }// receiveInfo()
}
