package com.example.bellosil21.pokertexasholdem.Poker.GameState;

import com.example.bellosil21.pokertexasholdem.Game.infoMsg.GameInfo;
import com.example.bellosil21.pokertexasholdem.Game.infoMsg.GameState;
import com.example.bellosil21.pokertexasholdem.Poker.Hand.Card;
import com.example.bellosil21.pokertexasholdem.Poker.Hand.Deck;
import com.example.bellosil21.pokertexasholdem.Poker.Hand.Hand;
import com.example.bellosil21.pokertexasholdem.Poker.HankRanker.CardCollection;
import com.example.bellosil21.pokertexasholdem.Poker.HankRanker.HandRanker;
import com.example.bellosil21.pokertexasholdem.Poker.HankRanker.SortByCardCollection;

import java.util.ArrayList;
import java.util.Collections;

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
    // the current phase
    private int numPhase;
    // number of players in the game
    private int numPlayers;
    // tracks whose turn it is
    private TurnTracker turnTracker;
    // controls and tracks the bets and pots
    private BetController betController;

    private ArrayList<GameInfo> lastActions;

    /**
     * constant
     */
    private static final int INIT_PHASE_NUM = 0;

    public static final int PHASE_PRE_FLOP = 0;
    public static final int PHASE_FLOP = 1;
    public static final int PHASE_TURN = 2;
    public static final int PHASE_RIVER = 3;

    public static final int CARDS_FLOP = 3;

    public static final int INIT_ROUND_NUM = 1;
    // the first player of the small blind
    private static final int INIT_DEALER_ID = 0;
    public static final int ROUNDS_PER_BLIND_INCREMENT = 5;
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

        numPhase = INIT_PHASE_NUM;

        lastActions = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++)  {
            lastActions.add(null);
        }

        turnTracker.nextRound();
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

        numPhase = toCopy.numPhase;

        lastActions = new ArrayList<>(toCopy.lastActions);
    }

    /**
     * Give players their cards. (Only if they are an active player)
     */
    public void deal() {
        int[] players = turnTracker.getActivePlayers();
        ArrayList<Hand> toDeal = new ArrayList<>();
        for (int i : players) {
            toDeal.add(hands.get(i));
        }
        playingDeck.dealPlayers(toDeal);
    }

    /**
     * Determines the rankings of the players from a HankRanker
     *
     * @return an int array who's indexes match the same player indexes as hands. The int represents
     * the players ranking, where 0 is the best rank, a higher int is a lower rank, and the max
     * integer means the player has folded.
     */
    public int[] rankCardCollections() {
        ArrayList<CardCollection> finalHands = new ArrayList<>();

        // find the best hand for every player still in the game
        // everyone who is not in the game is given a null hand
        for (int i = 0; i < numPlayers; i++) {
            if (turnTracker.isPlayerInRound(i)) {
                HandRanker ranker = new HandRanker(hands.get(i),
                        communityCards);
                finalHands.add(i, ranker.computeHandRank());
            } else {
                finalHands.add(i, null);
            }
        }

        // create a new array that's a sorted copy of the previous one
        ArrayList<CardCollection> sortedFinalHands = new ArrayList<>();
        sortedFinalHands.addAll(finalHands);
        Collections.sort(sortedFinalHands, new SortByCardCollection());

        int[] finalRanks = new int[numPlayers]; // stores the ranks to be
        // returned

        // rank each player according to this sorted array
        // if multiple people match a sorted hand, they have the same
        // rank
        for (int i = 0; i < sortedFinalHands.size(); i++) {
            CardCollection bestHandForRank = sortedFinalHands.get(i);

            for (int playerID = 0; playerID < finalHands.size(); playerID++) {
                CardCollection finalHandPlayer = finalHands.get(playerID);

                // assigned players who folded the max int
                if (finalHandPlayer == null) {
                    finalRanks[playerID] = Integer.MAX_VALUE;
                } else {
                    int compare = finalHandPlayer.compareTo(bestHandForRank);
                    if (compare == 0) {
                        finalRanks[playerID] = i;
                    }
                }

            } //player loop

        } // sorted CardCollections loop

        return finalRanks;
    }

    /**
     * Hides the hands of all players
     */
    public void hideHands() {
        for (Hand h : hands) {
            h.setShowCards(false);
        }
    }

    public BetController getBetController() {
        return betController;
    }

    public TurnTracker getTurnTracker() {
        return turnTracker;
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
        }

        return toReturn;
    }

    /**
     * Gets a player's current chip amount
     *
     * @param playerNum - Index of player to return
     * @return amount of chips the player has
     */
    public int getChips(int playerNum) {
        return betController.getPlayerChips(playerNum);
    }

    /**
     * Gets and returns all Cards so far laid out in the table
     *
     * @return ArrayList containing all the community Cards
     */
    public ArrayList<Card> getCommunityCards() {
        return communityCards;
    }

    public ArrayList<Hand> getHands(){ return hands; }
    public Deck getDeck(){ return this.playingDeck; }

    public int getRoundNumber(){return this.roundNumber;}

    public void incrementRoundNumber() {
        roundNumber++;
    }

    public void updateLastAction(int playerID, GameInfo action) {
        lastActions.set(playerID, action);
    }

    public ArrayList<GameInfo> getLastActions() {
        return lastActions;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public int getNumPhase() {
        return numPhase;
    }

    public void setNumPhase(int numPhase) {
        this.numPhase = numPhase;
    }

    public Hand getPlayerHand(int playerIndex){return hands.get(playerIndex);}
}
