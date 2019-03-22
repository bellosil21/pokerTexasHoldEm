package com.example.bellosil21.pokertexasholdem.Hand;

import java.io.Serializable;

/**
 * Defines a playing card, which is composed of a suit and rank.
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class Card implements Serializable, CardSlot {

    /**
     * Defines the type of suits in a standard 52 card deck.
     */
    public enum Suit {
        DIAMONDS,
        SPADES,
        CLUBS,
        HEART;

        public static final int NUM_OF_SUITS = 4;

        @Override
        public String toString() {
            return this.name();
        }
    }

    /**
     * Defines the type of rank in a standard 52 card deck
     * and assigns an integer value to weight enum.
     */
    public enum Rank {
        TWO(1),
        THREE(2),
        FOUR(3),
        FIVE(4),
        SIX(5),
        SEVEN(6),
        EIGHT(7),
        NINE(8),
        TEN(9),
        JACK(10),
        QUEEN(11),
        KING(12),
        ACE(13);

        public static final int NUM_OF_RANKS = 13;
        private int numVal;

        Rank(int numVal) {
            this.numVal = numVal;
        }

        public int getValue() {
            return numVal;
        }

        public String toString() {
            return this.name();
        }

        /**
         * External Citation
         *  Date:     22 Feburary 2019
         *  Problem:  Did not know how to give an enum a ranking.
         *  Resource: https://stackoverflow.com/questions/8811815/is-it-
         *            possible-to-assign-numeric-value-to-an-enum-in-java
         *  Solution: Implemented the code in this example.
         */
    }

    /** instance variables */
    private Suit suit;
    private Rank rank;

    /**
     * A card is composed of a suit an rank.
     *
     * @param suit a suit (diamonds, spades, clubs, hearts_
     * @param rank a rank two through ace
     */
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * Copy constructor
     * Copies the suit and rank into a new card.
     *
     * @param card the Card to copy
     */
    public Card(Card card) {
        this.suit = card.getSuit();
        this.rank = card.getRank();
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    /** toString
     * @return a String representing a card
     */
    @Override
    public String toString() {
        return rank.toString() + " of " + suit.toString();
    }

}
