package com.example.bellosil21.pokertexasholdem.Poker.GameState;

import com.example.bellosil21.pokertexasholdem.Poker.Money.PlayerChipCollection;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class PokerGameStateTest {

    @Test
    public String toString() {
        return null;
    }

    @Test
    public void placeBets() {
        PokerGameState game = new PokerGameState(100, 10,
                                                    20, 4);
        boolean isLegalAction;

        isLegalAction = game.placeBets(0, 200);
        assertEquals(false, isLegalAction); // does have enough to bet
                                                    // 200;

        isLegalAction = game.placeBets(game.getTurn().getActivePlayerID(), 21);
        assertEquals(true, isLegalAction);

        isLegalAction = game.placeBets(game.getTurn().getActivePlayerID(), 20);
        assertEquals(false, isLegalAction); // cannot bet a smaller amount

        isLegalAction = game.placeBets(game.getTurn().getActivePlayerID(), 21);
        assertEquals(false, isLegalAction);  // must bet an amount greater
                                                     // than the the maxBet

        isLegalAction = game.placeBets(game.getTurn().getActivePlayerID(), 22);
        assertEquals(true, isLegalAction);
    }

    @Test
    public void fold() {
    }

    @Test
    public void showHideCards() {
    }

    @Test
    public void check() {
    }

    @Test
    public void call() {
        PokerGameState gameState = new PokerGameState(100,1,5,4);
        ArrayList<PlayerChipCollection> playersChips = null;
        int playerID = 0;
        TurnTracker turn = new TurnTracker(playersChips, playerID);
        playerID = 1234;
        gameState.call(playerID);
        assertEquals(playerID, playersChips.get(playerID));

    }

    @Test
    public void allIn() {
        PokerGameState game = new PokerGameState(100, 10,
                20, 4);

        boolean isLegalAction;

        isLegalAction = game.allIn(game.getTurn().getActivePlayerID());
        assertEquals(true, isLegalAction); // if it is the active player's
        // turn, they can always go all in

        isLegalAction = game.allIn(game.getTurn().getActivePlayerID());
        assertEquals(true, isLegalAction);

        isLegalAction = game.allIn(game.getTurn().getActivePlayerID());
        assertEquals(true, isLegalAction);

        isLegalAction = game.allIn(game.getTurn().getActivePlayerID());
        assertEquals(true, isLegalAction);
    }

    @Test
    public void exit() {
    }

    @Test
    public void deal() {
    }

    @Test
    public void rankCardCollections() {

    }
}