package com.example.bellosil21.pokertexasholdem.Poker.HankRanker;

import com.example.bellosil21.pokertexasholdem.Poker.Hand.Card;

import java.io.Serializable;

/**
 * Stores the a poker hand that is associated with a hank ranking
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class CardCollection implements Serializable {

    // Instance variables for a card collection
    private Card[] cards;
    private HandRank handRank;

    /**
     * Constructor for a CardCollection object
     *
     * @param cards - array of cards containing the best hand for the player
     * @param handRank - the highest hand rank the player has with all cards
     *                 in the cards array accounted for
     */
    public CardCollection(Card[] cards, HandRank handRank) {
        this.cards = new Card[cards.length];
        for (int i = 0; i < cards.length; i++) {
            this.cards[i] = cards[i];
        }
        this.handRank = handRank;
    }

    public Card[] getCards() {
        return cards;
    }

    public HandRank getHandRank() {
        return handRank;
    }

    /**
     * Compares all the cards to find the highest ranking card
     *
     * @return the rank of the highest card in the collection
     */
    public Card.Rank getHighestRank() {
        boolean hasFive = false;
        Card.Rank highestRank = cards[0].getRank();

        // Compares each card and saves the highest ranking card in the list
        for (int i = 1; i < cards.length; i++) {
            if (cards[i].getRank().getValue() > highestRank.getValue()) {
                highestRank = cards[i].getRank();
            }
            if (cards[i].getRank() == Card.Rank.FIVE){
                hasFive = true;
            }
        }

        if (hasFive && highestRank.getValue() == Card.Rank.ACE.getValue()
                &&(handRank.equals(HandRank.STRAIGHT) ||
                (handRank.equals(HandRank.STRAIGHT_FLUSH)))){
            highestRank = Card.Rank.FIVE;
        }

        // Returns the highest card's rank
        return highestRank;
    }

    /**
     * Compares this CardCollection to another CardCollection based on their ranking
     * (first compares the HandRank, and then the highest card rank if HandRank is equal)
     *
     * @param other the CardCollection to compare against "this" one
     * @return 1 if this CardCollection is a higher rank
     *         -1 if this CardCollection is a lower rank
     *         0 if this CardCollection ties with the other
     */
    public int compareTo(CardCollection other) {
        if(other == null){
            return 1;
        }
        if (this.handRank.getValue() > other.handRank.getValue()) {
            return 1;
        }
        else if (this.handRank.getValue() < other.handRank.getValue()) {
            return -1;
        }
        return compareHighestCardRankTo(other);
    }

    /**
     * Compares this CardCollection to another CardCollection based on their ranking
     *
     * @param other the CardCollection to compare against "this" one
     * @return 1 if this CardCollection is a higher rank
     *         -1 if this CardCollection is a lower rank
     *         0 if this CardCollection ties with the other
     */
    public int compareHighestCardRankTo(CardCollection other) {
        int thisHighRank = this.getHighestRank().getValue();
        int otherHighRank = other.getHighestRank().getValue();

        // Checks which player has the highest Card
        if (thisHighRank > otherHighRank) {
            return 1;
        }
        else if (thisHighRank == otherHighRank) {
            return 0;
        }
        return -1;
    }

    @Override
    public String toString() {
        return "HandRank: " + this.handRank.toString();
    }
}
