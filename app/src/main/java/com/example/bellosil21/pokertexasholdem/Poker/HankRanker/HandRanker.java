package com.example.bellosil21.pokertexasholdem.Poker.HankRanker;

import com.example.bellosil21.pokertexasholdem.Poker.Hand.Card;
import com.example.bellosil21.pokertexasholdem.Poker.Hand.Hand;
import com.example.bellosil21.pokertexasholdem.Poker.Hand.SortCardByRank;

import java.lang.reflect.Array;
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

    /** instance vars **/
    private ArrayList<Card> cardSet;
    private ArrayList<Card> clubs;
    private ArrayList<Card> spades;
    private ArrayList<Card> diamonds;
    private ArrayList<Card> hearts;
    private int[] rankOccurrences;

    /** constants **/
    //public static final int FLUSH_REQ = 5;
    //private static final int START = 0;
    private static final int FIVE_CARD_HAND = 5;
    private static final int FOUR_CARD_HAND = 4;
    private static final int THREE_CARD_HAND = 3;
    private static final int TWO_CARD_HAND = 2;
    private static final int ONE_CARD_HAND = 1;

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
    private CardCollection computeHandRank() {
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

    /**
     * Get the best StraightFLush from each suit array. Then, return the best one.
     * Otherwise, return null.
     *
     * Both helper methods used here handle null cases.
     *
     * @return the CardCollection containing the best StraightFlush; otherwise, null
     */
    private CardCollection findStraightFlush() {
        CardCollection spadesSF = findStraightFlushHelper(spades);
        CardCollection clubsSF = findStraightFlushHelper(clubs);
        CardCollection diamondsSF = findStraightFlushHelper(diamonds);
        CardCollection heartsSF = findStraightFlushHelper(hearts);

        CardCollection bestSF = getHigherCardCollection(spadesSF, clubsSF);
        bestSF = getHigherCardCollection(bestSF, diamondsSF);
        bestSF = getHigherCardCollection(bestSF, heartsSF);
        return bestSF;
    }

    /**
     * If there exist a subset of a suit array such that the subset's rank values, n, are
     * {n, n-1, n-2, n-3, n-4}, we return a CardCollection representing those cards, with a
     * HandRank of StraightFlush. Otherwise, return null.
     * Note: The arrays of suits should be ordered from the card with highest rank to the card
     * with least ranking.
     *
     * @param cards an array of cards from the same suit
     * @return the CardCollection of the best StraightFlush if there is one; otherwise, null
     */
    private CardCollection findStraightFlushHelper(ArrayList<Card> cards) {
        // only loop while there are 5 cards remaining in the array (the current index + 4)
        for (int i = 0; i < cards.size() - FIVE_CARD_HAND + 1; i++) {
            int highestRank = cards.get(i).getRank().getValue();
            int nextRank1 = cards.get(i + 1).getRank().getValue();
            int nextRank2 = cards.get(i + 2).getRank().getValue();
            int nextRank3 = cards.get(i + 3).getRank().getValue();
            int lowestRank = cards.get(i + 4).getRank().getValue();

            // check if this subset obeys {n, n-1, n-2, n-3, n-4}
            // if so, this subset is a StraightFlush
            // otherwise, go to next iteration
            if (highestRank == (nextRank1 + 1) &&
                    highestRank == (nextRank2 + 2) &&
                    highestRank == (nextRank3 + 3) &&
                    highestRank == (lowestRank + 4)) {
                Card[] straightFlush = {cards.get(i), cards.get(i + 1),
                        cards.get(i + 2), cards.get(i + 3),
                        cards.get(i + 4)};
                return new CardCollection(straightFlush, HandRank.STRAIGHT_FLUSH);
            }
        }
        return null;
    }

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

    /**
     * Returns the higher ranking CardCollection from two CardCollections.
     * If they are equal in rank, it is arbitrary which one we return. For our
     * implementation, we return the first CardCollection a.
     *
     * This method handles null CardCollections. If there is only one null
     * collection, return the one that isn't null.
     * If both are null, return null.
     *
     * @param a the first CardCollection to compare
     * @param b the second CardCollection to compare
     * @return the higher ranking CardCollection
     */
    private CardCollection getHigherCardCollection(CardCollection a, CardCollection b) {
        //handing null cases
        if (a == null && b == null) {
            return null;
        }
        else if (a == null) {
            return b;
        }
        else if (b == null) {
            return a;
        }

        //non-null cases
        int compareTo = a.compareTo(b);
        if (compareTo > 1) {
            return a;
        }
        else if (compareTo < 1) {
            return b;
        }
        return a;
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
