package com.example.bellosil21.pokertexasholdem.Poker.Player;

import com.example.bellosil21.pokertexasholdem.Game.GameComputerPlayer;
import com.example.bellosil21.pokertexasholdem.Game.infoMsg.GameInfo;
import com.example.bellosil21.pokertexasholdem.Game.infoMsg.NotYourTurnInfo;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerAllIn;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerCall;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerCheck;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerFold;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerRaiseBet;
import com.example.bellosil21.pokertexasholdem.Poker.GameState.BetController;
import com.example.bellosil21.pokertexasholdem.Poker.GameState.PokerGameState;
import com.example.bellosil21.pokertexasholdem.Poker.Hand.Card;
import com.example.bellosil21.pokertexasholdem.Poker.HankRanker.CardCollection;
import com.example.bellosil21.pokertexasholdem.Poker.HankRanker.HandRanker;

import java.io.Serializable;
import java.util.Random;

/**
 * The "smart" AI for Poker
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class PokerSmartComputerPlayer extends GameComputerPlayer {

    private int confidence;
    private final int FAIRLY_CONFIDENT = 45;
    private final int CONFIDENT = 60;
    private final int VERY_CONFIDENT = 75;

    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public PokerSmartComputerPlayer(String name) {
        super(name);
        confidence = 0;
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
        if(info instanceof NotYourTurnInfo || info == null){
            return;
        }
        else if(info instanceof PokerGameState){

            // Setting the current game state and declaring player's chances
            PokerGameState state = (PokerGameState) info;
            CardCollection handRank;
            confidence = 0;

            // Gets the player current hand rank
            BetController betController =
                   new BetController(state.getBetController());

            HandRanker hand =
                    new HandRanker(state.getPlayerHand(this.playerNum),
                            state.getCommunityCards());

            handRank = hand.computeHandRank();

            // Sets the chance variable depending on current hand
            switch (handRank.getHandRank()){
                case STRAIGHT_FLUSH:
                    confidence = 35;
                    break;
                case FOUR_OF_A_KIND:
                    confidence = 30;
                    break;
                case FULL_HOUSE:
                    confidence = 25;
                    break;
                case FLUSH:
                    confidence = 20;
                    break;
                case STRAIGHT:
                    confidence = 15;
                    break;
                case THREE_OF_A_KIND:
                    confidence = 10;
                    break;
                case TWO_PAIR:
                    confidence = 8;
                    break;
                case PAIR:
                    confidence = 5;
                    break;
                case HIGH_CARD:
                    confidence = 3;
                    break;
            }

            int myChips = betController.getPlayerChips(this.playerNum);
            int maxBet = betController.getMaxBet();
            int difference = myChips - maxBet;
            int needToCall =
                    state.getBetController().getCallAmount(this.playerNum);
            int raiseAmount = needToCall + (needToCall / 2);
            if (raiseAmount > state.getChips(this.playerNum)){
                raiseAmount = state.getChips(this.playerNum);
            }

            if(betController.getCallAmount(this.playerNum) == 0){
                game.sendAction(new PokerCheck(this)); //its my turn and no one has bet.
                return;
            }
            else if(betController.getCallAmount(this.playerNum) ==
                    state.getChips(this.playerNum)){
                confidence -= 25;
            }
            else if (betController.getCallAmount(this.playerNum) > betController.getSmallBlind() * 3){
                confidence -= 15;
            }
            else if (difference > state.getBetController().getSmallBlind() * 2){
                confidence -= 10;
            }

            double chance = Math.random() * (100);
            confidence += chance;

            if (confidence > VERY_CONFIDENT){
                if (raiseAmount == state.getChips(this.playerNum)) {
                    game.sendAction(new PokerAllIn(this));
                }
                else {
                    game.sendAction(new PokerRaiseBet(this,
                            needToCall + (needToCall / 2),
                            needToCall));
                }
            }
            else if (confidence > CONFIDENT){
                if (raiseAmount == state.getChips(this.playerNum)) {
                    game.sendAction(new PokerAllIn(this));
                }
                else {
                    game.sendAction(new PokerRaiseBet(this,
                            needToCall + (needToCall / 4),
                            needToCall));
                }
            }
            else if (confidence > FAIRLY_CONFIDENT) {
                game.sendAction(new PokerCall(this));
            }
            else {
                game.sendAction(new PokerFold(this));
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
