package com.example.bellosil21.pokertexasholdem.Poker.GameState;

import android.util.Log;

import com.example.bellosil21.pokertexasholdem.Game.GameHumanPlayer;
import com.example.bellosil21.pokertexasholdem.Game.GamePlayer;
import com.example.bellosil21.pokertexasholdem.Game.LocalGame;
import com.example.bellosil21.pokertexasholdem.Game.actionMsg.GameAction;

public class PokerLocalGame extends LocalGame {

    PokerGameState state;

    private static final int startingChips = 1000;
    private static final int startingSmallBlind = 50;
    private static final int startingBigBling = 100;
    private static final int numPlayers = 4;

    public PokerLocalGame() {
        Log.i("SJLocalGame", "creating game");
        // create the state for the beginning of the game
        state = new PokerGameState(startingChips, startingSmallBlind,
                startingBigBling, numPlayers);
    }

    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        // if there is no state to send, ignore
        if (state == null) {
            return;
        }

        PokerGameState stateForPlayer;

        if (p instanceof GameHumanPlayer) {
            ((GameHumanPlayer)p).
        }

        // make a copy of the state; null out all cards except for the
        // top card in the middle deck
         = new PokerGameState(state, p.); // copy
        // of state
        stateForPlayer.nullAllButTopOf2(); // put nulls except for visible card

        // send the modified copy of the state to the player
        p.sendInfo(stateForPlayer);
    }

    @Override
    protected boolean canMove(int playerIdx) {
        return false;
    }

    @Override
    protected String checkIfGameOver() {
        return null;
    }

    @Override
    protected boolean makeMove(GameAction action) {
        return false;
    }

    /** Game Actions */

    /**
     * Submits a bet if it's the player's turn and goes to the next turn.
     * this is equivalent to Raise action which is why
     * we call the raise method from the PotTracker class.
     *
     * @param playerID the ID of the player giving the action
     * @param amount   the amount that the player is submitting
     * @return true if the bet is valid and it is the player's turn
     */
    public boolean raiseBet(int playerID, int amount) {

    }

    /**
     * Folds the player's hand if it's their turn and goes to the next turn.
     *
     * @param playerID the ID of the player giving the action
     * @return true if the action was valid and it is the player's turn
     */
    public boolean fold(int playerID) {

    }

    /**
     * Shows or hides a player's cards.
     *
     * @param playerID the ID of the player showing a card
     * @param isShown  true if the player wants to show their cards
     * @return true; this action is always valid
     */
    public boolean showHideCards(int playerID, boolean isShown) {

    }

    /**
     * Checks to see if it is the current player's turn and if there any
     * current bets. If not
     * return true and go to next turn.
     *
     * @param playerID ID of the player
     * @return true if the maxBet == 0 and it is the player's turn
     */
    public boolean check(int playerID) {

    }

    /**
     * Calls the current pot and goes to the next turn.
     *
     * @param playerID ID of the player
     * @return true if is the player's turn
     */
    public boolean call(int playerID) {

    }

    /**
     * Checks to see if it is the current player's turn. If it is an instance
     * of the player's
     * chip amount is set to the integer bets. Then the player's chip amount
     * is removed from
     * their personal pot and is placed as a bet.
     *
     * @param playerID the ID of the player
     * @return true if the bet was valid and it is the player's turn.
     */
    public boolean allIn(int playerID) {

    }

    public boolean toggleSitOut(int playerID)


    public boolean exit(int playerID) {

    }
}
