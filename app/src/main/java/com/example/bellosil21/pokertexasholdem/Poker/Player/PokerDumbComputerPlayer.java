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
        if(info instanceof PokerGameState){
            boolean showCards =
                    ((PokerGameState) info).getHands().get(this.playerNum).isShowCards();
            if (!showCards) {
                game.sendAction(new PokerShowHideCards(this));
            }

            sleep(500); //slow the computer down so the human player can see what is going on

            if(Math.random() > 0.5){
                game.sendAction(new PokerCall(this));
            }
            else{
                game.sendAction(new PokerCall(this));
            }
        }
    }
}
