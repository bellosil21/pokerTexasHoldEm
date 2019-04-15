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

/**
 * The "smart" AI for Poker
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class PokerSmartComputerPlayer extends GameComputerPlayer {

    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public PokerSmartComputerPlayer(String name) {
        super(name);
    }

    /**
     * Updates the state of the other players and the game
     *
     * This smart AI checks for maxBets == 0, makes a bet when they have an
     * excess amount of funds, otherwise they will call 75% of the time and
     * fold 25% of the time.
     *
     * @param info about the game
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        double random = Math.random();
        if(info instanceof NotYourTurnInfo || info == null){
            return;
        }
        else if(info instanceof PokerGameState){
           BetController betController =
                   new BetController(((PokerGameState) info).getBetController());

            int myChips = betController.getPlayerChips(this.playerNum);
            int maxBet = betController.getMaxBet();

            // slow down so the human player can see what is going
            sleep(500);

            if(betController.getMaxBet() == 0){
                game.sendAction(new PokerCheck(this)); //its my turn and no one has bet.
            }
            else{
                int difference = myChips - maxBet;
                if(difference > 100){
                    game.sendAction(new PokerRaiseBet(this, maxBet + 50,
                            betController.getCallAmount(playerNum)));
                }
                else if (random > 0.25) {
                    game.sendAction(new PokerCall(this));
                }
                else {
                    game.sendAction(new PokerFold(this));
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
