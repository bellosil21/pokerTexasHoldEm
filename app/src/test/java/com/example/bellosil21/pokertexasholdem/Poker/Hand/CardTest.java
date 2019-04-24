package com.example.bellosil21.pokertexasholdem.Poker.Hand;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Tests the Card methods
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class CardTest {

    /**
     * Tests the CardTest() method tests by adding cards to the deck. 
     */
    @Test
    public void CardTest(){
        ArrayList<Card> deck = new ArrayList<Card>();
        for (int ranking = 0; ranking < 4; ranking++){
            deck.add(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        }
    }

}