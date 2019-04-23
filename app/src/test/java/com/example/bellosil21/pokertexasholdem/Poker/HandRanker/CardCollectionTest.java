package com.example.bellosil21.pokertexasholdem.Poker.HandRanker;

import com.example.bellosil21.pokertexasholdem.Poker.Hand.Card;
import com.example.bellosil21.pokertexasholdem.Poker.Hand.Hand;
import com.example.bellosil21.pokertexasholdem.Poker.HankRanker.CardCollection;
import com.example.bellosil21.pokertexasholdem.Poker.HankRanker.HandRank;
import com.example.bellosil21.pokertexasholdem.Poker.HankRanker.HandRanker;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class CardCollectionTest {

    @Test
    public void compareTo() {
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

        int compare = fullHouse.compareTo(fourOfAKing);
        assertEquals(-1, compare);

        compare = fourOfAKing.compareTo(fullHouse);
        assertEquals(1, compare);

        compare = fourOfAKing.compareTo(fourOfAKing);
        assertEquals(0, compare);

    }

}
