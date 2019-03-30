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

    /** instance vars **/
    private ArrayDeque<Integer> activePlayers; // players who haven't taken a
    // turn in this phase
    private ArrayDeque<Integer> promptedPlayers; // players who taken a turn in
    // this phase
    private ArrayList<Integer> allInPlayers; // players who have committed
    // their whole chip collection to the pot
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

    /** constants **/
    private static final int PLAYERS_FOR_GAME_OVER = 1;

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
        this.allInPlayers = new ArrayList<Integer>();
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

        this.allInPlayers = new ArrayList<>();
        for (int i : toCopy.allInPlayers) {
            this.allInPlayers.add(i);
        }

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
     * Removes the player from being further prompted for this round. This
     * player is still in the round.
     *
     * @param playerID the ID of the player who went all in
     */
    public void allIn(int playerID) {
        if (!allInPlayers.contains(playerID)) {
            allInPlayers.add(playerID);
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
     *
     * @return true if there is still players to prompt
     */
    public boolean promptPlayers() {
        if (promptedPlayers.isEmpty()) {
            return false;
        }
        while(promptedPlayers.isEmpty() == false){
            activePlayers.offer(promptedPlayers.poll());
        }
        return true;
    }

    /**
     * If everyone but one player has folded, return the playerID that is still
     * left in the game.
     *
     * This player is either a prompted player, or is an allIn player
     *
     * @return -1 if more than one player remain; otherwise, return
     *          the playerID of the last player remaining
     */
    public int isRoundOver() {
        if (promptedPlayers.size() + allInPlayers.size() > 1) {
            return -1;
        }
        if (!promptedPlayers.isEmpty()) {
            return promptedPlayers.poll();
        }
        return allInPlayers.get(0);
    }

    /**
     * Requirement: This function must be called after the removed players
     * array has been updated and we check to see if someone won (one player
     * remaining in total)
     *
     * Resets the turn order for the next round with players still in the game.
     * First, the prompted, active, allIn, and folded player arrays are
     * cleared.
     *
     * Next, add player into the active queue until there is a numPlayer
     * amount of them, followed by removing players who lost or left.
     *
     * If there is one person left in the active players array, the game is
     * over and return the index of the player left in the array. They won.
     * Otherwise, return -1.
     *
     * @return -1 if there is more than one player left in active players;
     *         otherwise, return the playerID of the only player remaining
     */
    public int nextRound(){
        promptedPlayers.clear();
        activePlayers.clear();
        allInPlayers.clear();
        foldedPlayers.clear();

        for(int i=0; i<numPlayers; i++){
            activePlayers.add(i);
        }

        for (int i: removedPlayers){
            activePlayers.remove(i);
        }

        if (activePlayers.size() > PLAYERS_FOR_GAME_OVER) {
            return -1;
        }

        return activePlayers.pop();
    }

    /**
     * Requirement: nextRound() has been called prior to this function and
     * nextRound() returns -1, meaning that there is more than one player
     * left in the game.
     *
     * Returns the playerID of the smallBlind and bigBlind from the dealerID
     *
     * Increment the dealer ID (being within mod numPlayers to stay within index
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
    public int[] determineBlinds() {
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

    public int getDealerID() {
        return dealerID;
    }

    @Override
    public String toString() {
        return "Current Turn: Player " + getActivePlayerID();
    }
}
