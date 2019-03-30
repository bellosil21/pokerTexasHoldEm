package com.example.bellosil21.pokertexasholdem.Poker.GameState;

import com.example.bellosil21.pokertexasholdem.Poker.Money.PlayerChipCollection;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class PokerGameStateTest {


    @Test
    public void placeBets() {
        PokerGameState myGameState = new PokerGameState(0,100, 200, 4);

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
        /*
        PokerGameState myGameState = new PokerGameState(0,100, 200, 4);
        int playerID =  myGameState.getTurn().getActivePlayerID();
        boolean ans = true;
        boolean ans2 = false;
        boolean val;
        boolean val2;
        val =  myGameState.fold(0);
        val2 = myGameState.fold(1);
        assertEquals(ans,val);
        assertEquals(ans2, val2);
        assertEquals(playerID, 0);
        */

        PokerGameState myGameState = new PokerGameState(0, 100, 200, 4);
        int playerID = myGameState.getTurnTracker().getActivePlayerID();
        boolean ans = true;
        boolean val;
        val = myGameState.


    }

    @Test
    public void showHideCards() {
    }

    @Test
    public void check() {
        PokerGameState myGameState = new PokerGameState(0,100, 200, 4);
        int playerID = myGameState.getTurn().getActivePlayerID();

        boolean ans = true;
        boolean ans2 = false;
        boolean val1;
        boolean val2;
        val1 = myGameState.check(0);
        val2 = myGameState.check(1); //should be false bc not current turn.
        assertEquals(ans, val1);
        assertEquals(ans2, val2);
        assertEquals(playerID, 0);
    }

    @Test
    public void call() {
        PokerGameState gameState = new PokerGameState(100,1,5,4);
        ArrayList<PlayerChipCollection> playersChips = null;
        int playerID = 0;
        //TurnTracker turn = new TurnTracker(playersChips, playerID);
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