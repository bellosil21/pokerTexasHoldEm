package com.example.bellosil21.pokertexasholdem.Poker.GameState;

import com.example.bellosil21.pokertexasholdem.Poker.Money.PlayerChipCollection;

import java.util.ArrayList;

public class PlayerChipCollector {
    public static class BetController {
        private ArrayList<PotTracker> pots;
        private ArrayList<PlayerChipCollection> players;
        private int maxBet;
        private boolean isPlayerAllIn;
        private int smallBlind;
        private int bigBlind;
        private static final int DEFAULT_STARTNG_PCOLLECTION = 500;


        public BetController(int numPlayers){
            if(numPlayers<2){
                //this should never happen right?
                return;
            }
            for(int i = 0; i<numPlayers; i++){
                players.add(new PlayerChipCollection(DEFAULT_STARTNG_PCOLLECTION, i));
            }
            pots.add(new PotTracker());

        }

        public void forceBlinds(int smallBlindID, int bigBlindID){

        }

        public boolean call(int playerID){
             int playerChips = players.get(playerID).getChips();

            if (playerChips < maxBet) {
                players.get(playerID).removeChips(playerChips);
                //pot.addChips(playerChips);
            } else {
                players.get(playerID).removeChips(maxBet);
                //pot.addChips(maxBet);
            }

            players.get(playerID).setHasCalled(true);
            return false;
        }

        public boolean check(int playerID){ return false;}

        public boolean raiseBet(int playerID, int amount){
             if (amount <= maxBet) {
                return false;
            }

            if (players.get(playerID).getChips() < amount) {
                return false;
            }

            maxBet = amount;
            // this number represents the players previous during the same
            // betting phase
            int lastBet = players.get(playerID).getLastBet();
            // removes the player's bet amount from his personal pot subtracting
            // his last bet
            players.get(playerID).removeChips(maxBet - lastBet);
            // adds the max bet to the pot
            //pots.addChips(maxBet);

            return true;
        }

        public boolean allIn(int playerID){return false;}
        public void distributePots(){}

    }
}
