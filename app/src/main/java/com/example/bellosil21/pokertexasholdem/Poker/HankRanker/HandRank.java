package com.example.bellosil21.pokertexasholdem.Poker.HankRanker;

import java.io.Serializable;

/**
 * Class to define the different types of hands possible with a collection of
 * cards
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public enum HandRank implements Serializable {
    STRAIGHT_FLUSH(8),
    FOUR_OF_A_KIND(7),
    FULL_HOUSE(6),
    FLUSH(5),
    STRAIGHT(4),
    THREE_OF_A_KIND(3),
    TWO_PAIR(2),
    PAIR(1),
    HIGH_CARD(0);

    private int numVal;

    HandRank(int rank){
        this.numVal = rank;
    }

    public int getValue(){
        return this.numVal;
    }
}