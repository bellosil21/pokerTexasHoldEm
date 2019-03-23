package com.example.bellosil21.pokertexasholdem.Poker.HankRanker;

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