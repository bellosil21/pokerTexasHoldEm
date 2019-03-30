package com.example.bellosil21.pokertexasholdem.Poker.HankRanker;

import com.example.bellosil21.pokertexasholdem.Poker.Hand.Card;

import java.util.Comparator;

public class SortByCardCollection implements Comparator<CardCollection> {

    @Override
    public int compare(CardCollection cc1, CardCollection cc2) {
        return value(cc2) - value(cc1);
    }

    /**
     * Giving a value for a CardCollection.
     * A HandRank has a higher value over a card Rank.
     *
     * @param cc a CardCollection
     * @return the value of a CardCollection
     */
    private int value(CardCollection cc) {
        // null CardCollection are folded players, so they are lowest value
        if (cc == null) {
            return -1;
        }
        int cardRank = cc.getHighestRank().getValue();
        int handRank = cc.getHandRank().getValue();
        return cardRank + (Card.Rank.NUM_OF_RANKS * handRank);
    }
}
