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
    private ArrayList<Integer> contributors;
    private int contribution; 
    protected ChipCollection pot;

    /**
     * External Citation
     * Problem: needed to make a arraylist of integers
     * solution: Kevin googled that shit.
     */


    /** PotTracker Constructor
     *
     * @param
     */
    public PotTracker() {
        pot =  new ChipCollection(0);
        contribution = 0; 
    }

    /**
     * Copy constructor
     */
    public PotTracker(PotTracker toCopy) {
        this.contributors = new ArrayList<Integer>();

        for(int i =0; i<toCopy.contributors.size(); i++){
            this.contributors.add(toCopy.contributors.get(i));
        }
        this.contribution = toCopy.contribution; 
        this.pot = new ChipCollection(0); 
        this.pot.addChips(toCopy.pot.getChips());
    }

    public void addContributor(int playerID){
        if(playerID < 0){
            /* returns a positive integer ID*/
            this.addContributor(playerID*playerID);
        }
        contributors.add(playerID);
    }

    /* how do we do this? */
    public boolean checkIfContributed(int playerID){
        /** if a playerID exists in the array list of contributors, than does that mean they have
        indeed "contributed" already? meaning return true in this method??
         */
        return false;
    }

    /*
    @Override
    public String toString() {
        return "Pot: " + pot + "\nMaximum Bet: " + maxBet;
    }
    */
}
