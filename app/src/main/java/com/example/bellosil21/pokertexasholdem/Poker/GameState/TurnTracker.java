package com.example.bellosil21.pokertexasholdem.Poker.GameState;

import android.accessibilityservice.FingerprintGestureController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Keeps track of whose turn it is.
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class TurnTracker implements Serializable {

    /** instance variables **/
    private LinkedList<Integer> activePlayers; // players who haven't taken a
    // turn in this phase
    private LinkedList<Integer> promptedPlayers; // players who taken a turn in
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
    private int smallBlindID;
    private int bigBlindID;
    // turn when dealerID == playerID, the player is smallBlind
    // (dealerID + 1) % numPlayers == playerID, the player is bigBlind
    // (dealerID + 2) % numPlayers == playerID, the player takes the first turn

    /**
     External Citation
        Date:     26 March 2019
        Problem:  Did not know java's LinkedList implementation.
        Resource: https://docs.oracle.com/javase/8/docs/api/java/util/ArrayDeque.html
        Solution: We used an ArrayDeque found in java's javadoc.
     */

    /** constants **/
    private static final int PLAYERS_FOR_GAME_OVER = 1;

    /**
     * The tracker requires the total amount of players and the player to
     * take the first smallBlind.
     *
     * @param dealerID The marker for person who starts the small blind.
     * @param numPlayers The numbers of players.
     */
    public TurnTracker(int numPlayers, int dealerID) {
        this.activePlayers = new LinkedList<>();
        this.promptedPlayers = new LinkedList<>();
        this.allInPlayers = new ArrayList<>();
        this.foldedPlayers = new ArrayList<>();
        this.sittingOutPlayers = new ArrayList<>();
        this.removedPlayers = new ArrayList<>();
        this.numPlayers = numPlayers;
        this.dealerID = dealerID % numPlayers;
        smallBlindID = -1; // not initialized yet
        bigBlindID = -1; // not initialized yet

        for(int i=0; i<numPlayers; i++){
            activePlayers.add(i);
        }
    }

    /**
     * Copy constructor
     */
    public TurnTracker(TurnTracker toCopy) {
        this.activePlayers = new LinkedList<>();
        for (int i : toCopy.activePlayers) {
            activePlayers.offer(i);
        }

        this.promptedPlayers = new LinkedList<>();
        for (int i : toCopy.promptedPlayers) {
            promptedPlayers.offer(i);
        }

        this.allInPlayers = new ArrayList<>();
        for (int i : toCopy.allInPlayers) {
            allInPlayers.add(i);
        }

        this.foldedPlayers = new ArrayList<>();
        for (int i : toCopy.foldedPlayers) {
            foldedPlayers.add(i);
        }

        this.sittingOutPlayers = new ArrayList<>();
        for (int i : toCopy.sittingOutPlayers) {
            sittingOutPlayers.add(i);
        }

        this.removedPlayers = new ArrayList<>();
        for (int i : toCopy.removedPlayers) {
            removedPlayers.add(i);
        }

        this.numPlayers = toCopy.numPlayers;
        this.dealerID = toCopy.dealerID;
        this.smallBlindID = toCopy.smallBlindID;
        this.bigBlindID = toCopy.bigBlindID;
    }

    /**
     * Changes the turn to the next active player.
     */
    public void nextTurn() {
        if(activePlayers.isEmpty()){
            return;
        }

        // determine if someone left mid round
        if (removedPlayers.contains(activePlayers.peek())) {
            activePlayers.poll();

            if (activePlayers.isEmpty()) {
                return;
            }
        }

        promptedPlayers.offer(activePlayers.poll());
    }

    /**
     * Returns the player ID of who's turn it is.
     *
     * @return The current player's turn.
     */
    public int getActivePlayerID() {
        if(activePlayers.size() < 1){
            return -1;
        }
        Integer toReturn = activePlayers.peek();
        return toReturn;
    }

    /**
     * To be used in the PokerGameState to fold players who are sitting out
     * if true.
     */
    public boolean isActivePlayerSittingOut() {
        int activePlayer = getActivePlayerID();
        return (sittingOutPlayers.contains(activePlayer) || removedPlayers.contains(activePlayer));
    }

    /**
     * Removes a player from the round. This player will no longer be
     * prompted until the next round.
     * If the player folding is sitting out, don't add them to any array
     */
    public void fold(){
        int activePlayer = activePlayers.poll();
        if (!sittingOutPlayers.contains(activePlayer)) {
            foldedPlayers.add(activePlayer);
        }
    }

    /**
     * Removes the player from being further prompted for this round. This
     * player is still in the round.
     */
    public void allIn() {
        int activePlayer = activePlayers.poll();
        if (!sittingOutPlayers.contains(activePlayer)) {
            allInPlayers.add(activePlayer);
        }
    }

    /**
     * Removes a player from the game. This player will no longer be involved
     * in the game. Add them to the removed array list, and remove them from
     * any other list.
     *
     * @param playerID The ID of the player who lost or left.
     */
    public void remove(int playerID){
        if (!removedPlayers.contains(playerID)) {

            removedPlayers.add(playerID);

            activePlayers.remove((Integer)playerID);
            promptedPlayers.remove((Integer)playerID);

            allInPlayers.remove((Integer)playerID); // cast integer to remove
            // the object and not reference the index
            foldedPlayers.remove((Integer)playerID);
            sittingOutPlayers.remove((Integer)playerID);

        }
    }

    /**
     * Toggles whether a player is sitting in/out. If they are sitting in,
     * they sit out and always fold when they are prompted. If they are
     * sitting out, they will regain control of their actions.
     *
     * @param playerID the ID of the player who is sitting in/out
     * @return False if the player has been removed.
     */
    public boolean toggleSitting(Integer playerID){
        if (removedPlayers.contains(playerID)) {
            return false;
        }
        if (sittingOutPlayers.contains(playerID)){
            sittingOutPlayers.remove(playerID);
        }
        else {
            sittingOutPlayers.add(playerID);
        }
        return true;
    }

    /**
     * Moves all players from the prompted queue back in the active queue to
     * get their action. This method is called when there is a new bet or
     * when there is a new betting phase (after new community cards have been
     * place or have been dealt).
     *
     * We maintain the order of the queue by taking the first player in the
     * prompted queue and adding them into the back of the active queue.
     *
     * @return True if there is still players to prompt.
     */
    public boolean promptPlayers() {
        if (promptedPlayers.isEmpty()) {
            return false;
        }

        while (!promptedPlayers.isEmpty()) {
            activePlayers.offer(promptedPlayers.poll());
        }

        return true;
    }

    /**
     * If everyone but one player has folded, return the playerID that is still
     * left in the game.
     *
     * This player is either a active player, prompted player, or is an allIn
     * player.
     *
     * @return -1 if more than one player remain; otherwise, return
     *          the playerID of the last player remaining.
     */
    public int isRoundOver() {
        if (activePlayers.size() + promptedPlayers.size() + allInPlayers.size() > 1) {
            return -1;
        }
        if (!promptedPlayers.isEmpty()) {
            return promptedPlayers.peek();
        }
        if (!allInPlayers.isEmpty()) {
            return allInPlayers.get(0);
        }
        return activePlayers.peek();
    }

    /**
     * Determines if a player is in the round. (To be used with distributing
     * the pots since we don't check if a player is active).
     *
     * @param playerID the ID of the player
     * @return true if the player is in prompted players or all in players
     */
    public boolean isPlayerInRound(Integer playerID) {
        return (promptedPlayers.contains(playerID) || allInPlayers.contains(playerID));
    }

    /**
     * Determines if no one is needed to take an action
     * @return true if the active player queue is empty
     */
    public boolean isPhaseOver() {
        if (activePlayers.isEmpty()) {
            return true;
        } else if (numPlayers - allInPlayers.size() - foldedPlayers.size() < 2) {
            return true;
        }
        return false;
    }

    /**
     * Requirement: This function must be called after the removed players
     * array has been updated.
     *
     * Resets the turn order for the next round with players still in the game.
     * First, the prompted, active, allIn, and folded player arrays are
     * cleared.
     *
     * Next, add player into the active queue until there is a numPlayer
     * amount of them, followed by removing players who lost or left.
     *
     */
    public void nextRound(){
        promptedPlayers.clear();
        activePlayers.clear();
        allInPlayers.clear();
        foldedPlayers.clear();

        for(int i=0; i<numPlayers; i++){
            activePlayers.add(i);
        }

        for (int i: removedPlayers){
            activePlayers.remove((Integer)i);
        }
    }

    /**
     * Checks if the game is over.
     *
     * @return -1 if the difference between the number of player and the size
     * of removedPlayers is greater than 1; otherwise, return the playerID of
     * the only player remaining.
     */
    public int checkIfGameOver() {
        if (numPlayers - removedPlayers.size() > PLAYERS_FOR_GAME_OVER) {
            return -1;
        }
        if (!allInPlayers.isEmpty()) {
            return allInPlayers.get(allInPlayers.size() - 1);
        }
        if (!promptedPlayers.isEmpty()) {
            return promptedPlayers.get(0);
        }
        return activePlayers.get(0);
    }

    /**
     * Increment the dealer ID (being within mod numPlayers to stay within index
     * bounds) until it matches to an active player.
     *
     * Finally, make the dealerID match the first player in the active queue
     */
    public void determineBlinds() {
        dealerID = (dealerID+1)%numPlayers;
        while(!activePlayers.contains(dealerID)){
            dealerID = (dealerID+1)%numPlayers;
        }

        while(activePlayers.peek() != dealerID) {
            activePlayers.offer(activePlayers.poll());
        }
    }

    /**
     * Queues a person being forced to give a blind. If the player has no
     * more funds, call allIn(); otherwise, add them to the end of the active
     * player queue.
     *
     * @param isOutOfFunds True if the active player (being forced to bet a
     *                     blind) is out funds.
     */
    public void queueBlind(boolean isOutOfFunds) {
        if (isOutOfFunds) {
            allIn();
        } else {
            activePlayers.offer(activePlayers.poll());
        }
    }

    public int getDealerID() {
        return dealerID;
    }

    public int[] getActivePlayers() {
        int[] toReturn = new int[activePlayers.size()];
        for (int i = 0; i < toReturn.length; i++) {
            toReturn[i] = activePlayers.get(i);
        }
        return toReturn;
    }

    public void setSmallBlindID(int smallBlindID) {
        this.smallBlindID = smallBlindID;
    }

    public void setBigBlindID(int bigBlindID) {
        this.bigBlindID = bigBlindID;
    }

    public int getSmallBlindID() {
        return smallBlindID;
    }

    public int getBigBlindID() {
        return bigBlindID;
    }

    @Override
    public String toString() {
        return "Current Turn: Player " + getActivePlayerID();
    }
}
