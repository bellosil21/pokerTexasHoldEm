package com.example.bellosil21.pokertexasholdem.Poker.HandRanker;

import com.example.bellosil21.pokertexasholdem.Poker.Hand.Card;
import com.example.bellosil21.pokertexasholdem.Poker.Hand.Hand;
import com.example.bellosil21.pokertexasholdem.Poker.HankRanker.CardCollection;
import com.example.bellosil21.pokertexasholdem.Poker.HankRanker.HandRank;
import com.example.bellosil21.pokertexasholdem.Poker.HankRanker.HandRanker;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class HankRankerTest {

    @Test
    public void straightFlush() {
        Hand player = new Hand();
        player.setHole1(new Card(Card.Suit.HEART, Card.Rank.EIGHT));
        player.setHole2(new Card(Card.Suit.HEART, Card.Rank.KING));

        ArrayList<Card> community = new ArrayList<>();
        community.add(new Card(Card.Suit.HEART, Card.Rank.QUEEN));
        community.add(new Card(Card.Suit.HEART, Card.Rank.JACK));
        community.add(new Card(Card.Suit.HEART, Card.Rank.TEN));
        community.add(new Card(Card.Suit.HEART, Card.Rank.NINE));
        community.add(new Card(Card.Suit.HEART, Card.Rank.ACE));

        HandRanker hr = new HandRanker(player, community);

        CardCollection straightFlush = hr.computeHandRank();

        assertEquals(HandRank.STRAIGHT_FLUSH.getValue(),
                straightFlush.getHandRank().getValue());
        assertEquals(Card.Rank.ACE.getValue(), straightFlush.getHighestRank().getValue());
    }

    @Test
    public void twoPair() {
        Hand player = new Hand();
        player.setHole1(new Card(Card.Suit.DIAMONDS, Card.Rank.EIGHT));
        player.setHole2(new Card(Card.Suit.CLUBS, Card.Rank.JACK));

        ArrayList<Card> community = new ArrayList<>();
        community.add(new Card(Card.Suit.CLUBS, Card.Rank.QUEEN));
        community.add(new Card(Card.Suit.HEART, Card.Rank.JACK));
        community.add(new Card(Card.Suit.DIAMONDS, Card.Rank.TEN));
        community.add(new Card(Card.Suit.HEART, Card.Rank.EIGHT));
        community.add(new Card(Card.Suit.SPADES, Card.Rank.THREE));

        HandRanker hr = new HandRanker(player, community);

        CardCollection straightFlush = hr.computeHandRank();

        assertEquals(HandRank.TWO_PAIR.getValue(),
                straightFlush.getHandRank().getValue());
        assertEquals(Card.Rank.JACK.getValue(),
                straightFlush.getHighestRank().getValue());
    }

    @Test
    public void highCard() {
        Hand player = new Hand();
        player.setHole1(new Card(Card.Suit.CLUBS, Card.Rank.THREE));
        player.setHole2(new Card(Card.Suit.SPADES, Card.Rank.QUEEN));

        ArrayList<Card> community = new ArrayList<>();
        community.add(new Card(Card.Suit.CLUBS, Card.Rank.TEN));
        community.add(new Card(Card.Suit.HEART, Card.Rank.FOUR));
        community.add(new Card(Card.Suit.DIAMONDS, Card.Rank.SEVEN));
        community.add(new Card(Card.Suit.HEART, Card.Rank.EIGHT));
        community.add(new Card(Card.Suit.SPADES, Card.Rank.TWO));

        HandRanker hr = new HandRanker(player, community);

        CardCollection straightFlush = hr.computeHandRank();

        assertEquals(HandRank.HIGH_CARD.getValue(),
                straightFlush.getHandRank().getValue());
        assertEquals(Card.Rank.QUEEN.getValue(),
                straightFlush.getHighestRank().getValue());
    }

}
