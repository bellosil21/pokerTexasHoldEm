package com.example.bellosil21.pokertexasholdem.Poker.Player;

import android.util.Log;

import com.example.bellosil21.pokertexasholdem.Game.GameComputerPlayer;
import com.example.bellosil21.pokertexasholdem.Game.infoMsg.GameInfo;
import com.example.bellosil21.pokertexasholdem.Game.infoMsg.NotYourTurnInfo;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerCall;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerShowHideCards;
import com.example.bellosil21.pokertexasholdem.Poker.GameState.PokerGameState;


/**
 * The "dumb" AI for Poker
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class PokerDumbComputerPlayer extends GameComputerPlayer {

    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public PokerDumbComputerPlayer(String name) {
        super(name);
    }

    /**
     * Updates the state of the other players and the game
     *
     * This dumb AI shows their cards and always sends a call action.
     *
     * @param info about the game
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        /*checking for null object */
        if(info == null){
            //this should never happen right? does it make sense?
            Log.i("DumbComputerPlayer", "GameInfo object is null.");
        }
        if(info instanceof NotYourTurnInfo){
            return;
        }
        if(info instanceof PokerGameState) {
            // check if it is not our turn
            if (((PokerGameState) info).getTurnTracker().getActivePlayerID() != playerNum) {
                return;
            }

            boolean showCards =
                    ((PokerGameState) info).getHands().get(this.playerNum).isShowCards();
            if (!showCards) {
                game.sendAction(new PokerShowHideCards(this, true));
            }

            sleep(1000); // slow the computer down so the human player
                                   // can see what is going on

            game.sendAction(new PokerCall(this));
        }// if
    }// receiveInfo()
}
