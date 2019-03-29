package com.example.bellosil21.pokertexasholdem.Poker.GameState;

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
    private ArrayList<Integer> contributors;
    /*
     * External Citation
     *  Date:     27 March 2019
     *  Problem:  Did not know how to make an array list of ints.
     *  Resource: https://stackoverflow.com/questions/8811815/is-it-
     *            possible-to-assign-numeric-value-to-an-enum-in-java
     *  Solution: Used the Integer object instead of the primitive type.
     */

    private int contribution; // bet amount to be added to the pot per player

    /** Default PotTracker Constructor
     *
     * Initializes a default pot
     */
    public PotTracker(int amount) {
        contributors = new ArrayList<>();
        contribution = amount;
    }

    /** PotTracker Constructor
     *
     * @param amount the amount of chips initially in this pot
     * @param players an array of players who contributed to this amount
     */
    public PotTracker(int amount, ArrayList<Integer> players) {
        contributors = new ArrayList<>();
        contributors.addAll(players);
        contribution = amount;
    }

    /**
     * Copy constructor
     */
    public PotTracker(PotTracker toCopy) {
        this.contributors = new ArrayList<>();
        this.contributors.addAll(toCopy.contributors);

        this.contribution = toCopy.contribution;
    }

    public void addContributor(int playerID){
        contributors.add(playerID);
    }

    public boolean isContributor(int playerID) {
        return contributors.contains(playerID);
    }

    public int getContribution() {
        return contribution;
    }
    public ArrayList<Integer> getContributors(){return this.contributors;}

    @Override
    public String toString() {
        return "Contribution: " + contribution + ", contributors: " + contributors.toString();
    }

}
