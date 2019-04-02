package com.example.bellosil21.pokertexasholdem.Poker.Player;

import com.example.bellosil21.pokertexasholdem.Game.GameComputerPlayer;
import com.example.bellosil21.pokertexasholdem.Game.infoMsg.GameInfo;
import com.example.bellosil21.pokertexasholdem.Game.infoMsg.NotYourTurnInfo;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerCall;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerCheck;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerFold;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerRaiseBet;
import com.example.bellosil21.pokertexasholdem.Poker.GameState.BetController;
import com.example.bellosil21.pokertexasholdem.Poker.GameState.PokerGameState;
import com.example.bellosil21.pokertexasholdem.Poker.GameState.TurnTracker;

public class PokerSmartComputerPlayer extends GameComputerPlayer {

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
        sleep(100); //slow down
        double random = Math.random();
        if(info instanceof NotYourTurnInfo || info == null){
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
                int difference = myChips - maxBet;
                if(difference > 100){
                    game.sendAction(new PokerRaiseBet(this, maxBet + 50));
                }
                if (random > 0.25) {
                    game.sendAction(new PokerCall(this));
                }
                else {
                    game.sendAction(new PokerFold(this)); //because i will loose.
                }
            }
        }
        /**
         External Citation
            Date:      27 March 2019
            Problem:   Needed an reference to start with this method.
            Resource:
                https://github.com/srvegdahl
            Solution:   I used the examples in these repositories as a reference to how to start
                        this functionality.
         */
    }

}
