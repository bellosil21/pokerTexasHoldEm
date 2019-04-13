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
import com.example.bellosil21.pokertexasholdem.Poker.GameInfo.PokerEndOfRound;
import com.example.bellosil21.pokertexasholdem.Poker.GameInfo.PokerIncreasingBlinds;
import com.example.bellosil21.pokertexasholdem.Poker.GameInfo.PokerPlayerOutOfFunds;
import com.example.bellosil21.pokertexasholdem.Poker.Hand.BlankCard;
import com.example.bellosil21.pokertexasholdem.Poker.Hand.Hand;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The local game to control the master PokerGameState
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class PokerLocalGame extends LocalGame implements Serializable {

    private PokerGameState state;

    private static final int STARTING_CHIPS = 100000;
    private static final int STARTING_SMALL_BLIND = 100;
    private static final int STARTING_BIG_BLIND = 200;
    private static final int SLEEP_TIME_POST_ROUND = 5000;

    public PokerLocalGame(int numPlayers) {
        Log.i("SJLocalGame", "creating game");
        // create the state for the beginning of the game
        state = new PokerGameState(STARTING_CHIPS, STARTING_SMALL_BLIND,
                STARTING_BIG_BLIND, numPlayers);
        startRound();
    }

    /**
     * Sends an updated game state to the players.
     *
     * Confidential information is hidden (player cards)
     *
     * @param p the player to send info to
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        if(p == null){
            Log.i("PokerLocalGame.java", "GamePlayer object is null");
            return;
        }
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
     * Determine whether a player is allowed to move.
     *
     * @param playerIdx The player-number of the player in question.
     */
    @Override
    protected boolean canMove(int playerIdx) {
        int activePlayer = state.getTurnTracker().getActivePlayerID();
        boolean canMove;
        return (activePlayer == playerIdx);
    }

    /**
     * Checks whether the game is over; if so, returns a string giving the result
     * otherwise, return null.
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
     * Makes a move on behalf of a player.
     *
     * @param action The action denoting the move to be made.
     * @return True if the move was legal; false otherwise.
     */
    @Override
    protected boolean makeMove(GameAction action) {
        /*check if GameAction object is null*/
        if(action == null){
            Log.i("PokerLocalGame.java", "GameAction object is null.");
            return false;
        }

        int playerID = getPlayerIdx(action.getPlayer());

        boolean isValid = false; // by default, we return false
        boolean nextTurn = false; // by default, we return false

        /*Game Actions*/
        // ALL IN
        if (action instanceof PokerAllIn) {
            allIn(playerID);
            nextTurn = true;
        }

        // CALL
        else if (action instanceof PokerCall) {
            call(playerID);
            nextTurn = true;
        }

        // CHECK
        else if (action instanceof PokerCheck) {
            nextTurn = check(playerID);
        }

        // FOLD
        else if (action instanceof PokerFold) {
            state.getTurnTracker().fold();
            nextTurn = true;
        }

        // RAISE BET
        else if (action instanceof PokerRaiseBet) {
            int raiseAmount = ((PokerRaiseBet) action).getRaiseAmount();
            nextTurn = raiseBet(playerID, raiseAmount);
        }

        // SHOW HIDE CARDS
        else if (action instanceof PokerShowHideCards) {
            boolean isShown = ((PokerShowHideCards) action).isShown();
            showHideCards(playerID, isShown);
            isValid = true;
        }

        // SIT OUT/IN
        else if (action instanceof PokerSitOut) {
            isValid = state.getTurnTracker().toggleSitting(playerID);

            // if the player is currently sitting out and it is their turn,
            // fold their hand
            if (state.getTurnTracker().isActivePlayerSittingOut()) {
                makeMove(new PokerFold(players[playerID]));
            }
        }

        if (nextTurn) {
            state.updateLastAction(playerID, action);
            endTurnCleanUp();
            return true;
        }

        return isValid;
    }


    /**
     * Tells BetController and TurnTracker that this player went all in.
     * Because all in uses up all the player's funds, the TurnTracker knows
     * to go to the next turn.
     *
     * @param playerID The ID of the player.
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
     * @param playerID ID of the player.
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
     * @return True if the action is valid.
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
     * @return True if the action is valid.
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
     * @param playerID The ID of the player showing a card.
     */
    private void showHideCards(int playerID, boolean isShown) {
        Hand playerHand  = state.getHands().get(playerID);
        playerHand.setShowCards(isShown);
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
        // check if one player is left
        int onlyPlayerLeft = state.getTurnTracker().isRoundOver();
        if (onlyPlayerLeft != -1) {

            //go to the end of the round, passing this playerID as the best rank
            int[] rankings = new int[state.getNumPlayers()];
            for (int i = 0; i < state.getNumPlayers(); i++) {
                if (onlyPlayerLeft != i) {
                    rankings[i] = Integer.MAX_VALUE;
                }
            }
            endOfRound(rankings);
        } else {

            // go to the next phase if it is over
            while (state.getTurnTracker().isPhaseOver()) {
                nextPhase();
            }
        }

        // if next player is sitting out, fold them and clean up the turn.
        if (state.getTurnTracker().isActivePlayerSittingOut()) {
            int playerID = state.getTurnTracker().getActivePlayerID();
            state.updateLastAction(playerID, new PokerFold(players[playerID]));
            state.getTurnTracker().fold();
            endTurnCleanUp();
        }
    }

    /**
     * Sets up the game for the next phase
     */
    private void nextPhase() {
        int numPhase = state.getNumPhase();
        if (numPhase == PokerGameState.PHASE_PRE_FLOP) {
            phasePreFlop();
        } else if (numPhase == PokerGameState.PHASE_FLOP) {
            phaseFlop();
        } else if (numPhase == PokerGameState.PHASE_TURN) {
            phaseTurn();
        } else if (numPhase == PokerGameState.PHASE_RIVER) {
            phaseRiver();
        } else {
            // should never occur
            Log.i("PokerLocalGame.java","The numPhase variable in " +
                    "PokerGameState.java is probably null");
        }
        state.getTurnTracker().promptPlayers();
    }

    /**
     * It is the end of the pre-fop, so set up the game for the flop
     */
    private void phasePreFlop() {
        for (int i = 0; i < PokerGameState.CARDS_FLOP; i++) {
            state.getCommunityCards().add(state.getDeck().getACard());
        }

        state.getBetController().startPhase();

        state.setNumPhase(PokerGameState.PHASE_FLOP);

    }

    /**
     * It is the end of the flop, so set up the game for the turn
     */
    private void phaseFlop() {
        state.getCommunityCards().add(state.getDeck().getACard());

        state.getBetController().startPhase();

        state.setNumPhase(PokerGameState.PHASE_TURN);
    }

    /**
     * It is the end of the turn, so set up the game for the river
     */
    private void phaseTurn() {
        state.getCommunityCards().add(state.getDeck().getACard());

        state.getBetController().startPhase();

        state.setNumPhase(PokerGameState.PHASE_RIVER);
    }

    /**
     * It is the end of the river, so distribute the pots to the winners
     */
    private void phaseRiver() {
        endOfRound(state.rankCardCollections());
    }

    /**
     * Distribute the pots and prepare the game for the next round
     *
     * @param rankings the standing of the round
     *                 index represents the player
     *                 element represents their rank (0 is high)
     */
    private void endOfRound(int rankings[]) {
        state.getBetController().distributePots(rankings);

        // reveal the cards after the end of the round
        int numFolded = 0;
        for (int i = 0; i < rankings.length; i++) {
            if (rankings[i] != Integer.MAX_VALUE) {
                state.getHands().get(i).setShowCards(true);
            } else {
                numFolded++;
            }
        }

        // only show the cards to the players if there are at least two
        // players that did not fold
        if (numFolded < players.length - 1) {
            sendAllUpdatedState();
        }

        // tell players the standings
        int[] winnings = state.getBetController().getWinnings();
        PokerEndOfRound eof = new PokerEndOfRound(winnings);
        for (GamePlayer p : players) {
            p.sendInfo(eof);
        }

        try {
            Thread.sleep(SLEEP_TIME_POST_ROUND);
        } catch (InterruptedException ex) {
            // continue
        }

        state.hideHands();

        state.getBetController().asynchronousReset();

        //remove players who are out of funds and set their cards to blank cards
        ArrayList<Integer> newlyRemovedPlayers = new ArrayList<>();
        for (int i = 0; i < state.getNumPlayers(); i++) {
            if (state.getBetController().getPlayerChips(i) == 0) {
                boolean newPlayerIsOut = state.getTurnTracker().remove(i);
                if (newPlayerIsOut) {
                    newlyRemovedPlayers.add(i);
                }
                state.getHands().get(i).setHole1(new BlankCard());
                state.getHands().get(i).setHole2(new BlankCard());

            }
        }
        if (!newlyRemovedPlayers.isEmpty()) {
            tellPlayersAPlayerIsOutOfFunds(newlyRemovedPlayers);
        }

        state.getTurnTracker().nextRound();

        // if no one left, do nothing. the game framework should notice
        if (state.getTurnTracker().checkIfGameOver() != -1) {
            return;
        }

        state.incrementRoundNumber();

        // Increase the blinds if we are at a multiple of rounds per blind
        // increment. Also, inform the players.
        if (state.getRoundNumber() % PokerGameState.ROUNDS_PER_BLIND_INCREMENT == 0) {
            state.getBetController().incrementBlinds();
            int newSmall = state.getBetController().getSmallBlind();
            int newBig = state.getBetController().getBigBlind();
            tellPlayersIncreasingBlinds(newSmall, newBig);
        }

        //reset the game actions for the new round
        for (int i = 0; i < state.getLastActions().size(); i++) {
            state.updateLastAction(i, null);
        }

        startRound();
    }

    /**
     * Starts the next round by setting up the new cards and forcing the
     * blinds to contribute to the pot
     */
    private void startRound() {
        state.getCommunityCards().clear();
        state.getDeck().reset();
        state.deal();

        state.getTurnTracker().determineBlinds();

        // get the small blind player, make them bet, and tell the turn
        // tracker the result
        int smallBlindID = state.getTurnTracker().getActivePlayerID();
        state.getTurnTracker().setSmallBlindID(smallBlindID);
        boolean forcedAllInSB = state.getBetController().forceSmallBlinds(smallBlindID);
        state.getTurnTracker().queueBlind(forcedAllInSB);

        // get the big blind player, make them bet, and tell the turn
        // tracker the result
        int bigBlindID = state.getTurnTracker().getActivePlayerID();
        state.getTurnTracker().setBigBlindID(bigBlindID);
        boolean forcedAllInBB = state.getBetController().forceBigBlinds(bigBlindID);
        state.getTurnTracker().queueBlind(forcedAllInBB);

        state.setNumPhase(PokerGameState.PHASE_PRE_FLOP);
    }

    /**
     * Sends players a PokerIncreasingBlinds GameInfo
     *
     * @param newSmall the new small blind
     * @param newBig the new big blind
     */
    private void tellPlayersIncreasingBlinds(int newSmall, int newBig) {
        PokerIncreasingBlinds info = new PokerIncreasingBlinds(newSmall,
                newBig);
        for (GamePlayer p : players) {
            p.sendInfo(info);
        }
    }

    /**
     * Sends players a PokerPlayerOutOfFunds GameIngo
     *
     * @param playerIDs the playerID who is out of funds
     */
    private void tellPlayersAPlayerIsOutOfFunds(ArrayList<Integer> playerIDs) {
        PokerPlayerOutOfFunds info = new PokerPlayerOutOfFunds(playerIDs);
        for (GamePlayer p : players) {
            p.sendInfo(info);
        }
    }

}