package com.example.bellosil21.pokertexasholdem.Poker.GameState;

import com.example.bellosil21.pokertexasholdem.Poker.Hand.Card;
import com.example.bellosil21.pokertexasholdem.Poker.Hand.Deck;
import com.example.bellosil21.pokertexasholdem.Poker.Hand.Hand;
import com.example.bellosil21.pokertexasholdem.Poker.Money.PlayerChipCollection;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Defines the game state to play Poker.
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class PokerGameState implements Serializable {

    /**
     * instance variables
     */
    // all cards in deck
    private Deck playingDeck;
    // array of Players hand
    private ArrayList<Hand> hands;
    // array of Cards shared for community
    private ArrayList<Card> communityCards;

    // current round
    private int roundNumber;
    // place holder to define who has BB, SB, and first better and a number
    // between 0 and number of players minus 1
    private int dealerID;

    // current small blind betting amount
    private int smallBlind;
    // current big blind betting amount
    private int bigBlind;

    // array of chip amount for players
    private ArrayList<PlayerChipCollection> playersChips;

    // tracks the pot and maximum bet
    private PotTracker bets;
    // tracks whose turn it is
    private TurnTracker turn;

    /**
     * constants
     */
    private static final int INIT_ROUND_NUM = 1;

    /**
     * Creates and initialize a new PokerGameState from given options.
     *
     * @param startingChips starting amount of chips for each player
     * @param startingSmall starting mandatory betting amount for small blind
     * @param startingBig   starting mandatory betting amount for big blind
     * @param numPlayers    number of players in game
     */
    public PokerGameState(int startingChips, int startingSmall,
                          int startingBig, int numPlayers) {
        playingDeck = new Deck();
        hands = new ArrayList<Hand>();
        for (int i = 0; i < numPlayers; i++) {
            hands.add(new Hand());
        }
        communityCards = new ArrayList<Card>();

        roundNumber = INIT_ROUND_NUM;
        dealerID = 0;

        smallBlind = startingSmall;
        bigBlind = startingBig;

        playersChips = new ArrayList<PlayerChipCollection>();
        for (int i = 0; i < numPlayers; i++) {
            playersChips.add(new PlayerChipCollection(startingChips, i));
        }

        //bets = new PotTracker(playersChips);

        turn = new TurnTracker(playersChips, dealerID);
    }

    /**
     * Copy Constructor that only gives players their hand and does not give
     * them the deck. All
     * other instance vars are given.
     *
     * @param toCopy   the PokerGameState to copy
     * @param playerID the playerID that is given this copy of the game state
     */
    public PokerGameState(PokerGameState toCopy, int playerID) {
        playingDeck = null;

        // only pass the player their hand or the hand's showCards is true;
        // otherwise, pass blank
        // hands for the other players
        hands = new ArrayList<Hand>();
        for (int i = 0; i < toCopy.hands.size(); i++) {
            if (i == playerID || toCopy.hands.get(i).isShowCards()) {
                hands.add(new Hand(toCopy.hands.get(i)));
            } else {
                hands.add(new Hand());
            }
        }

        communityCards = new ArrayList<Card>();
        for (Card c : toCopy.communityCards) {
            communityCards.add(new Card(c));
        }

        roundNumber = toCopy.roundNumber;
        dealerID = toCopy.dealerID;

        smallBlind = toCopy.smallBlind;
        bigBlind = toCopy.bigBlind;

        playersChips = new ArrayList<PlayerChipCollection>();
        for (PlayerChipCollection cc : toCopy.playersChips) {
            playersChips.add(new PlayerChipCollection(cc));
        }

        bets = new PotTracker(toCopy.bets);
        turn = new TurnTracker(toCopy.turn);
    }

    /**
     * Description of a game state.
     *
     * @return a string that contains the description of the game state.
     */
    @Override
    public String toString() {
        // creates toReturn string variable
        String toReturn = "\nPoker Game State:\n";
        if (playingDeck == null) {
            toReturn += "The deck of the game is hidden";
        } else {
            toReturn = playingDeck.toString();
        }
        // for loop iterates the player's hand array to determine which cards
        // the player has
        for (int i = 0; i < hands.size(); i++) {
            // prints out the cards that are in the player's hand
            toReturn += "\nPlayer " + (i + 1) + "'s Hand: " + hands.get(i).toString();
        }
        // states which community cards are currently on the table
        toReturn += "\nCommunity Cards:";
        // iterates through the community cards array and prints them out
        if (communityCards.size() == 0) {
            toReturn += " No community cards have been dealt.";
        } else {
            for (Card c : communityCards) {
                toReturn += " " + c.toString();
            }
        }
        // states the current round number
        toReturn += "\nRound Number: " + roundNumber;
        // states who the current dealer is
        toReturn += "\nCurrent Dealer: " + dealerID;
        // states the cost of the small blind
        toReturn += "\nSmall Blind: " + smallBlind;
        // states the cost of the big blind
        toReturn += "\nBig Blind: " + bigBlind;
        // iterates through the player's chip amount and states how much
        // money they have
        for (int i = 0; i < playersChips.size(); i++) {
            toReturn += "\nPlayer " + (i + 1) + ": " + playersChips.get(i);
        }
        // states the current amount of the pot and
        toReturn += "\n" + bets.toString();
        toReturn += "\n" + turn.toString();

        return toReturn;
    }

    /** Game Actions */

    /**
     * Submits a bet if it's the player's turn and goes to the next turn.
     * this is equivalent to Raise action which is why
     * we call the raise method from the PotTracker class.
     *
     * @param playerID the ID of the player giving the action
     * @param amount   the amount that the player is submitting
     * @return true if the bet is valid and it is the player's turn
     */
    public boolean placeBets(int playerID, int amount) {
        if (turn.getActivePlayerID() != playerID) {
            return false;
        }
        if (bets.raiseBet(playerID, amount)) {
            turn.nextTurn();
            return true;
        }
        return false;
    }

    /**
     * Folds the player's hand if it's their turn and goes to the next turn.
     *
     * @param playerID the ID of the player giving the action
     * @return true if the action was valid and it is the player's turn
     */
    public boolean fold(int playerID) {
        if (turn.getActivePlayerID() != playerID) {
            return false;
        }
        playersChips.get(playerID).setHasFolded(true);
        turn.nextTurn();

        return true;
    }

    /**
     * Shows or hides a player's cards.
     *
     * @param playerID the ID of the player showing a card
     * @param isShown  true if the player wants to show their cards
     * @return true; this action is always valid
     */
    public boolean showHideCards(int playerID, boolean isShown) {
        hands.get(playerID).setShowCards(isShown);
        return true;
    }

    /**
     * Checks to see if it is the current player's turn and if there any
     * current bets. If not
     * return true and go to next turn.
     *
     * @param playerID ID of the player
     * @return true if the maxBet == 0 and it is the player's turn
     */
    public boolean check(int playerID) {
        if (turn.getActivePlayerID() == playerID && bets.getMaxBet() == 0) {
            turn.nextTurn();
            return true;
        }
        return false;
    }

    /**
     * Calls the current pot and goes to the next turn.
     *
     * @param playerID ID of the player
     * @return true if is the player's turn
     */
    public boolean call(int playerID) {
        if (turn.getActivePlayerID() != playerID) {
            return false;
        }

        bets.call(playerID);
        turn.nextTurn();
        return true;
    }

    /**
     * Checks to see if it is the current player's turn. If it is an instance
     * of the player's
     * chip amount is set to the integer bets. Then the player's chip amount
     * is removed from
     * their personal pot and is placed as a bet.
     *
     * @param playerID the ID of the player
     * @return true if the bet was valid and it is the player's turn.
     */
    public boolean allIn(int playerID) {
        if (turn.getActivePlayerID() == playerID) {
            int bet = playersChips.get(playerID).getChips();
            return bets.raiseBet(playerID, bet);
        }
        return false;
    }


    public boolean exit(int playerID) {
        if (turn.getActivePlayerID() == playerID) {
            fold(playerID);
        } else {
            playersChips.get(playerID).setHasFolded(true);
            turn.nextTurn();
        }
        return true;
    }


    /**
     * Give players their cards.
     */
    public void deal() {
        playingDeck.dealPlayers(hands);
    }

}
