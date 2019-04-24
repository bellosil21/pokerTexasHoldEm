package com.example.bellosil21.pokertexasholdem.Poker.HandRanker;

import com.example.bellosil21.pokertexasholdem.Poker.Hand.Card;
import com.example.bellosil21.pokertexasholdem.Poker.Hand.Hand;
import com.example.bellosil21.pokertexasholdem.Poker.HankRanker.CardCollection;
import com.example.bellosil21.pokertexasholdem.Poker.HankRanker.HandRank;
import com.example.bellosil21.pokertexasholdem.Poker.HankRanker.HandRanker;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Tests a CardCollection
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class CardCollectionTest {

    /**
     * Method check to see the functionality of comparing between two hands.
     * It is expected that the method will determine which type of hand is
     * higher over the other.
     */
    @Test
    public void compareTo() {
        // Creates a new player's hand given a certain set of community cards
        Hand player = new Hand();
        player.setHole1(new Card(Card.Suit.HEART, Card.Rank.ACE));
        player.setHole2(new Card(Card.Suit.HEART, Card.Rank.KING));

        ArrayList<Card> community = new ArrayList<>();
        community.add(new Card(Card.Suit.SPADES, Card.Rank.KING));
        community.add(new Card(Card.Suit.CLUBS, Card.Rank.KING));
        community.add(new Card(Card.Suit.DIAMONDS, Card.Rank.KING));
        community.add(new Card(Card.Suit.HEART, Card.Rank.NINE));
        community.add(new Card(Card.Suit.HEART, Card.Rank.EIGHT));

        HandRanker hr = new HandRanker(player, community);

        CardCollection fourOfAKing = hr.computeHandRank();
        assertEquals(HandRank.FOUR_OF_A_KIND, fourOfAKing.getHandRank());
        assertEquals(Card.Rank.KING, fourOfAKing.getHighestRank());

        // Creates another player's hand with a new array of community cards
        player = new Hand();
        player.setHole1(new Card(Card.Suit.CLUBS, Card.Rank.TWO));
        player.setHole2(new Card(Card.Suit.HEART, Card.Rank.TWO));

        community = new ArrayList<>();
        community.add(new Card(Card.Suit.DIAMONDS, Card.Rank.EIGHT));
        community.add(new Card(Card.Suit.CLUBS, Card.Rank.EIGHT));
        community.add(new Card(Card.Suit.DIAMONDS, Card.Rank.KING));
        community.add(new Card(Card.Suit.HEART, Card.Rank.NINE));
        community.add(new Card(Card.Suit.HEART, Card.Rank.EIGHT));

        hr = new HandRanker(player, community);

        CardCollection fullHouse = hr.computeHandRank();
        assertEquals(HandRank.FULL_HOUSE, fullHouse.getHandRank());
        assertEquals(Card.Rank.EIGHT, fullHouse.getHighestRank());

        // Compares two hands and determines which hand is higher
        int compare = fullHouse.compareTo(fourOfAKing);
        assertEquals(-1, compare);

        compare = fourOfAKing.compareTo(fullHouse);
        assertEquals(1, compare);

        compare = fourOfAKing.compareTo(fourOfAKing);
        assertEquals(0, compare);

    }

}
