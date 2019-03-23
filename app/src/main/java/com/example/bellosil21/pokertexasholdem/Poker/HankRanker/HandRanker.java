package com.example.bellosil21.pokertexasholdem.Poker.HankRanker;

import com.example.bellosil21.pokertexasholdem.Poker.Hand.Card;
import com.example.bellosil21.pokertexasholdem.Poker.Hand.Hand;
import com.example.bellosil21.pokertexasholdem.Poker.Hand.SortCardByRank;

import java.util.ArrayList;
import java.util.Collections;


/**
 * Contains the methods to calculate the best five card poker hand given 5 community
 * cards and two hole cards
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class HandRanker {
    public static final int FLUSH_REQ = 5;

    private static final int START = 0;

    private ArrayList<Card> cardSet;
    private ArrayList<Card> clubs;
    private ArrayList<Card> spades;
    private ArrayList<Card> diamonds;
    private ArrayList<Card> hearts;
    private int[] rankOccurrences;

    public HandRanker(Hand player, ArrayList<Card> community){
        cardSet = new ArrayList<Card>();

        cardSet.add((Card)player.getHole1());
        cardSet.add((Card)player.getHole2());

        for (Card card: community){
            cardSet.add(card);
        }

        rankOccurrences = new int[Card.Rank.NUM_OF_RANKS];
    }

    /**
     * Find the best five card poker hand out of the five community cards and two hole cards. We
     * will first for test the best poker hand, a straight flush, and the incrementally test until
     * the second to last poker hand, a pair, to realize when the worst poker hard is achieved. To
     * start, we will sort all array lists to get the highest ranking cards at the beginning. Then,
     * once we come across the first poker hand that firsts a given set of cards from the sorted
     * array, we know that it is the best hand out of the set.
     *
     * @return a CardCollection containing the five poker cards of the best hand, the HandRank, and
     * the highest Rank of the collection.
     */
    public CardCollection computeHandRank() {
        // sort the cardSet by rank in order to return the best 5.
        // given this sorted set we can sort each suit into a sorted
        // set to search for hands involving suits.
        // while sorting by suit, we can also count the type of ranks for pairs,
        // three of a kinds, and four of a kinds
        Collections.sort(cardSet, new SortCardByRank());

        for (Card card : cardSet) {
            suitSort(card);
            rankOccurrences[card.getRank().getValue()]++;
        }

        // test to see if we have the best 5 card poker hand, then test the next best, and
        // then the next best, and so on, until we return the worst (high card)
        CardCollection toReturn = findStraightFlush();
        if (toReturn != null) { return toReturn; }

        toReturn = findStraightFlush();
        if (toReturn != null) { return toReturn; }

        toReturn = findFourOfAKind();
        if (toReturn != null) { return toReturn; }

        toReturn = findFullHouse();
        if (toReturn != null) { return toReturn; }

        toReturn = findFlush();
        if (toReturn != null) { return toReturn; }

        toReturn = findStraight();
        if (toReturn != null) { return toReturn; }

        toReturn = findThreeOfAKind();
        if (toReturn != null) { return toReturn; }

        toReturn = findTwoPair();
        if (toReturn != null) { return toReturn; }

        toReturn = findOnePair();
        if (toReturn != null) { return toReturn; }

        return findHighCard();
    }

    //TODO
    private CardCollection findStraightFlush() { return null; }

    //TODO
    private CardCollection findFourOfAKind() { return null; }

    //TODO
    private CardCollection findFullHouse() { return null; }

    //TODO
    private CardCollection findFlush() { return null; }

    //TODO
    private CardCollection findStraight() { return null; }

    //TODO
    private CardCollection findThreeOfAKind() { return null; }

    //TODO
    private CardCollection findTwoPair() { return null; }

    //TODO
    private CardCollection findOnePair() { return null; }

    //TODO
    private CardCollection findHighCard() { return null; }

    /**
     * A helper method to be used in the constructor.
     * Places a card from the card set into the correct suit array list.
     *
     * @param card a card to be added into a suit array
     */
    private void suitSort(Card card) {
        switch (card.getSuit()) {
            case CLUBS:
                clubs.add(card);
                break;
            case SPADES:
                spades.add(card);
                break;
            case DIAMONDS:
                diamonds.add(card);
                break;
            case HEART:
                hearts.add(card);
                break;
            default:
                assert false;

        }
    }

    //



    /** storage for now
    public HandRank handType() {

        boolean flushStatus = flush(this.cardSet);

        int lowestIndex = START;

        ArrayList<Card> hand = new ArrayList<Card>();
        while (!cardSet.isEmpty()){
            for (Card card: cardSet) {
                if (card.getRank().compareTo(
                        cardSet.get(lowestIndex).getRank()) < 0){
                    lowestIndex = cardSet.indexOf(card);
                }
            }
            hand.add(cardSet.get(lowestIndex));
            cardSet.remove(lowestIndex);
            lowestIndex = START;
        }
        return null;
        //return compareHand();
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
     */
}
