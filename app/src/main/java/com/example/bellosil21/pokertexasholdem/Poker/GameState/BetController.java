package com.example.bellosil21.pokertexasholdem.Poker.GameState;

import com.example.bellosil21.pokertexasholdem.Poker.Money.PlayerChipCollection;

import java.util.ArrayList;

public class BetController {
    private ArrayList<PotTracker> pots;
    private ArrayList<PlayerChipCollection> players;
    private int maxBet; //added a getter method for this shit bois.
    private boolean isPlayerAllIn;
    private int smallBlind;
    private int bigBlind;
    private int numberOfPlayers;

    public BetController(int numPlayers){
        if(numPlayers < 2){
            /** invalid number of players nah?
             *
             */
        }
        for(int i = 0; i<numPlayers; i++){
            players.add(new PlayerChipCollection(0, i)); //i will be the id of player.
        }
        pots.add(new PotTracker());
        this.maxBet = 0;
        this.isPlayerAllIn = false;
        this.smallBlind = 0;
        this.bigBlind = 0;
        this.numberOfPlayers = numPlayers;
    }

    /*not sure what you want this to do*/
    public void forceBlinds(int smallBlindID, int bigBlindID){}

    public boolean call(int playerID){
        /* we should put a unit test here to test if player chips is the amount we want*/
        int playerChips = players.get(playerID).getChips();


        if (playerChips < maxBet) {
            //since all the chips this player had was not enought, they have put all in.
            players.get(playerID).removeChips(playerChips);
            pots.add(new PotTracker()); //check to see if this index should be 1.

            /*this will create the new main pot which will consist only of the playerchips times
            the number of players.
             */
            pots.get(1).pot.addChips(playerChips*numberOfPlayers);

            /*this pot goes to the person with the highest bet if they were to win the round*/
            pots.get(0).pot.addChips(playerChips);

            /*add player to list of contributors in potTracker*/
            //pots.get(0).addContributor(playerID); is this even neccessary, wouldnt this repeat?
            /*lastly, let the controller know that someone has all ined.*/
            isPlayerAllIn = true;
        } else {
            players.get(playerID).removeChips(maxBet);
            pots.get(0).pot.addChips(maxBet);
        }

        players.get(playerID).setHasCalled(true);
        return false;
    }

    public boolean check(int playerID){
        if(playerID < 0){
            /*invalid player ID*/
            return false;
        }
        if(isPlayerAllIn){
            /*meaning someone in the round has already bet so checking is unrealistic*/
            return false;
        }
        if(maxBet != 0){
            /*meaning its not the first turn*/
            return false;
        }
        return true;
    }

    public boolean raiseBet(int playerID, int amount){
        if (amount <= maxBet) {
            return false;
        }

        if (players.get(playerID).getChips() < amount) {
            return false;
        }

        maxBet = amount;
        players.get(playerID).removeChips(amount);
        players.get(playerID).setLastBet(amount);
        pots.get(0).pot.addChips(amount);
        return true;
        //pots.get(0).addContributor(playerID);    dont understand purpose.
        /*
        // this number represents the players previous during the same
        // betting phase
        int lastBet = players.get(playerID).getLastBet();
        // removes the player's bet amount from his personal pot subtracting
        // his last bet
        players.get(playerID).removeChips(maxBet - lastBet);
        // adds the max bet to the pot
        /*now update the last bet to the amount*/
        /*pot.addChips(maxBet); */
        //return true;
    }
    public boolean allIn(int playerID){
        if(playerID < 0){
            /* invalid player ID*/
            return false;
        }
        int allChips = players.get(playerID).getChips();

        /*check to see if all in is the largest bet*/
        if(allChips > maxBet){
            maxBet = allChips;
        }

        /*remove all the chips from given player object.*/
        players.get(playerID).removeChips(allChips);

        /*set isAllIn variable to true*/
        isPlayerAllIn = true;

        /*set players last bet to this all in amount*/
        players.get(playerID).setLastBet(allChips);

        return true;
    }

    /* what? */
    public void distributePots(){}
    public int getMaxBet(){
        return this.maxBet;
    }
}
