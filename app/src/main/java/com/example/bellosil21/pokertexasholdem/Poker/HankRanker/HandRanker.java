package com.example.bellosil21.pokertexasholdem.Poker.HankRanker;

import com.example.bellosil21.pokertexasholdem.Poker.Hand.Card;
import com.example.bellosil21.pokertexasholdem.Poker.Hand.Hand;
import com.example.bellosil21.pokertexasholdem.Poker.Hand.SortCardByRank;

import java.io.Serializable;
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
public class HandRanker implements Serializable {

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
        clubs = new ArrayList<Card>();
        spades = new ArrayList<Card>();
        diamonds = new ArrayList<Card>();
        hearts = new ArrayList<Card>();

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
                Card[] straightFlush = {new Card(cards.get(i)),
                        new Card(cards.get(i + 1)),
                        new Card(cards.get(i + 2)),
                        new Card(cards.get(i + 3)),
                        new Card(cards.get(i + 4))};
                return new CardCollection(straightFlush, HandRank.STRAIGHT_FLUSH);
            }
        }
        return null;
    }

    /**
     * Finds the best Four of a Kind hand
     *
     * @return the best Four of a Kind Card Collection
     */
    private CardCollection findFourOfAKind() {

        // Checks and rankOccurrences array to find the rank that uses all four
        // suits of the card
        int toCheck = -1;
        for (int index = rankOccurrences.length - 1; index >= 0; index--){
            if (rankOccurrences[index] == FOUR_CARD_HAND){
                toCheck = index;
                break;
            }
        }

        // Returns null no rank has four instances in the hand
        if (toCheck == -1){return null;}

        // Gets all the cards of that rank
        Card[] toReturn = new Card[FOUR_CARD_HAND];
        int ind = 0;
        for (Card card: cardSet){
            if (card.getRank().getValue() == toCheck){
                toReturn[ind] = new Card(card);
                ind++;
            }
        }

        // Creates, initializes, and returns a CardCollection object containing
        // the Four of a Kind hand and its Hand Rank type
        return new CardCollection(toReturn, HandRank.FOUR_OF_A_KIND);
    }


    /**
     * Checks community cards and the player's hand to find the best
     * hand that contains a Full House
     *
     * @return a CardCollection object containing the best Full House Hand
     */
    private CardCollection findFullHouse() {
        // instance variables for the rank that is the highest pair
        // and triple
        int pair = -1;
        int triple = -1;

        // Checks the rankOccurrences array to find the highest rankings that
        // has pairs or triples
        Card[] toReturn = new Card[FIVE_CARD_HAND];
        for (int index = rankOccurrences.length - 1; index >= 0; index--){
            // we first check the better case of triple in order since a
            // triple can appear as a triple

            // if we already found a pair/triple, we do not want to replace it
            // with a pair/triple of lower ranking since we wish to return
            // the best hand
            if (triple == -1 && rankOccurrences[index] == THREE_CARD_HAND) {
                triple = index;
            }
            else if (pair == -1 && rankOccurrences[index] >= TWO_CARD_HAND) {
                // if we already found a triple, a triple of the lower rank
                // can count as the pair
                pair = index;
            }

            if (triple >= 0 && pair >= 0){ break;}
        }

        // Checks if a pair and triple exist
        // If not, null is returned
        if (triple < 0|| pair < 0){ return null;}

        // Gets all the cards that are part of the highest triple or double
        int ind = 0;
        for (Card card: cardSet){
            if (card.getRank().getValue() == triple){
                toReturn[ind] = new Card(card);
                ind++;
            }
            else if (card.getRank().getValue() == pair){
                toReturn[ind] = new Card(card);
                ind++;
            }

            if (ind == FIVE_CARD_HAND){break;}
        }

        // Creates, initializes, and returns a CardCollection object containing
        // the Full House hand and its Hand Rank type
        return new CardCollection(toReturn, HandRank.FULL_HOUSE);
    }

    /**
     * Checks all the cards to see if there at least 5 cards of the same suit
     * in a Hand to make the highest winning flush
     *
     * @return a CardCollection object containing the highest flush possible
     * with the given cards
     */
    private CardCollection findFlush() {
        CardCollection spadesF = findFlushHelper(spades);
        CardCollection clubsF = findFlushHelper(clubs);
        CardCollection diamondsF = findFlushHelper(diamonds);
        CardCollection heartsF = findFlushHelper(hearts);

        CardCollection bestF = getHigherCardCollection(spadesF, clubsF);
        bestF = getHigherCardCollection(bestF, diamondsF);
        bestF = getHigherCardCollection(bestF, heartsF);
        return bestF;
    }

    /**
     * Checks the suit array to find which suit has at least five cards
     * and adds the highest five cards into the Hand If none exists, returns
     * null.
     *
     * @param suitArray an array of Cards of the same suit
     * @return a CardCollection of a flush if found, else null
     */
    private CardCollection findFlushHelper(ArrayList<Card> suitArray) {
        if (suitArray.size() >= FIVE_CARD_HAND){
            // we found a suit with five cards
            // now, get the first five cards in the suit array since they are
            // the highest ranking (the suit array is in descending order)

            Card[] toReturn = {new Card(suitArray.get(0)),
                    new Card(suitArray.get(1)), new Card(suitArray.get(2)),
                    new Card(suitArray.get(3)), new Card(suitArray.get(4))};

            // Creates, initializes, and returns a new CardCollection object
            return new CardCollection(toReturn, HandRank.FLUSH);
        }

        return null;
    }

    /**
     * Checks rankOccurrences to see if there exist a row of five indexes
     * where the element is not zero. If so, there is a straight and we need
     * to find it in the cardSet.
     *
     * @return a CardCollection of the best straight, else null
     */
    private CardCollection findStraight() {
        int toCheck = -1; // marker for the highest rank of the straight

        // start at the highest index and only go to index FIVE_CARD_HAND - 1
        // since the index just left of it will never create a run of 5 cards
        for (int i = rankOccurrences.length - 1; i >= FIVE_CARD_HAND - 1; i--) {
            if (rankOccurrences[i] > 0 && rankOccurrences[i - 1] > 0 &&
                    rankOccurrences[i - 2] > 0 && rankOccurrences[i - 3] > 0&&
                    rankOccurrences[i - 4] > 0) {
                toCheck = i;
                break;
            }
        }

        // return null if we didn't find a straight
        if (toCheck == -1) { return null; }

        Card[] toReturn = new Card[FIVE_CARD_HAND];

        // find card that make up the straight

        // toCheck is decreased once found until all cards the straight is found
        // because we once want one card of each rank in the straight

        int ind = 0; // tracks how many cards are in the toReturn array
                     // once it is 5, we found all the cards
        for (Card card : cardSet) {
            if (toCheck == card.getRank().getValue()) {
                toReturn[ind] = new Card(card);

                ind++;
                toCheck--;

                if (ind == toReturn.length) {
                    break;
                }
            }
        }

        return new CardCollection(toReturn, HandRank.STRAIGHT);
    }

    /**
     * Checks all given cards to find the hand with the highest
     * triple
     *
     * @return a CardCollection object with the highest Three of a Kind
     * collection
     */
    private CardCollection findThreeOfAKind() {
        int toCheck = -1;

        // Creates and finds the rank in which three instances of that kind
        // exists
        Card[] toReturn = new Card[THREE_CARD_HAND];
        for (int index = rankOccurrences.length - 1; index >=0 ; index--){
            if (rankOccurrences[index] == THREE_CARD_HAND){
                toCheck = index;
                break;
            }
        }

        // If none is found, return null
        if (toCheck == -1){return null;}


        // Adds all the cards with the highest rank to the array of cards
        // to be returned
        int ind = 0;
        for (Card card: cardSet){
            if (card.getRank().getValue() == toCheck){
                toReturn[ind] = new Card(card);
                ind++;
            }
            if (ind == THREE_CARD_HAND){break;}
        }

        // Creates, initializes, and returns a CardCollection object
        // containing the highest three of a kind hand
        return new CardCollection(toReturn, HandRank.THREE_OF_A_KIND);
    }

    /**
     * Checks all the given cards to find the highest hand involving two
     * pairs of cards for different ranks
     *
     * @return a CardCollection of two pairs if found, else null.
     */
    private CardCollection findTwoPair() {

        // Initializing the instances variables for storing the ranks
        // with pairs
        int toCheck1 = -1;
        int toCheck2 = -1;

        // Checks each rank and finds two highest ranks that has at least two
        // cards in the hand
        Card[] toReturn = new Card[FOUR_CARD_HAND];
        for (int index = rankOccurrences.length - 1; index >= 0; index--){
            if (rankOccurrences[index] >= TWO_CARD_HAND){
                if (toCheck1 < 0) {
                    toCheck1 = index;
                }
                else {
                    // if the first occurrence is found, then we found the
                    // second one and we're done
                    toCheck2 = index;
                    break;
                }
            }
        }

        // if two ranks cannot be found, return null
        if ((toCheck1 < 0) || (toCheck2 < 0)){return null;}

        // Adds all the cards with either of the two found ranks into the
        // array of cards
        int occurencesPair1 = 0; // amount of the first pair added
        int occurencesPair2 = 0; // amount of the second pair added
        int ind = 0; // keeps track of the index for the toReturn array

        // find the first two cards of the same pair and only the first two
        for (Card card: cardSet){
            if (occurencesPair1 < 2 && card.getRank().getValue() == toCheck1) {
                toReturn[ind] = new Card(card);
                ind++;
                occurencesPair1++;
            }
            else if (occurencesPair2 < 2 && card.getRank().getValue() == toCheck2) {
                toReturn[ind] = new Card(card);
                ind++;
                occurencesPair2++;
            }

            if (ind == toReturn.length) {
                break;
            }
        }

        // Creates, initializes, and returns a CardCollection object
        // containing the highest Two Pair hand and its Hand Rank type
        return new CardCollection(toReturn, HandRank.TWO_PAIR);
    }

    /**
     * Checks the rankOccurrences array to see if there is a pair. If so, find
     * the two cards of that rank in the cardSet and return it.
     *
     * @return a CardCollection for a pair if found, else null.
     */
    private CardCollection findOnePair() {

        int toCheck = -1;
        Card[] toReturn = new Card[TWO_CARD_HAND];

        // check if there is a pair
        for (int index = rankOccurrences.length - 1; index > -1 ; index--){
            if (rankOccurrences[index] == TWO_CARD_HAND){
                toCheck = index;
                break;
            }
        }

        if (toCheck == -1){return null;}

        // find the pair
        int ind = 0; // keeps track of the index for toReturn
        for (Card card: cardSet){
            if (card.getRank().getValue() == toCheck){
                toReturn[ind] = new Card(card);
                ind++;

                if (ind == TWO_CARD_HAND){break;}
            }

        }
        return new CardCollection(toReturn, HandRank.PAIR);
    }

    /**
     * Return the first card of the ordered cardSet since it is ordered in
     * descending rank.
     *
     * @return a CardCollection of the best highCard
     */
    private CardCollection findHighCard() {
        Card[] toReturn = new Card[ONE_CARD_HAND];

        toReturn[0] = new Card(cardSet.get(0));

        return new CardCollection(toReturn, HandRank.HIGH_CARD);
    }

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
    public static CardCollection getHigherCardCollection(CardCollection a,
                                             CardCollection b) {
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

}