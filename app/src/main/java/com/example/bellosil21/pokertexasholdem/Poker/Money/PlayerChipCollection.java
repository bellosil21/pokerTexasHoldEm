package com.example.bellosil21.pokertexasholdem.Poker.Money;

/**
 * Stores an amount of chips with player data.
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class PlayerChipCollection extends ChipCollection {

    private int playerID;
    private int lastBet; // keep track of most recent bet in case of a raise
    private int lastContributedPot; // index of the last pot this player
                                    // contributed to

    private static final int DEFAULT_LAST_CONTRIBUTED_POT = -1;

    /**
     * Assigns player data to a ChipCollection.
     *
     * @param amount The amount of starting chips in the collection
     * @param id     The player's id
     */
    public PlayerChipCollection(int amount, int id) {
        super(amount);
        this.playerID = id;
        lastBet = 0;
        lastContributedPot = DEFAULT_LAST_CONTRIBUTED_POT;
    }

    /**
     * Copy constructor
     */
    public PlayerChipCollection(PlayerChipCollection toCopy) {
        super(toCopy.amount);
        this.playerID = toCopy.playerID;
        lastBet = toCopy.lastBet;
        lastContributedPot = toCopy.lastContributedPot;
    }


    public void setLastBet(int lastBet) {
        this.lastBet = lastBet;
    }

    public int getLastBet() {
        return lastBet;
    }

    public void setLastContributedPot(int pot) {lastContributedPot = pot;}

    public int getLastContributedPot() { return lastContributedPot; }

    /**
     * Prints the status of the collection.
     *
     * @return the string describing status of player action
     */
    @Override
    public String toString() {
        return super.toString() + ", playerID is " + playerID;
    }
}
