package com.example.bellosil21.myapplication;

import java.util.ArrayList;

/**
 * Keeps track of the pot, bets, the players who have called the maximum bet.
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class BetTracker {
    // all player chip collections
    private ArrayList<PlayerChipCollection> players;
    // the maximum bet that has taken place before everyone has called in
    // order to move on to the next playing phase
    private int maxBet;
    // the winnings
    private ChipCollection pot;
    // sets default pot size to 0
    public static final int DEFAULT_POT_SIZE = 0;

    /** BetTracker Constructor
     *
     * @param players Takes in an array list of PlayerChip collection
     *                which is used to keep track of the players in the
     *                current game and the amount in the pot.
     */
    public BetTracker(ArrayList<PlayerChipCollection> players) {
        this.players = players;
        this.pot = new ChipCollection(DEFAULT_POT_SIZE);
        maxBet = 0;
    }

    /**
     * Copy constructor
     */
    public BetTracker(BetTracker toCopy) {
        this.players = new ArrayList<PlayerChipCollection>();

        for (PlayerChipCollection p : toCopy.players) {
            this.players.add(new PlayerChipCollection(p));
        }

        this.maxBet = toCopy.maxBet;
        this.pot = new ChipCollection(toCopy.pot);
    }

    /**
     * To be used with the GameAction. If the bet is valid, we prompt the
     * other players to respond to the new bet.
     * A bet is valid if it is greater than the maximum bet for the phase and
     * if the player has enough chips to bet.
     *
     * @param playerID  the ID of the player that's betting
     * @param betAmount the amount that the player is betting
     * @return true if the bet is valid
     */
    public boolean raiseBet(int playerID, int betAmount) {
        if (betAmount <= maxBet) {
            return false;
        }

        if (players.get(playerID).getChips() < betAmount) {
            return false;
        }

        maxBet = betAmount;
        // this number represents the players previous during the same
        // betting phase
        int lastBet = players.get(playerID).getLastBet();
        // removes the player's bet amount from his personal pot subtracting
        // his last bet
        players.get(playerID).removeChips(maxBet - lastBet);
        // adds the max bet to the pot
        pot.addChips(maxBet);

        promptOtherPlayers(playerID);

        return true;
    }

    /**
     * Calls the maxBet. If the player does not have enough chips to match
     * the maxBet, then they will go all in.
     * This method also sets the player's hasCalled boolean to true.
     *
     * @param playerID the ID of the player that's calling
     */
    public void call(int playerID) {
        int playerChips = players.get(playerID).getChips();

        if (playerChips < maxBet) {
            players.get(playerID).removeChips(playerChips);
            pot.addChips(playerChips);
        } else {
            players.get(playerID).removeChips(maxBet);
            pot.addChips(maxBet);
        }

        players.get(playerID).setHasCalled(true);
    }

    /**
     * Returns the maxBet
     */
    public int getMaxBet() {
        return maxBet;
    }

    /**
     * Sets the hasCalled variable of the current players to false, besides
     * the player that placed the bet.
     *
     * @param playerID the ID of the player who placed the bet and does not
     *                 need to call
     */
    private void promptOtherPlayers(int playerID) {
        for (PlayerChipCollection p : players) {
            if (!p.hasFolded()) {
                p.setHasCalled(false);
            }
        }
        players.get(playerID).setHasCalled(true);
    }

    @Override
    public String toString() {
        return "Pot: " + pot + "\nMaximum Bet: " + maxBet;
    }
}
