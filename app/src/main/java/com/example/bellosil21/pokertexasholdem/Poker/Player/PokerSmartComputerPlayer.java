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
            Random rand = new Random();

            PokerGameState state = (PokerGameState) info;
            BetController betController =
                   new BetController(state.getBetController());

            HandRanker hand =
                    new HandRanker(state.getPlayerHand(this.playerNum),
                            state.getCommunityCards());

            CardCollection handRank = hand.computeHandRank();

            int chance = 0;

            switch (handRank.getHandRank()){
                case STRAIGHT_FLUSH:
                    chance = 9;
                    break;
                case FOUR_OF_A_KIND:
                    chance = 8;
                    break;
                case FULL_HOUSE:
                    chance = 7;
                    break;
                case FLUSH:
                    chance = 6;
                    break;
                case STRAIGHT:
                    chance = 5;
                    break;
                case THREE_OF_A_KIND:
                    chance = 4;
                    break;
                case TWO_PAIR:
                    chance = 3;
                    break;
                case PAIR:
                    chance = 2;
                    break;
                case HIGH_CARD:
                    chance = 1;
                    break;
            }

            int myChips = betController.getPlayerChips(this.playerNum);
            int maxBet = betController.getMaxBet();
            int difference = myChips - maxBet;

            // slow down so the human player can see what is going
            sleep(500);

            if(betController.getMaxBet() == 0 && chance < 20){
                game.sendAction(new PokerCheck(this)); //its my turn and no one has bet.
            }
            else if (difference > state.getBetController().getSmallBlind() * 3){
                chance -= 15;
            }
            else if (difference > state.getBetController().getBigBlind() * 3){
                chance -= 30;
            }

            int confidence = (int)(Math.random() * chance + 1);

            if(confidence > chance * .6){
                game.sendAction(new PokerRaiseBet(this, maxBet + 50));
            }
            else if (confidence > chance * .3) {
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
