package com.example.bellosil21.pokertexasholdem.Poker.Hand;

import java.io.Serializable;

/**
 * Defines the cards in a player's hand.
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class Hand implements Serializable {

    //Declares the cards in a player's hand
    private CardSlot hole1;
    private CardSlot hole2;
    //Determines if other players can see this player's cards
    private boolean showCards;

    public enum HandRank{
        ROYAL_FLUSH(1),
        STRAIGHT_FLUSH(2),
        FOUR_OF_A_KIND(3),
        FULL_HOUSE(4),
        FLUSH(5),
        STRAIGHT(6),
        THREE_OF_A_KIND(7),
        TWO_PAIR(8),
        PAIR(9),
        HIGH_CARD(10);

        public static final int numOfHandRanks = 10;
        private int numVal;

        HandRank(int rank){this.numVal = rank;}

        public int getRank(){return this.numVal;}
    }

    /**
     * Initializes a hand to have two blank cards.
     */
    public Hand() {
        this.hole1 = new BlankCard();
        this.hole2 = new BlankCard();
        showCards = false;
    }

    /**
     * Copy constructor
     * Copies the cards into a new hand.
     *
     * @param toCopy the Hand to copy
     */
    public Hand(Hand toCopy) {
        hole1 = new Card((Card) toCopy.hole1);
        hole2 = new Card((Card) toCopy.hole2);
        showCards = toCopy.showCards;
    }

    /**
     * Sets the boolean value if the cards are to be shown.
     * @param isShown - boolean if the cards are to be shown
     */
    public void setShowCards(boolean isShown) {
        showCards = isShown;
    }

    public boolean isShowCards() {
        return showCards;
    }

    public void setHole1(CardSlot hole1) {
        this.hole1 = hole1;
    }

    public void setHole2(CardSlot hole2) {
        this.hole2 = hole2;
    }

    /**
     * Describes the hand.
     *
     * @return a string describing the cards in hand
     */
    public String toString() {
        return "Card1: " + hole1.toString() + " " +
                "Card2 : " + hole2.toString() +
                " showCards: " + showCards;
    }

}
