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
    ArrayDeque<Integer> activePlayers; // players who haven't taken a turn in this phase
    ArrayDeque<Integer> promptedPlayers; // players who taken a turn in this phase
    ArrayList<Integer> foldedPlayers; // players who folded and no longer in this round
    ArrayList<Integer> sittingOutPlayers; // players who are sitting out and fold on the first phase
    ArrayList<Integer> removedPlayers; // players who left the game or no longer have any chips
    // to bet (an amount of 0)
    int numPlayers; // the number of players in this game
    int dealerID; // is a marker for blinds and who takes the first turn when dealerID ==
    // playerID, the player is smallBlind
    // (dealerID + 1) % numPlayers == playerID, the player is bigBlind
    // (dealerID + 2) % numPlayers == playerID, the player takes the first turn


    // TODO: 3/26/2019 add arrayDeque citation
    // https://docs.oracle.com/javase/8/docs/api/java/util/ArrayDeque.html
    /**
     * The tracker requires the total amount of players and the player to
     * take the first turn.
     *
     * @param dealerID         the ArrayList of all PlayerChipCollection in the
     *                      game
     * @param numPlayers the player to take the first turn;
     *                       0 <= activePlayerID < players.size()
     */
    public TurnTracker(int numPlayers, int dealerID) {
        this.activePlayers = new ArrayDeque<Integer>();
        this.promptedPlayers = new ArrayDeque<Integer>();
        this.foldedPlayers = new ArrayList<Integer>();
        this.sittingOutPlayers = new ArrayList<Integer>();
        this.removedPlayers = new ArrayList<Integer>();

    }

    /**
     * Copy constructor
     */
    public TurnTracker(TurnTracker toCopy) {

        for (PlayerChipCollection p : toCopy.players) {
            this.players.add(new PlayerChipCollection(p));
        }
    }

    /**
     * Changes the turn for the next player.
     */
    public boolean nextTurn() {

        if(activePlayers.isEmpty() == true){
            return false;
        }
        promptedPlayers.offer(activePlayers.poll());
        return true;
    }

    /**
     * Returns the player ID of who's turn
     * it is.
     * @return The current player's turn.
     */
    public int getActivePlayerID() {
        if(activePlayers.isEmpty() == true){
            return -1;
        }
        return activePlayers.peek();
    }

    public void fold(int playerID){
        foldedPlayers.add(playerID);
    }

    public void remove(int playerID){
        removedPlayers.add(playerID);
    }

    public void toggleSitting(int playerID){
        if(sittingOutPlayers.contains(playerID) == true){
            sittingOutPlayers.remove(playerID);
        }
        else{
            sittingOutPlayers.add(playerID);
        }
    }

    public void nextPhase(){
        while(promptedPlayers.isEmpty() == false){
            activePlayers.offer(promptedPlayers.poll());
        }
    }

    public void nextRound(){
        promptedPlayers.clear();
        activePlayers.clear();
        for(int i=0; i<numPlayers; i++){
            activePlayers.add(i);
        }
        for (int i: removedPlayers){
            activePlayers.remove(i);
        }
        dealerID = (dealerID+1)%numPlayers;
        while(!activePlayers.contains(dealerID)){
            dealerID = (dealerID+1)%numPlayers;
        }
    }
    @Override
    public String toString() {
        return "Current Turn: Player " + getActivePlayerID();
    }
}
