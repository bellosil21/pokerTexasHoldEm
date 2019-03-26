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

    public CardSlot getHole1() { return this.hole1; }

    public CardSlot getHole2() { return this.hole2; }

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
