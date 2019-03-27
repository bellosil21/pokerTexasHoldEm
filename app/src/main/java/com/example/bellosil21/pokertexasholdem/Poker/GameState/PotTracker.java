package com.example.bellosil21.pokertexasholdem.Poker.GameState;

import com.example.bellosil21.pokertexasholdem.Poker.Money.ChipCollection;

import java.util.ArrayList;

/**
 * Keeps track of the pot, bets, the players who have called the maximum bet.
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class PotTracker {
    private ChipCollection pot;
    private ArrayList<Integer> contributors;
    private int contribution;
    private boolean isLocked;

    private static final int DEFAULT_POT = 0;

    /**
     * External Citation
     * Problem: needed to make a arraylist of integers
     * solution: Kevin googled that shit.
     */

    /** Default PotTracker Constructor
     *
     * Initializes a default pot
     */
    public PotTracker() {
        pot =  new ChipCollection(DEFAULT_POT);
        contributors = new ArrayList<>();
        contribution = DEFAULT_POT;
        isLocked = false;
    }

    /** PotTracker Constructor
     *
     * @param amount the amount of chips initially in this pot
     * @param players an array of players who contributed to this amount
     */
    public PotTracker(int amount, ArrayList<Integer> players) {
        pot =  new ChipCollection(amount);
        contributors = new ArrayList<>();
        contributors.addAll(players);
        contribution = amount;
        isLocked = false;
    }

    /**
     * Copy constructor
     */
    public PotTracker(PotTracker toCopy) {
        this.pot = new ChipCollection(toCopy.pot);

        this.contributors = new ArrayList<>();
        this.contributors.addAll(toCopy.contributors);

        this.contribution = toCopy.contribution;
        this.isLocked = toCopy.isLocked;
    }

    /**
     * If this pot is not locked, add the player to the list of contributors
     * and return true.
     *
     * @param playerID ID of player to contribute
     * @return true if not locked; otherwise, false
     */
    public boolean addContributor(int playerID){
        if (isLocked) {
            return false;
        }
        contributors.add(playerID);
        return true;
    }


    public ArrayList<Integer> getContributors(){
        return this.contributors;
    }

    public boolean isLocked() { return isLocked; }

    public void lock() { isLocked = true; }

    /*
    @Override
    public String toString() {
        return "Pot: " + pot + "\nMaximum Bet: " + maxBet;
    }
    */
}
