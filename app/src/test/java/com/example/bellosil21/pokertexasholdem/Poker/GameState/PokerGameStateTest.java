package com.example.bellosil21.pokertexasholdem.Poker.GameState;

import com.example.bellosil21.pokertexasholdem.Poker.Hand.Card;
import com.example.bellosil21.pokertexasholdem.Poker.Hand.Deck;

import org.junit.Test;

import java.util.ArrayList;

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
        PokerGameState myGameState = new PokerGameState(0, 100, 200, 4);
        Deck myDeck = myGameState.getDeck();
        assertEquals(null, myDeck);
        myGameState.deal();
        assertNotEquals(null, myDeck);
        ArrayList<Card> cardArrayList = new ArrayList<>();

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