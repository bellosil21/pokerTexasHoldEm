package com.example.bellosil21.pokertexasholdem.Poker.GameState;

import com.example.bellosil21.pokertexasholdem.Game.infoMsg.GameState;
import com.example.bellosil21.pokertexasholdem.Poker.Hand.Card;
import com.example.bellosil21.pokertexasholdem.Poker.Hand.Deck;
import com.example.bellosil21.pokertexasholdem.Poker.Hand.Hand;

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
public class PokerGameState extends GameState {

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
    // number of players in the game
    private int numPlayers;
    // tracks whose turn it is
    private TurnTracker turnTracker;
    // controls and tracks the bets and pots
    private BetController betController;

    /**
     * constants
     */
    private static final int INIT_ROUND_NUM = 1;
    // the first player of the small blind
    private static final int INIT_DEALER_ID = 0;
    private static final long serialVersionUID = -8269749892027578792L;

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

        hands = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            hands.add(new Hand());
        }

        communityCards = new ArrayList<>();

        roundNumber = INIT_ROUND_NUM;

        this.numPlayers = numPlayers;

        betController = new BetController(numPlayers,
                startingChips, startingSmall, startingBig);

        turnTracker = new TurnTracker(numPlayers, INIT_DEALER_ID);
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
        hands = new ArrayList<>();
        for (int i = 0; i < toCopy.hands.size(); i++) {
            if (i == playerID || toCopy.hands.get(i).isShowCards()) {
                hands.add(new Hand(toCopy.hands.get(i)));
            } else {
                hands.add(new Hand());
            }
        }

        communityCards = new ArrayList<>();
        for (Card c : toCopy.communityCards) {
            communityCards.add(new Card(c));
        }

        roundNumber = toCopy.roundNumber;

        numPlayers = toCopy.numPlayers;

        turnTracker = new TurnTracker(toCopy.turnTracker);

        betController = new BetController(toCopy.betController);
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
        toReturn += "\nCurrent Dealer: " + turnTracker.getDealerID();
        // states the cost of the small blind
        toReturn += "\nSmall Blind: " + betController.getSmallBlind();
        // states the cost of the big blind
        toReturn += "\nBig Blind: " + betController.getBigBlind();
        // iterates through the player's chip amount and states how much
        // money they have
        for (int i = 0; i < numPlayers; i++) {
            toReturn += "\nPlayer " + (i + 1) + ": " + betController.getPlayerChips(i);
        };

        return toReturn;
    }

    /**
     * Give players their cards.
     */
    public void deal() {
        playingDeck.dealPlayers(hands);
    }

    /**
     * Determines the rankings of the players from a HankRanker
     *
     * @return an int array who's indexes match the same player indexes as hands. The int represents
     * the players ranking, where 0 is the best rank, a higher int is a lower rank, and the max
     * integer means the player has folded.
     */
    public int[] rankCardCollections() {
        //TODO
        return null;
    }

    public BetController getBetController() {
        return betController;
    }

    public TurnTracker getTurnTracker() {
        return turnTracker;
    }
}
