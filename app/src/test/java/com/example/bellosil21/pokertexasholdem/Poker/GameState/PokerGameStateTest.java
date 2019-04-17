package com.example.bellosil21.pokertexasholdem.Poker.GameState;

import com.example.bellosil21.pokertexasholdem.Poker.Hand.Deck;

import org.junit.Test;

import static org.junit.Assert.*;

public class PokerGameStateTest {

    @Test
    public void nextPhase() {
        PokerGameState myGameState = new PokerGameState(0, 100, 200, 4);
    }

    @Test
    public void endOfRound() {
        PokerGameState myGameState = new PokerGameState(0, 100, 200, 4);
    }

    @Test
    public void deal() {
        dealHealer(1);
        dealHealer(4);
    }

    private void dealHealer(int numPlayers) {
        PokerGameState myGameState = new PokerGameState(0, 100, 200, numPlayers);
        Deck myDeck = myGameState.getDeck();
        assertEquals(52, myDeck.getDeck().size()); // a new deck has 52 cards
        myGameState.deal();
        assertNotEquals(52 - numPlayers * 2, myDeck);
    }

    @Test
    public void getBetController() {
        //not necessary.
    }

    @Test
    public void getTurnTracker() {
        //not necessary.
    }


    @Test
    public void getChips() {
        //not necessary.
    }

    @Test
    public void getCommunityCards() {
        //not necessary.
    }

    @Test
    public void getHands() {
        //not necessary.
    }
}