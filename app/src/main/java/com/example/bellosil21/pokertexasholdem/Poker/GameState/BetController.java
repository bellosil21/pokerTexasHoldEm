package com.example.bellosil21.pokertexasholdem.Poker.GameState;

import com.example.bellosil21.pokertexasholdem.Poker.Money.PlayerChipCollection;

import java.util.ArrayList;

public class BetController {
    private ArrayList<PotTracker> pots; // Keeps track of all the pots.
    /* A pot at a lower index includes all players, while pots at high
     * indexes include less players. Assume we only have one pot to begin
     * with. A new pot is created under two conditions:
     *      A - a player does not have enough chips to fully call a bet;
     *          thus, their call makes them have no more chips
     *      B - a player goes all-in to raise the maximum bet
     *
     * Case A:
     *      This player can only win the bets that match their maximum amount
     *      contributed. Because they do not have enough to fully call, they
     *      should not be able to win all the bets in this pot. Hence, a new
     *      pot needs to added before the current one in the array. This new
     *      pot will be
     */


    private ArrayList<PlayerChipCollection> players; //setting a getter method for this???
    private int maxBet; //added a getter method for this shit bois.
    private boolean isPlayerAllIn;
    private int smallBlind;
    private int bigBlind;
    private int numberOfPlayers;
    private final int MAIN_POT_INDEX = 0;
    /*
    private final int INDEX_0 = 0;
    private final int INDEX_1 = 1;
    private final int INDEX_2 = 2;
    private final int INDEX_3 = 4;
    */

    public BetController(int numPlayers, int smBlind, int bgBlind){
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
        this.smallBlind = smBlind;
        this.bigBlind = bgBlind;
        this.numberOfPlayers = numPlayers;
    }

    /*not sure what you want this to do*/
    //will automatically put bets for player index 0 and 1
    public void forceBlinds(int smallBlindID, int bigBlindID){
        /*add the small blind and big blind bets to the pot*/
        pots.get(MAIN_POT_INDEX).pot.addChips(smallBlind);
        pots.get(MAIN_POT_INDEX).pot.addChips(bigBlind);

        /*remove the chips from the big blind and small blind players*/
        players.get(smallBlindID).removeChips(smallBlind);
        players.get(bigBlindID).removeChips(bigBlind);

    }

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
            pots.get(MAIN_POT_INDEX).pot.addChips(playerChips);

            /*add player to list of contributors in potTracker*/
            //pots.get(0).addContributor(playerID); is this even neccessary, wouldnt this repeat?
            /*lastly, let the controller know that someone has all ined.*/
            isPlayerAllIn = true;
        } else {
            players.get(playerID).removeChips(maxBet);
            pots.get(MAIN_POT_INDEX).pot.addChips(maxBet);
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
        pots.get(MAIN_POT_INDEX).pot.addChips(amount);
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
        else{
            pots.add(new PotTracker());
            pots.get(pots.size()-1).pot.addChips(allChips);
        }

        /*remove all the chips from given player object.*/
        players.get(playerID).removeChips(allChips);

        /*set isAllIn variable to true*/
        isPlayerAllIn = true;

        /*set players last bet to this all in amount*/
        players.get(playerID).setLastBet(allChips);

        return true;
    }

    public void distributePots(int[] rankings){
        int n;
        ArrayList<Integer> winners;
        for(PotTracker p : pots){
            winners = new ArrayList<>();
            n = getHighestRanking(p.getContributors(), rankings);
                for(int i = 0; i<rankings.length; i++){
                    if(rankings[i] == n){
                        winners.add(i);
                    }
                }
               int money = p.pot.getChips();
            /* noe iterate through all the winners and distribute pots accordingly. */
            if(winners.size() >1){
                //split money into groups equal to the amount of winners.
                money = money/winners.size();
                for(int i = 0; i<winners.size(); i++){
                    players.get(winners.get(i)).addChips(money);
                }
            }
            else{
                players.get(winners.get(0)).addChips(money);
            }
        }
        /*reset maxbet and isPlayerAllin*/
       asynchronousReset();
    }
    /* this method will take in an array of integers that symbolize the type of winning hand
    * if -1. than folded
    * if two or more integers reapeat, than split the pot.
    */


    /**
     * helper method for distribute pots.
     * @return
     */
    private int getHighestRanking(ArrayList<Integer> contributors, int[] rankings){
        /* 0 is the highest possible rank, which makes the for loop weird. */
        int highestRank = rankings[contributors.get(0)];
        for(int i = 0; i < contributors.size(); i++){
            if(highestRank > rankings[contributors.get(i)]) {
                highestRank = rankings[contributors.get(i)];
            }
        }
        return highestRank;
    }

    public int getMaxBet(){
        return this.maxBet;
    }

    public int getPlayerChips(int playerID){
        int allChips = players.get(playerID).getChips();
        return allChips;
    }

    /**
     * This is a helper method for other methods in the BetController class.
     * This basically resets the maxBet variable to 0 and the isPlayerAllIn boolean to false.
     * examples of where this would be called would include when the round is over
     * (i.e. distributePots() is called).
     */
    private void asynchronousReset(){
        maxBet = 0;
        isPlayerAllIn = false;
    }
}
