package com.example.bellosil21.pokertexasholdem.Poker.GameState;

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
    private ArrayDeque<Integer> activePlayers; // players who haven't taken a
    // turn in this phase
    private ArrayDeque<Integer> promptedPlayers; // players who taken a turn in
    // this phase
    private ArrayList<Integer> foldedPlayers; // players who folded and no
    // longer in this round
    private ArrayList<Integer> sittingOutPlayers; // players who are sitting out
    // and fold on the first phase
    private ArrayList<Integer> removedPlayers; // players who left the game or
    // no longer have any chips
    // to bet (an amount of 0)
    private int numPlayers; // the number of players in this game
    private int dealerID; // is a marker for blinds and who takes the first
    // turn when dealerID == playerID, the player is smallBlind
    // (dealerID + 1) % numPlayers == playerID, the player is bigBlind
    // (dealerID + 2) % numPlayers == playerID, the player takes the first turn
    /*
     * External Citation
     *  Date:     26 March 2019
     *  Problem:  Did not know java's LinkedList implementation.
     *  Resource: https://docs.oracle.com/javase/8/docs/api/java/util/ArrayDeque.html
     *  Solution: We used an ArrayDeque found in java's javadoc.
     */

    /**
     * The tracker requires the total amount of players and the player to
     * take the first smallBlind.
     *
     * @param dealerID the marker for person who starts the small blind
     * @param numPlayers the numbers of players
     */
    public TurnTracker(int numPlayers, int dealerID) {
        this.activePlayers = new ArrayDeque<Integer>();
        this.promptedPlayers = new ArrayDeque<Integer>();
        this.foldedPlayers = new ArrayList<Integer>();
        this.sittingOutPlayers = new ArrayList<Integer>();
        this.removedPlayers = new ArrayList<Integer>();
        this.numPlayers = numPlayers;
        this.dealerID = dealerID % numPlayers;
    }

    /**
     * Copy constructor
     */
    public TurnTracker(TurnTracker toCopy) {
        this.activePlayers = toCopy.activePlayers.clone();
        this.promptedPlayers = toCopy.promptedPlayers.clone();

        this.foldedPlayers = new ArrayList<>();
        for (int i : toCopy.foldedPlayers) {
            this.foldedPlayers.add(i);
        }

        this.sittingOutPlayers = new ArrayList<>();
        for (int i : toCopy.sittingOutPlayers) {
            this.sittingOutPlayers.add(i);
        }

        this.removedPlayers = new ArrayList<>();
        for (int i: toCopy.removedPlayers) {
            this.removedPlayers.add(i);
        }

        this.numPlayers = toCopy.numPlayers;
        this.dealerID = toCopy.dealerID;
    }

    /**
     * Changes the turn to the next active player. If there are no more
     * players to me prompted, return true. Otherwise, return false.
     *
     * @return true if there's a player who needs to take an action
     */
    public boolean nextTurn() {

        if(activePlayers.isEmpty() == true){
            return false;
        }
        promptedPlayers.offer(activePlayers.poll());
        return true;
    }

    /**
     * Returns the player ID of who's turn it is.
     *
     * @return The current player's turn.
     */
    public int getActivePlayerID() {
        if(activePlayers.isEmpty() == true){
            return -1;
        }
        return activePlayers.peek();
    }

    /**
     * To be used in the PokerGameState to fold players who are sitting out
     * if true
     */
    public boolean isActivePlayerSittingOut() {
        return sittingOutPlayers.contains(getActivePlayerID());
    }

    /**
     * Removes a player from the round. This player will no longer be
     * prompted until the next round.
     *
     * @param playerID the ID of the player who folded
     */
    public void fold(int playerID){
        if (!foldedPlayers.contains(playerID)) {
            foldedPlayers.add(playerID);
        }
    }

    /**
     * Removes a player from the game. This player will no longer be involved
     * in the game.
     *
     * @param playerID the ID of the player who lost or left
     */
    public void remove(int playerID){
        if (!removedPlayers.contains(playerID)) {
            removedPlayers.add(playerID);
        }
    }

    /**
     * Toggles whether a player is sitting in/out. If they are sitting in,
     * they sit out and always fold when they are prompted. If they are
     * sitting out, they will regain control of their actions.
     *
     * @param playerID the ID of the player who is sitting in/out
     */
    public void toggleSitting(int playerID){
        if(sittingOutPlayers.contains(playerID) == true){
            sittingOutPlayers.remove(playerID);
        }
        else{
            sittingOutPlayers.add(playerID);
        }
    }

    /**
     * Moves all players from the prompted queue back in the active queue to
     * get their action. This method is called when there is a new bet or
     * when there is a new betting phase (after new community cards have been
     * place or have been dealt)
     *
     * We maintain the order of the queue by taking the first player in the
     * prompted queue and adding them into the back of the active queue.
     */
    public void promptPlayers(){
        while(promptedPlayers.isEmpty() == false){
            activePlayers.offer(promptedPlayers.poll());
        }
    }

    /**
     * Resets the turn order for the next round with players still in the game.
     * First, the prompted and active player queue is cleared. Next, add
     * player into the active queue until there is a numPlayer amount of
     * them, followed by removing players who lost or left. Then, increment
     * the dealer ID (being within mod numPlayers to stay within index
     * bounds) until it matches to an active player.
     *
     * We return two integers in an array. The first being the playerID of
     * the small blind, and the second being the playerID of the big blind.
     * We poll these players from the front of the active queue and then
     * place them at the end of the active queue.
     *
     * @return an int array with two element; the first being the playerID of
     * the small blind and the second being the playerID of the big blind.
     */
    public int[] nextRound(){
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

        int[] blinds = new int[2];
        blinds[0] = activePlayers.poll();
        blinds[1] = activePlayers.poll();
        activePlayers.offer(blinds[0]);
        activePlayers.offer(blinds[1]);

        return blinds;
    }

    /**
     * Returns the number of players still in the game
     */
    public int getRemainingPlayers() {
        return activePlayers.size() + promptedPlayers.size();
    }

    @Override
    public String toString() {
        return "Current Turn: Player " + getActivePlayerID();
    }
}
