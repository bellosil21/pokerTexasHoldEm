package com.example.bellosil21.pokertexasholdem.Poker.GameState;

import android.util.Log;

import com.example.bellosil21.pokertexasholdem.Game.GameHumanPlayer;
import com.example.bellosil21.pokertexasholdem.Game.GamePlayer;
import com.example.bellosil21.pokertexasholdem.Game.LocalGame;
import com.example.bellosil21.pokertexasholdem.Game.actionMsg.GameAction;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerAllIn;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerCall;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerCheck;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerFold;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerRaiseBet;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerShowHideCards;

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

        // make the copy of the game for the player
        for (int i = 0; i < this.players.length; i++) {
            if (this.players[i].equals(p)) {
                stateForPlayer = new PokerGameState(state, i);
                p.sendInfo(stateForPlayer);
                break;
            }
        }

    }

    /**
     * whether a player is allowed to move
     *
     * @param playerIdx
     * 		the player-number of the player in question
     */
    @Override
    protected boolean canMove(int playerIdx) {
        int activePlayer = state.getTurnTracker().getActivePlayerID();
        if (activePlayer == playerIdx) {
            return true;
        }
        return false;
    }

    /**
     * checks whether the game is over; if so, returns a string giving the result
     *
     * @result
     * 		the end-of-game message, or null if the game is not over
     */
    @Override
    protected String checkIfGameOver() {
        int check = state.getTurnTracker().checkIfGameOver();
        if (check > 0) {
            return null;
        }

        return this.playerNames[check] + " is the winner!";

    }

    /**
     * makes a move on behalf of a player
     *
     * @param action
     * 		the action denoting the move to be made
     * @return
     * 		true if the move was legal; false otherwise
     */
    @Override
    protected boolean makeMove(GameAction action) {
        boolean isValid = false;

        // ALL IN
        if (action instanceof PokerAllIn) {
            int playerID = getPlayerIdx(action.getPlayer());
            state.getBetController().allIn(playerID);
            isValid = true;
        }

        // CALL
        else if (action instanceof PokerCall) {
            int playerID = getPlayerIdx(action.getPlayer());
            boolean usedAllFunds = state.getBetController().call(playerID);

            // we need to keep track if the player used all their funds
            if (usedAllFunds) {
                state.getTurnTracker().allIn();
            }

            isValid = true;
        }

        // CHECK
        else if (action instanceof PokerCheck) {
            isValid = state.getBetController().check();
        }

        // FOLD
        else if (action instanceof PokerFold) {
            state.getTurnTracker().fold();

            isValid = true;
        }

        // RAISE BET
        else if (action instanceof PokerRaiseBet) {
            int playerID = getPlayerIdx(action.getPlayer());
            int raiseAmount = ((PokerRaiseBet) action).getRaiseAmount();

            int raiseReturn = state.getBetController().raiseBet(playerID,
                    raiseAmount);

            if (raiseReturn == BetController.RAISE_INVALID) {
                isValid = false;
            }
            else if (raiseReturn == BetController.RAISE_NO_FUNDS_LEFT) {
                state.getTurnTracker().allIn();
                isValid = true;
            }
            else if (raiseReturn == BetController.RAISE_FUNDS_LEFT) {
                isValid = true;
            }
            else {
                // this should never happen
                isValid = false;
            }
        }

        // SHOW HIDE CARDS
        else if (action instanceof PokerShowHideCards) {

        }


        return isValid;
    }

    /* Game Actions */

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
