package com.example.bellosil21.pokertexasholdem.Poker.Hand;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Used for sorting cards in descending order of rank
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */

public class SortCardByRank implements Comparator<Card>, Serializable {

    @Override
    public int compare(Card card1, Card card2) {
        return card2.getRank().getValue() - card1.getRank().getValue();
    }
    /**
     * External Citation
     *  Date:     22 March 2019
     *  Problem:  I wanted to know if there was a built in method to sort an array list.
     *  Resource: https://www.geeksforgeeks.org/collections-sort-java-examples/
     *  Solution: Implemented the code in this example.
     */
}
