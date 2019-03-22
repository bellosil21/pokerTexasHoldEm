package com.example.bellosil21.myapplication;

import java.util.ArrayList;

/**
 * Keeps track of whose turn it is.
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class TurnTracker {
    // the amount of players in the game
    private ArrayList<PlayerChipCollection> players;
    // a player ID to track the current player's turn 0 <= activePlayerID <
    // players.size()
    private int activePlayerID;

    /**
     * The tracker requires the total amount of players and the player to
     * take the first turn.
     *
     * @param player         the ArrayList of all PlayerChipCollection in the
     *                      game
     * @param activePlayerID the player to take the first turn;
     *                       0 <= activePlayerID < players.size()
     */
    public TurnTracker(ArrayList<PlayerChipCollection> player,
                       int activePlayerID) {
        this.players = player;
        this.activePlayerID = activePlayerID;
    }

    /**
     * Copy constructor
     */
    public TurnTracker(TurnTracker toCopy) {
        this.players = new ArrayList<PlayerChipCollection>();

        for (PlayerChipCollection p : toCopy.players) {
            this.players.add(new PlayerChipCollection(p));
        }
        activePlayerID = toCopy.activePlayerID;
    }

    /**
     * Changes the turn for the next player.
     */
    public void nextTurn() {
        activePlayerID = (activePlayerID + 1) % players.size();
    }

    /**
     * Returns the player ID of who's turn
     * it is.
     * @return The current player's turn.
     */
    public int getActivePlayerID() {
        return activePlayerID;
    }

    @Override
    public String toString() {
        return "Current Turn: Player " + activePlayerID;
    }
}
