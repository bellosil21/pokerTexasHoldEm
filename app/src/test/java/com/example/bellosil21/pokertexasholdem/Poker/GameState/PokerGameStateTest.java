package com.example.bellosil21.pokertexasholdem.Poker.GameState;

import com.example.bellosil21.pokertexasholdem.Poker.Money.PlayerChipCollection;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PokerGameStateTest {

    @Test
    public String toString() {
        return null;
    }

    @Test
    public void placeBets() {
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