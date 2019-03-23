package com.example.bellosil21.pokertexasholdem.Poker.Hand;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Stores a standard 52 deck of cards.
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class Deck implements Serializable {

    private ArrayList<Card> deckOfCards;

    /**
     * A deck is composed of all permutations of suits and rankings of a card.
     */
    public Deck() {
        deckOfCards = new ArrayList<Card>();
        for (Card.Suit s : Card.Suit.values()) {
            for (Card.Rank r : Card.Rank.values()) {
                Card newCard = new Card(s, r);
                deckOfCards.add(newCard);
            }
        }

        /**
         * External Citation
         *  Date:     22 Feburary 2019
         *  Problem:  Did not know how to enumerate through all enums in a type.
         *  Resource: https://stackoverflow.com/questions/1104975/
         *            a-for-loop-to-iterate-over-an-enum-in-java
         *  Solution: Implemented the code in this example.
         */
    }

    /**
     * Copy constructor
     * Copies all cards in the deck into a new deck.
     *
     * @param toCopy the Deck to copy
     */
    public Deck(Deck toCopy) {
        deckOfCards = new ArrayList<Card>();

        for (Card c : toCopy.deckOfCards) {
            deckOfCards.add(new Card(c));
        }
    }

    /**
     * Deals all hands the top card in the deck. A card is dealt by removing
     * it to the deck and adding it to a hand.
     * We do not need to check if the deck is empty since the deck will
     * never run out of cards with 8 players or less.
     *
     * @param hands the array of player hands
     */
    public void dealPlayers(ArrayList<Hand> hands) {
        for (Hand h : hands) {
            h.setHole1(deckOfCards.remove(0));
            h.setHole2(deckOfCards.remove(0));
        }
    }

    /**
     * Describes the deck.
     *
     * @return a string describing the amount of cards left in the deck and
     * prints out all the cards in the deck
     */
    @Override
    public String toString() {
        String toReturn = deckOfCards.size() + " cards ";
        for (Card c : deckOfCards) {
            toReturn += "\n" + c.toString();
        }
        return toReturn;
    }

}
