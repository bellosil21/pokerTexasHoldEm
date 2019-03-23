package com.example.bellosil21.pokertexasholdem.Poker.HankRanker;

import com.example.bellosil21.pokertexasholdem.Poker.Hand.Card;

/**
 * Stores the a poker hand that is associated with a hank ranking
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class CardCollection {

    private Card[] cards;
    private HandRank handRank;
    private Card.Rank rank;

    public CardCollection(Card[] cards, HandRank handRank) {
        this.cards = new Card[cards.length];
        for (int i = 0; i < cards.length; i++) {
            this.cards[i] = cards[i];
        }
        this.handRank = handRank;

        //TODO: Calculate high card
    }

    public Card[] getCards() {
        return cards;
    }

    public HandRank getHandRank() {
        return handRank;
    }

    public Card.Rank getRank() {
        return rank;
    }
}
