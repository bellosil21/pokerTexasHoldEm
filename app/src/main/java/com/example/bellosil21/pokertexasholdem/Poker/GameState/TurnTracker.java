package com.example.bellosil21.pokertexasholdem.Poker.GameState;

import com.example.bellosil21.pokertexasholdem.Poker.Money.PlayerChipCollection;

import java.util.ArrayDeque;
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
    ArrayDeque<Integer> activePlayers; // players who haven't taken a turn in
                                       // this phase
    ArrayDeque<Integer> promptedPlayers; // players who taken a turn in this
                                         // phase
    ArrayList<Integer> foldedPlayers; // players who folded and no longer in
                                      // this round
    ArrayList<Integer> sittingOutPlayers; // players who are sitting out and
                                          // fold on the first phase
    ArrayList<Integer> removedPlayers; // players who left the game or no
                                       // longer have any chips to bet (an
                                       // amount of 0)
    int numPlayers; // the number of players in this game
    int dealerID; // is a marker for blinds and who takes the first turn
                  // when dealerID == playerID, the player is smallBlind
                  //     (dealerID + 1) % numPlayers == playerID,
                  //        the player is bigBlind
                  //     (dealerID + 2) % numPlayers == playerID,
                  //        the player takes the first turn

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
     * Returns the player ID of who's turn it is.
     * The first player in the activePlayers array is the active player
     *
     * @return The current player's turn.
     */
    public int getActivePlayerID() {
        return 0;
    }

    @Override
    public String toString() {
        return "Current Turn: Player " + activePlayers;
    }
}
