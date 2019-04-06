package com.example.bellosil21.pokertexasholdem.Poker.GameState;

import android.content.Context;
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
import com.example.bellosil21.pokertexasholdem.Poker.Hand.BlankCard;
import com.example.bellosil21.pokertexasholdem.Poker.Hand.Hand;

/**
 * The local game to control the master PokerGameState
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class PokerLocalGame extends LocalGame {

    private PokerGameState state;

    private static final int STARTING_CHIPS = 1000;
    private static final int STARTING_SMALL_BLIND = 50;
    private static final int STARTING_BIG_BLIND = 100;
    private static final int NUM_PLAYERS = 4;

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

        boolean isValid = false; // by default, we return false
        boolean nextTurn = false; // by default, we return false

        /*Game Actions*/
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
            isValid = state.getTurnTracker().toggleSitting(playerID);
        }

        if (nextTurn) {
            int playerID = getPlayerIdx(action.getPlayer());
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
            int[] rankings = new int[NUM_PLAYERS];
            for (int i = 0; i < NUM_PLAYERS; i++) {
                if (onlyPlayerLeft != i) {
                    rankings[i] = Integer.MAX_VALUE;
                }
            }
            endOfRound(rankings);
        }

        // check if everyone has been prompted
        else if (state.getTurnTracker().isPhaseOver()) {
            //TODO: if last round, show all cards and sleep thread

            nextPhase();
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
    }

    /**
     * It is the end of the pre-fop, so set up the game for the flop
     */
    private void phasePreFlop() {
        for (int i = 0; i < PokerGameState.CARDS_FLOP; i++) {
            state.getCommunityCards().add(state.getDeck().getACard());
        }

        // if there are no players left to prompt, go to the text phase until
        // we end the round
        boolean startPhase = state.getTurnTracker().promptPlayers();
        if (!startPhase) {
            phaseFlop();
            return;
        }

        state.getBetController().startPhase();

        state.setNumPhase(PokerGameState.PHASE_FLOP);

    }

    /**
     * It is the end of the flop, so set up the game for the turn
     */
    private void phaseFlop() {
        state.getCommunityCards().add(state.getDeck().getACard());

        // if there are no players left to prompt, go to the text phase until
        // we end the round
        boolean startPhase = state.getTurnTracker().promptPlayers();
        if (!startPhase) {
            phaseTurn();
            return;
        }

        state.getBetController().startPhase();

        state.setNumPhase(PokerGameState.PHASE_TURN);
    }

    /**
     * It is the end of the turn, so set up the game for the river
     */
    private void phaseTurn() {
        state.getCommunityCards().add(state.getDeck().getACard());

        // if there are no players left to prompt, go to the text phase until
        // we end the round
        boolean startPhase = state.getTurnTracker().promptPlayers();
        if (!startPhase) {
            phaseRiver();
            return;
        }

        state.getBetController().startPhase();

        state.setNumPhase(PokerGameState.PHASE_RIVER);
        /** can something null happen here*/
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
        BetController betController = state.getBetController();
        TurnTracker turnTracker = state.getTurnTracker();

        betController.distributePots(rankings);

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
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            // continue
        }



        state.hideHands();
        betController.asynchronousReset();
        betController.startPhase();

        //remove players who are out of funds and set their cards to blank cards
        for (int i = 0; i < state.getNumPlayers(); i++) {
            if (betController.getPlayerChips(i) == 0) {
                turnTracker.remove(i);
                state.getHands().get(i).setHole1(new BlankCard());
                state.getHands().get(i).setHole2(new BlankCard());

            }
        }

        turnTracker.nextRound();

        //if no one left, do nothing. the game framework should notice
        if (turnTracker.checkIfGameOver() != -1) {
            return;
        }

        state.incrementRoundNumber(); // increment the round;
        if (state.getRoundNumber() % PokerGameState.ROUNDS_PER_BLIND_INCREMENT == 0) {
            betController.incrementBlinds();
        }

        //reset the game actions for the new round
        for (int i = 0; i < state.getLastActions().size(); i++) {
            state.getLastActions().set(i, null);
        }

        startRound();
    }

    /**
     * Starts the next round by setting up the new cards and forcing the
     * blinds to contribute to the pot
     */
    private void startRound() {
        BetController betController = state.getBetController();
        TurnTracker turnTracker = state.getTurnTracker();

        state.getCommunityCards().clear();
        state.getDeck().reset();
        state.deal();

        turnTracker.determineBlinds();

        // get the small blind player, make them bet, and tell the turn
        // tracker the result
        int activePlayerID = turnTracker.getActivePlayerID();
        turnTracker.setSmallBlindID(activePlayerID);
        boolean forcedAllInSB = betController.forceSmallBlinds(activePlayerID);
        turnTracker.queueBlind(forcedAllInSB);

        // get the big blind player, make them bet, and tell the turn
        // tracker the result
        activePlayerID = turnTracker.getActivePlayerID();
        turnTracker.setBigBlindID(activePlayerID);
        boolean forcedAllInBB = betController.forceBigBlinds(activePlayerID);
        turnTracker.queueBlind(forcedAllInBB);

        state.setNumPhase(PokerGameState.PHASE_PRE_FLOP);
    }


}
