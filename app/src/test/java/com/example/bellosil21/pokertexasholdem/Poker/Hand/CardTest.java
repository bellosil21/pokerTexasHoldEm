package com.example.bellosil21.pokertexasholdem.Poker.Hand;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CardTest {

    @Test
    public void CardTest(){
        ArrayList<Card> deck = new ArrayList<Card>();
        for (int ranking = 0; ranking < 4; ranking++){
            deck.add(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        }
    }
    @Test
    public void getRank() {
    }

    @Test
    public void getSuit() {
    }
}