package com.example.bellosil21.pokertexasholdem.Poker.GameState;

import android.util.Log;

import com.example.bellosil21.pokertexasholdem.Game.GamePlayer;
import com.example.bellosil21.pokertexasholdem.Game.LocalGame;
import com.example.bellosil21.pokertexasholdem.Game.actionMsg.GameAction;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerAllIn;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerCall;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerCheck;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerFold;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerRaiseBet;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerShowHideCards;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerSitOut;
import com.example.bellosil21.pokertexasholdem.Poker.Hand.Hand;

public class PokerLocalGame extends LocalGame {

    private PokerGameState state;

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
        if(p == null){
            Log.i("PokerLocalGame.java", "GamePlayer object is null");
            return;
        }
        // if there is no state to send, ignore
        if (state == null) { //addd a log.i call here
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
        return (activePlayer == playerIdx);
    }

    /**
     * checks whether the game is over; if so, returns a string giving the result
     * otherwise, return null
     */
    @Override
    protected String checkIfGameOver() {
        int check = state.getTurnTracker().checkIfGameOver();
        if (check < 0) {
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
        /*check if GameAction object is null*/
        if(action == null){
            Log.i("PokerLocalGame.java", "GameAction object is nulll come fix it.");
            return false;
        }

        boolean isValid = false; // by default, we return false
        boolean nextTurn = false; // by default, we return false

        // ALL IN
        if (action instanceof PokerAllIn) {
            int playerID = getPlayerIdx(action.getPlayer());
            allIn(playerID);
            nextTurn = true;
        }

        // CALL
        else if (action instanceof PokerCall) {
            int playerID = getPlayerIdx(action.getPlayer());
            call(playerID);
            nextTurn = true;
        }

        // CHECK
        else if (action instanceof PokerCheck) {
            int playerID = getPlayerIdx(action.getPlayer());
            nextTurn = check(playerID);
        }

        // FOLD
        else if (action instanceof PokerFold) {
            state.getTurnTracker().fold();
            nextTurn = true;
        }

        // RAISE BET
        else if (action instanceof PokerRaiseBet) {
            int playerID = getPlayerIdx(action.getPlayer());
            int raiseAmount = ((PokerRaiseBet) action).getRaiseAmount();
            nextTurn = raiseBet(playerID, raiseAmount);
        }

        // SHOW HIDE CARDS
        else if (action instanceof PokerShowHideCards) {
            int playerID = getPlayerIdx(action.getPlayer());
            toggleShowHideCards(playerID);

            isValid = true;
        }

        // SIT OUT/IN
        else if (action instanceof PokerSitOut) {
            int playerID = getPlayerIdx(action.getPlayer());
            state.getTurnTracker().toggleSitting(playerID);

            isValid = true;
        }

        if (nextTurn) {
            int playerID = getPlayerIdx(action.getPlayer());
            state.updatelastAction(playerID, action);
            endTurnCleanUp();
            return true;
        }

        return isValid;
    }

    /* Game Actions */

    /**
     * Tells BetController and TurnTracker that this player went all in.
     * Because all in uses up all the player's funds, the TurnTracker knows
     * to go to the next turn.
     *
     * @param playerID the ID of the player
     */
    private void allIn(int playerID) {
        boolean maxBetChanged = state.getBetController().allIn(playerID);

        // if the max bet changed, prompt the players
        if (maxBetChanged) {
            state.getTurnTracker().promptPlayers();
        }

        state.getTurnTracker().allIn();
    }

    /**
     * Tells the BetController that we called. If we used up all our funds,
     * tell the turn tracker; otherwise, manually go to next turn.
     *
     * @param playerID ID of the player
     */
    private void call(int playerID) {
        boolean usedAllFunds = state.getBetController().call(playerID);

        // we need to keep track if the player used all their funds
        if (usedAllFunds) {
            state.getTurnTracker().allIn();
            return;
        }

        state.getTurnTracker().nextTurn();
    }

    /**
     * Determine if the check is valid. If so, manually go to the next turn.
     *
     * @return true if the action is valid
     */
    private boolean check(int playerID) {
        boolean isValid = state.getBetController().check(playerID);
        if (!isValid) {
            return false;
        }

        state.getTurnTracker().nextTurn();
        return true;
    }


    /**
     * Passes the raiseBet action to the BetController.
     * If the bet is illegal, return false.
     * If the bet is valid and the player has no funds, tell the turn tracker
     * and return true.
     * If the bet is valid and the player still has funds, manually go to the
     * next turn and return true
     *
     * Players need to be prompted if the maxBet is raised before we tell
     * move to the next turn.
     *
     * @param playerID the ID of the player giving the action
     * @param raiseAmount   the amount that the player is submitting
     * @return true if the action is valid
     */
    private boolean raiseBet(int playerID, int raiseAmount) {
        int raiseReturn = state.getBetController().raiseBet(playerID,
                raiseAmount);

        if (raiseReturn == BetController.RAISE_INVALID) {
            return false;
        }
        else if (raiseReturn == BetController.RAISE_NO_FUNDS_LEFT) {
            state.getTurnTracker().promptPlayers();
            state.getTurnTracker().allIn();
            return true;
        }
        else if (raiseReturn == BetController.RAISE_FUNDS_LEFT) {
            state.getTurnTracker().promptPlayers();
            state.getTurnTracker().nextTurn();
            return true;
        }

        //this should never run since we taken care of all possibilities
        return false;
    }

    /**
     * If a player is showing their cards, hide them.
     * If a player is hiding their cards, show them.
     *
     * @param playerID the ID of the player showing a card
     */
    private void toggleShowHideCards(int playerID) {
        Hand playerHand  = state.getHands().get(playerID);

        boolean isShown = playerHand.isShowCards();
        playerHand.setShowCards(!isShown);
    }

    /**
     * After every valid turn, we determine if the betting phase is over.
     * We start by folding the next player if they are sitting out.
     *
     * Then, we see if everyone has folded. If so, we giving them the highest
     * rank and end the round.
     *
     * Otherwise, we check to see if we need to go to the next phase.
     */
    private void endTurnCleanUp() {
        // if next player is sitting out, fold them
        if (state.getTurnTracker().isActivePlayerSittingOut()) {
            state.getTurnTracker().fold();
        }

        // check if one player is left
        int onlyPlayerLeft = state.getTurnTracker().isRoundOver();
        if (onlyPlayerLeft != -1) {

            //go to the end of the round, passing this playerID as the best rank
            int[] rankings = new int[numPlayers];
            for (int i = 0; i < numPlayers; i++) {
                if (onlyPlayerLeft != i) {
                    rankings[i] = Integer.MAX_VALUE;
                }
            }
            state.endOfRound(rankings);
        }

        // check if everyone has been prompted
        else if (state.getTurnTracker().isPhaseOver()) {
            //TODO: if last round, show all cards and sleep thread

            state.nextPhase();
        }
    }
}
