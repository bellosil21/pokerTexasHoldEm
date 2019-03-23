package com.example.bellosil21.pokertexasholdem.Hand;

import com.example.bellosil21.pokertexasholdem.Poker.Hand.Card;
import com.example.bellosil21.pokertexasholdem.Poker.Hand.Hand;

import java.util.ArrayList;

import static com.example.bellosil21.pokertexasholdem.Poker.Hand.Card.Suit.NUM_OF_SUITS;

public class HandRanker {
    public static final int FLUSH_REQ = 5;

    HandRank rank;

    private static final int START = 0;

    ArrayList<Card> playerHand;

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

    public HandRanker(Hand player, ArrayList<Card> community){
        playerHand = new ArrayList<Card>();

        if (player.getHole1() instanceof Card){
            Card hole1Copy = new Card((Card)player.getHole1());
            playerHand.add(hole1Copy);
        }
        if (player.getHole2() instanceof Card){
            Card hole2Copy = new Card((Card)player.getHole2());
            playerHand.add(hole2Copy);
        }

        for (Card card: community){
            playerHand.add(card);
        }

        rank = handType();
    }

    public HandRank handType() {

        boolean flushStatus = flush(this.playerHand);

        int lowestIndex = START;

        ArrayList<Card> hand = new ArrayList<Card>();
        while (!playerHand.isEmpty()){
            for (Card card: playerHand) {
                if (card.getRank().compareTo(
                        playerHand.get(lowestIndex).getRank()) < 0){
                    lowestIndex = playerHand.indexOf(card);
                }
            }
            hand.add(playerHand.get(lowestIndex));
            playerHand.remove(lowestIndex);
            lowestIndex = START;
        }

        return compareHand();
    }

    public boolean flush(ArrayList<Card> cards) {
        int[] suitCount = new int[NUM_OF_SUITS];

        for (Card card: cards){
            switch(card.getSuit()){
                case SPADES:
                    suitCount[0]++;
                    break;
                case CLUBS:
                    suitCount[1]++;
                    break;
                case HEART:
                    suitCount[2]++;
                    break;
                case DIAMONDS:
                    suitCount[3]++;
                    break;
            }
        }

        for (int index = 0; index < NUM_OF_SUITS; index++){
            if (suitCount[index] >= FLUSH_REQ){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Integer> winningDecision(ArrayList<Hand> players,
                                              ArrayList<Card> community,
                                              ArrayList<Integer> winners,
                                              int winningHandType){

        return winners;
    }
}
