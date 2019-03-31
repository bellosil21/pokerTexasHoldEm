package com.example.bellosil21.pokertexasholdem.Poker.Player;

import android.os.Build;

import com.example.bellosil21.pokertexasholdem.Game.Game;
import com.example.bellosil21.pokertexasholdem.Game.GameComputerPlayer;
import com.example.bellosil21.pokertexasholdem.Game.GamePlayer;
import com.example.bellosil21.pokertexasholdem.Game.infoMsg.GameInfo;
import com.example.bellosil21.pokertexasholdem.Game.infoMsg.NotYourTurnInfo;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerCall;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerCheck;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerFold;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerRaiseBet;
import com.example.bellosil21.pokertexasholdem.Poker.GameState.BetController;
import com.example.bellosil21.pokertexasholdem.Poker.GameState.PokerGameState;
import com.example.bellosil21.pokertexasholdem.Poker.GameState.TurnTracker;

import java.util.Random;

public class PokerSmartComputerPlayer extends GameComputerPlayer {

    protected Game game;
    protected GamePlayer player;
    protected String name;

    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public PokerSmartComputerPlayer(String name) {
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {
        //Random n = new Random();
        //int randomInt = n.nextInt(2)+1;
        if(info instanceof NotYourTurnInfo || game == null){
            return;
        }
        else if(info instanceof PokerGameState){
           BetController betController =
                   new BetController(((PokerGameState) info).getBetController());
            TurnTracker turnTracker = new TurnTracker(((PokerGameState) info).getTurnTracker());

            int myID = turnTracker.getActivePlayerID(); //my ID because its my turn right?
            int myChips = betController.getPlayerChips(myID);
            int maxBet = betController.getMaxBet();

            if(betController.getMaxBet() == 0){
                game.sendAction(new PokerCheck(this)); //its my turn and no one has bet.
            }
            else{
                if(myChips > maxBet){
                    game.sendAction(new PokerRaiseBet(this, 50));
                }
                else if(myChips == maxBet){
                    game.sendAction(new PokerCall(this));
                }
                else if(myChips < maxBet){
                    game.sendAction(new PokerFold(this)); //because i will loose.
                }
            }
        }
    }

}
