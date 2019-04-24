package com.example.bellosil21.pokertexasholdem.Poker.GameState;

import com.example.bellosil21.pokertexasholdem.Poker.Hand.Deck;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the PokerGameState
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class PokerGameStateTest {

    /**
     * Tests the copy constructor of the PokerGameState by creating instances with different
     * constructor values. For each instance, the deal method is called to instantiate the
     * deck for each state. Additionally, the round number and phase number is incremented
     * differently for each object of the GameState. Once all the data is set, we assert that
     * data that should be passed is copied, and data that shouldn't be passed is not copied.
     */
    @Test
    public void copyConstructorTest2(){
        PokerGameState state1 = new PokerGameState(5000, 600, 1200, 4);
        PokerGameState state2 = new PokerGameState(2000, 200, 400, 3);
        PokerGameState state3 = new PokerGameState(1000, 50, 100, 2);

        state1.deal();
        state1.incrementRoundNumber();
        state1.setNumPhase(2);

        state2.deal();
        state2.incrementRoundNumber();
        state2.incrementRoundNumber();
        state2.setNumPhase(3);

        PokerGameState copyOfState1 = new PokerGameState(state1,
                state1.getTurnTracker().getActivePlayerID());

        //need to assert that the deck was not copied.
        assertNotEquals(state1.getDeck(), copyOfState1.getDeck());
        assertNull(copyOfState1.getDeck());
        assertNotNull(state1.getDeck());;
        //need to assert that the copy can't view the hands of the other players
        assertNotEquals(state1.getHands(), copyOfState1.getHands());
        assertEquals(state1.getCommunityCards(), copyOfState1.getCommunityCards());
        assertEquals(state1.getRoundNumber(), copyOfState1.getRoundNumber());
        assertNotEquals(state2.getRoundNumber(), copyOfState1.getRoundNumber());
        assertNotEquals(state3.getRoundNumber(), copyOfState1.getRoundNumber());
        assertEquals(state1.getNumPhase(), copyOfState1.getNumPhase());
        assertNotEquals(state2.getNumPhase(), copyOfState1.getNumPhase());
        assertEquals(state1.getNumPlayers(), copyOfState1.getNumPlayers());
        assertNotEquals(state2.getNumPlayers(), copyOfState1.getNumPlayers());
        assertNotEquals(state3.getNumPlayers(), copyOfState1.getNumPlayers());
        //simply checking to see if the variables that need to be the same are in fact the same.
        assertEquals(state1.getTurnTracker().getDealerID(),
                copyOfState1.getTurnTracker().getDealerID());
        assertEquals(state1.getTurnTracker().getBigBlindID(),
                copyOfState1.getTurnTracker().getBigBlindID());
        assertEquals(state1.getTurnTracker().getSmallBlindID(),
                copyOfState1.getTurnTracker().getSmallBlindID());
        //same with the betController object.
        assertEquals(state1.getBetController().getMaxBet(),
                copyOfState1.getBetController().getMaxBet());
        assertArrayEquals(state1.getBetController().getWinnings(),
                copyOfState1.getBetController().getWinnings());
        //assert that the GameInfo array lists are equal.
        assertEquals(state1.getLastActions(), copyOfState1.getLastActions());
    }

    @Test
    public void deal() {
        dealHealer(1);
        dealHealer(4);
    }

    private void dealHealer(int numPlayers) {
        PokerGameState myGameState = new PokerGameState(0, 100, 200, numPlayers);
        Deck myDeck = myGameState.getDeck();
        assertEquals(52, myDeck.getDeck().size()); // a new deck has 52 cards
        myGameState.deal();
        assertNotEquals(52 - numPlayers * 2, myDeck);
    }
}