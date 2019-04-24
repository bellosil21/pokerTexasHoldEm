package com.example.bellosil21.pokertexasholdem.Poker.Hand;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Tests the Deck methods
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class DeckTest {

    /**
     * Method to test if all 52 cards in a deck was created successfully
     */
    @Test
    public void deckTest(){
        Deck deck = new Deck();
        ArrayList<Card> deckReturn = deck.getDeck();

        int[] rankOcc = new int[13];
        int[] suitOcc = new int[4];

        // Counts all the cards in the deck
        for (Card card: deckReturn){
          int x = card.getRank().getValue();
          rankOcc[x]++;
          switch (card.getSuit()){
              case DIAMONDS:
                  suitOcc[0]++;
                  break;
              case SPADES:
                  suitOcc[1]++;
                  break;
              case HEART:
                  suitOcc[2]++;
                  break;
              case CLUBS:
                  suitOcc[3]++;
                  break;
          }

            for (Card card1: deckReturn) {
                if (deckReturn.indexOf(card) != deckReturn.indexOf(card1)) {
                    assertNotEquals(card, card1);
                }
            }

        }

        // Checks if all suits and ranks are within the deck
        for (int suit = 0; suit < 4; suit++){
            assertEquals(13, suitOcc[suit]);
        }

        for (int rank = 0; rank < 13; rank++){
            assertEquals(4, rankOcc[rank]);
        }
    }
}