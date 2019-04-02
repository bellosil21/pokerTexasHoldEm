package com.example.bellosil21.pokertexasholdem.Poker.HankRanker;

import java.io.Serializable;

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

    public static final int numOfHandRanks = 10;
    private int numVal;

    HandRank(int rank){this.numVal = rank;}

    public int getValue(){return this.numVal;}
}