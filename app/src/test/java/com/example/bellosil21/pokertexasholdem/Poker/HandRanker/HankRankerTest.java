package com.example.bellosil21.pokertexasholdem.Poker.HandRanker;

import com.example.bellosil21.pokertexasholdem.Poker.Hand.Card;
import com.example.bellosil21.pokertexasholdem.Poker.Hand.Hand;
import com.example.bellosil21.pokertexasholdem.Poker.HankRanker.CardCollection;
import com.example.bellosil21.pokertexasholdem.Poker.HankRanker.HandRank;
import com.example.bellosil21.pokertexasholdem.Poker.HankRanker.HandRanker;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class HankRankerTest {

    @Test
    public void straightFlush() {

        // Tests for a Straight Flush when all community cards are given
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

        // Tests for a Identifying a Straight Flush case for only three
        // community cards given
        player = new Hand();
        player.setHole1(new Card(Card.Suit.CLUBS, Card.Rank.QUEEN));
        player.setHole2(new Card(Card.Suit.CLUBS, Card.Rank.JACK));

        community = new ArrayList<>();
        community.add(new Card(Card.Suit.CLUBS, Card.Rank.EIGHT));
        community.add(new Card(Card.Suit.CLUBS, Card.Rank.NINE));
        community.add(new Card(Card.Suit.CLUBS, Card.Rank.TEN));

        hr = new HandRanker(player, community);

        straightFlush = hr.computeHandRank();

        assertEquals(HandRank.STRAIGHT_FLUSH.getValue(),
                straightFlush.getHandRank().getValue());
        assertEquals(Card.Rank.QUEEN.getValue(),
                straightFlush.getHighestRank().getValue());

        // Tests the invalidity of the case where no cards community cards are
        // given
        player = new Hand();
        player.setHole1(new Card(Card.Suit.CLUBS, Card.Rank.QUEEN));
        player.setHole2(new Card(Card.Suit.CLUBS, Card.Rank.JACK));

        community.clear();

        hr = new HandRanker(player, community);

        straightFlush = hr.computeHandRank();

        assertEquals(HandRank.HIGH_CARD.getValue(),
                straightFlush.getHandRank().getValue());
        assertEquals(Card.Rank.QUEEN.getValue(),
                straightFlush.getHighestRank().getValue());
    }

    @Test
    public void fourOfKind() {
        Hand player = new Hand();
        player.setHole1(new Card(Card.Suit.HEART, Card.Rank.EIGHT));
        player.setHole2(new Card(Card.Suit.SPADES, Card.Rank.EIGHT));

        ArrayList<Card> community = new ArrayList<>();
        community.add(new Card(Card.Suit.DIAMONDS, Card.Rank.EIGHT));
        community.add(new Card(Card.Suit.CLUBS, Card.Rank.EIGHT));
        community.add(new Card(Card.Suit.SPADES, Card.Rank.TEN));
        community.add(new Card(Card.Suit.CLUBS, Card.Rank.NINE));
        community.add(new Card(Card.Suit.HEART, Card.Rank.ACE));

        HandRanker hr = new HandRanker(player, community);

        CardCollection fourKind = hr.computeHandRank();

        assertEquals(HandRank.FOUR_OF_A_KIND.getValue(),
                fourKind.getHandRank().getValue());
        assertEquals(Card.Rank.EIGHT.getValue(),
                fourKind.getHighestRank().getValue());

        // Tests for a Identifying a Four of a Kind hand case with only three
        // community cards given
        player = new Hand();
        player.setHole1(new Card(Card.Suit.CLUBS, Card.Rank.KING));
        player.setHole2(new Card(Card.Suit.SPADES, Card.Rank.KING));

        community = new ArrayList<>();
        community.add(new Card(Card.Suit.CLUBS, Card.Rank.EIGHT));
        community.add(new Card(Card.Suit.DIAMONDS, Card.Rank.KING));
        community.add(new Card(Card.Suit.HEART, Card.Rank.KING));

        hr = new HandRanker(player, community);

        fourKind = hr.computeHandRank();

        assertEquals(HandRank.FOUR_OF_A_KIND.getValue(),
                fourKind.getHandRank().getValue());
        assertEquals(Card.Rank.KING.getValue(),
                fourKind.getHighestRank().getValue());

        // Tests for a Identifying a Four of a Kind hand case when no
        // community cards are given
        player = new Hand();
        player.setHole1(new Card(Card.Suit.CLUBS, Card.Rank.QUEEN));
        player.setHole2(new Card(Card.Suit.CLUBS, Card.Rank.JACK));

        community.clear();

        hr = new HandRanker(player, community);

        fourKind = hr.computeHandRank();

        assertNotEquals(HandRank.STRAIGHT_FLUSH.getValue(),
                fourKind.getHandRank().getValue());
        assertEquals(Card.Rank.QUEEN.getValue(),
                fourKind.getHighestRank().getValue());
    }

    @Test
    public void fullHouse() {
        Hand player = new Hand();
        player.setHole1(new Card(Card.Suit.HEART, Card.Rank.EIGHT));
        player.setHole2(new Card(Card.Suit.SPADES, Card.Rank.EIGHT));

        ArrayList<Card> community = new ArrayList<>();
        community.add(new Card(Card.Suit.DIAMONDS, Card.Rank.KING));
        community.add(new Card(Card.Suit.CLUBS, Card.Rank.KING));
        community.add(new Card(Card.Suit.SPADES, Card.Rank.TEN));
        community.add(new Card(Card.Suit.CLUBS, Card.Rank.NINE));
        community.add(new Card(Card.Suit.HEART, Card.Rank.KING));

        HandRanker hr = new HandRanker(player, community);

        CardCollection fHouse = hr.computeHandRank();

        assertEquals(HandRank.FULL_HOUSE.getValue(),
                fHouse.getHandRank().getValue());
        assertEquals(Card.Rank.KING.getValue(),
                fHouse.getHighestRank().getValue());

        // Tests for a Identifying a Full House hand case with only three
        // community cards given
        player = new Hand();
        player.setHole1(new Card(Card.Suit.CLUBS, Card.Rank.KING));
        player.setHole2(new Card(Card.Suit.SPADES, Card.Rank.KING));

        community = new ArrayList<>();
        community.add(new Card(Card.Suit.CLUBS, Card.Rank.EIGHT));
        community.add(new Card(Card.Suit.DIAMONDS, Card.Rank.EIGHT));
        community.add(new Card(Card.Suit.HEART, Card.Rank.EIGHT));

        hr = new HandRanker(player, community);

        fHouse = hr.computeHandRank();

        assertEquals(HandRank.FULL_HOUSE.getValue(),
                fHouse.getHandRank().getValue());
        assertEquals(Card.Rank.KING.getValue(),
                fHouse.getHighestRank().getValue());
    }

    @Test
    public void flush() {
        Hand player = new Hand();
        player.setHole1(new Card(Card.Suit.DIAMONDS, Card.Rank.TWO));
        player.setHole2(new Card(Card.Suit.DIAMONDS, Card.Rank.SIX));

        ArrayList<Card> community = new ArrayList<>();
        community.add(new Card(Card.Suit.DIAMONDS, Card.Rank.NINE));
        community.add(new Card(Card.Suit.DIAMONDS, Card.Rank.EIGHT));
        community.add(new Card(Card.Suit.SPADES, Card.Rank.TEN));
        community.add(new Card(Card.Suit.CLUBS, Card.Rank.NINE));
        community.add(new Card(Card.Suit.DIAMONDS, Card.Rank.ACE));

        HandRanker hr = new HandRanker(player, community);

        CardCollection fl = hr.computeHandRank();

        assertEquals(HandRank.FLUSH.getValue(),
                fl.getHandRank().getValue());
        assertEquals(Card.Rank.ACE.getValue(),
                fl.getHighestRank().getValue());

        // Tests for a Identifying a Flush hand case with only three
        // community cards given
        player = new Hand();
        player.setHole1(new Card(Card.Suit.CLUBS, Card.Rank.TWO));
        player.setHole2(new Card(Card.Suit.CLUBS, Card.Rank.SEVEN));

        community = new ArrayList<>();
        community.add(new Card(Card.Suit.CLUBS, Card.Rank.EIGHT));
        community.add(new Card(Card.Suit.CLUBS, Card.Rank.KING));
        community.add(new Card(Card.Suit.CLUBS, Card.Rank.JACK));

        hr = new HandRanker(player, community);

        fl = hr.computeHandRank();

        assertEquals(HandRank.FLUSH.getValue(),
                fl.getHandRank().getValue());
        assertEquals(Card.Rank.KING.getValue(),
                fl.getHighestRank().getValue());
    }

    @Test
    public void straightOne() {
        Hand player = new Hand();
        player.setHole1(new Card(Card.Suit.HEART, Card.Rank.FOUR));
        player.setHole2(new Card(Card.Suit.SPADES, Card.Rank.FIVE));

        ArrayList<Card> community = new ArrayList<>();
        community.add(new Card(Card.Suit.DIAMONDS, Card.Rank.JACK));
        community.add(new Card(Card.Suit.CLUBS, Card.Rank.THREE));
        community.add(new Card(Card.Suit.SPADES, Card.Rank.TEN));
        community.add(new Card(Card.Suit.CLUBS, Card.Rank.TWO));
        community.add(new Card(Card.Suit.HEART, Card.Rank.ACE));

        HandRanker hr = new HandRanker(player, community);

        CardCollection str = hr.computeHandRank();

        assertEquals(HandRank.STRAIGHT.getValue(),
                str.getHandRank().getValue());
        assertEquals(Card.Rank.FIVE.getValue(),
                str.getHighestRank().getValue());

        Hand playerNumTwo = new Hand();
        playerNumTwo.setHole1(new Card(Card.Suit.HEART, Card.Rank.QUEEN));
        playerNumTwo.setHole2(new Card(Card.Suit.SPADES, Card.Rank.FIVE));

        ArrayList<Card> communityTwo = new ArrayList<>();
        communityTwo.add(new Card(Card.Suit.DIAMONDS, Card.Rank.JACK));
        communityTwo.add(new Card(Card.Suit.CLUBS, Card.Rank.TWO));
        communityTwo.add(new Card(Card.Suit.SPADES, Card.Rank.TEN));
        communityTwo.add(new Card(Card.Suit.CLUBS, Card.Rank.KING));
        communityTwo.add(new Card(Card.Suit.HEART, Card.Rank.ACE));

        HandRanker hrTwo = new HandRanker(playerNumTwo, communityTwo);

        CardCollection strTwo = hrTwo.computeHandRank();

        assertEquals(HandRank.STRAIGHT.getValue(),
                strTwo.getHandRank().getValue());
        assertEquals(Card.Rank.ACE.getValue(),
                strTwo.getHighestRank().getValue());

    }

    @Test
    public void ThreeOfKind() {
        Hand player = new Hand();
        player.setHole1(new Card(Card.Suit.HEART, Card.Rank.ACE));
        player.setHole2(new Card(Card.Suit.SPADES, Card.Rank.EIGHT));

        ArrayList<Card> community = new ArrayList<>();
        community.add(new Card(Card.Suit.DIAMONDS, Card.Rank.ACE));
        community.add(new Card(Card.Suit.CLUBS, Card.Rank.TWO));
        community.add(new Card(Card.Suit.SPADES, Card.Rank.TEN));
        community.add(new Card(Card.Suit.CLUBS, Card.Rank.NINE));
        community.add(new Card(Card.Suit.HEART, Card.Rank.ACE));

        HandRanker hr = new HandRanker(player, community);

        CardCollection threeKind = hr.computeHandRank();

        assertEquals(HandRank.THREE_OF_A_KIND.getValue(),
                threeKind.getHandRank().getValue());
        assertEquals(Card.Rank.ACE.getValue(),
                threeKind.getHighestRank().getValue());

        // Test Case 2
        player = new Hand();
        player.setHole1(new Card(Card.Suit.HEART, Card.Rank.ACE));
        player.setHole2(new Card(Card.Suit.SPADES, Card.Rank.EIGHT));

        community = new ArrayList<>();
        community.add(new Card(Card.Suit.DIAMONDS, Card.Rank.ACE));
        community.add(new Card(Card.Suit.CLUBS, Card.Rank.ACE));
        community.add(new Card(Card.Suit.SPADES, Card.Rank.TEN));

        hr = new HandRanker(player, community);

        threeKind = hr.computeHandRank();

        assertEquals(HandRank.THREE_OF_A_KIND.getValue(),
                threeKind.getHandRank().getValue());
        assertEquals(Card.Rank.ACE.getValue(),
                threeKind.getHighestRank().getValue());
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

        CardCollection twoP = hr.computeHandRank();

        assertEquals(HandRank.TWO_PAIR.getValue(),
                twoP.getHandRank().getValue());
        assertEquals(Card.Rank.JACK.getValue(),
                twoP.getHighestRank().getValue());

        player = new Hand();
        player.setHole1(new Card(Card.Suit.HEART, Card.Rank.ACE));
        player.setHole2(new Card(Card.Suit.SPADES, Card.Rank.EIGHT));

        community = new ArrayList<>();
        community.add(new Card(Card.Suit.DIAMONDS, Card.Rank.TEN));
        community.add(new Card(Card.Suit.CLUBS, Card.Rank.ACE));
        community.add(new Card(Card.Suit.SPADES, Card.Rank.TEN));

        hr = new HandRanker(player, community);

        twoP = hr.computeHandRank();

        assertEquals(HandRank.TWO_PAIR.getValue(),
                twoP.getHandRank().getValue());
        assertEquals(Card.Rank.ACE.getValue(),
                twoP.getHighestRank().getValue());
    }

    @Test
    public void pair() {
        Hand player = new Hand();
        player.setHole1(new Card(Card.Suit.HEART, Card.Rank.TWO));
        player.setHole2(new Card(Card.Suit.SPADES, Card.Rank.EIGHT));

        ArrayList<Card> community = new ArrayList<>();
        community.add(new Card(Card.Suit.DIAMONDS, Card.Rank.JACK));
        community.add(new Card(Card.Suit.CLUBS, Card.Rank.THREE));
        community.add(new Card(Card.Suit.SPADES, Card.Rank.TEN));
        community.add(new Card(Card.Suit.CLUBS, Card.Rank.TWO));
        community.add(new Card(Card.Suit.HEART, Card.Rank.ACE));

        HandRanker hr = new HandRanker(player, community);

        CardCollection pr = hr.computeHandRank();

        assertEquals(HandRank.PAIR.getValue(),
                pr.getHandRank().getValue());
        assertEquals(Card.Rank.TWO.getValue(),
                pr.getHighestRank().getValue());

        player = new Hand();
        player.setHole1(new Card(Card.Suit.HEART, Card.Rank.TWO));
        player.setHole2(new Card(Card.Suit.SPADES, Card.Rank.EIGHT));

        community = new ArrayList<>();
        community.add(new Card(Card.Suit.DIAMONDS, Card.Rank.ACE));
        community.add(new Card(Card.Suit.HEART, Card.Rank.TWO));
        community.add(new Card(Card.Suit.SPADES, Card.Rank.TEN));

        hr = new HandRanker(player, community);

        pr = hr.computeHandRank();

        assertEquals(HandRank.PAIR.getValue(),
                pr.getHandRank().getValue());
        assertEquals(Card.Rank.TWO.getValue(),
                pr.getHighestRank().getValue());

        player = new Hand();
        player.setHole1(new Card(Card.Suit.HEART, Card.Rank.EIGHT));
        player.setHole2(new Card(Card.Suit.SPADES, Card.Rank.EIGHT));

        community = new ArrayList<>();

        hr = new HandRanker(player, community);

        pr = hr.computeHandRank();

        assertEquals(HandRank.PAIR.getValue(),
                pr.getHandRank().getValue());
        assertEquals(Card.Rank.EIGHT.getValue(),
                pr.getHighestRank().getValue());
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
