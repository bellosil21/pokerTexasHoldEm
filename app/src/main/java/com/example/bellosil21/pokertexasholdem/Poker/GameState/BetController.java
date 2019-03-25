package com.example.bellosil21.pokertexasholdem.Poker.GameState;

import com.example.bellosil21.pokertexasholdem.Poker.Money.PlayerChipCollection;

import java.util.ArrayList;

public class BetController {
    private ArrayList<PotTracker> pots;
    private ArrayList<PlayerChipCollection> players; //setting a getter method for this???
    private int maxBet; //added a getter method for this shit bois.
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

    public BetController(int numPlayers, int smBlind, int bgBlind, int startingChips){
        for(int i = 0; i<numPlayers; i++){
            players.add(new PlayerChipCollection(startingChips, i)); //i will be the id of player.
        }
        pots.add(new PotTracker());
        this.maxBet = 0;
        this.smallBlind = smBlind;
        this.bigBlind = bgBlind;
        this.numberOfPlayers = numPlayers;
    }

    public void forceBlinds(int smallBlindID, int bigBlindID){
        //if player has enough, use the method raise bet on them with blind amount
        //if they do not have enough, make them all in
    }

    public boolean call(int playerID){
        int playerChips = players.get(playerID).getChips();

        //if player has enough, add the amount to the pot
        //if player does not, make them all in

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

        //TODO: contribute to the pot with 0 amount

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

    /**
     * If going allIn when the next pot to contribute has a higher contribute
     * than the player's chips to allIn, create a new pot at index N-1 (N
     * sized pot array). Then, shift the contributions of the N'th pot, such
     * that chips moved is the allin's players chips times the contributors
     * of the N'th pot. The contribution of this new pot is the allIn amount,
     * and the contributors is the allIn player plus all players on the N'th
     * pot. Also, set the isPlayerAllIn to true.
     *
     * If going allIn when then next pot to contribute has a lower contribute
     * amount than the player's chips to allIn, add the contribution
     * amount to the N'th pot and set isPlayerAllIn to true. Then, create a new
     * pot at index N+1 (N sized pot array) and add the allIn's player chip
     * amount minus the N'th pot contribution amount. Finally, set this new
     * pot's isPlayerAllIn to true.
     *
     * @param playerID the ID of the player
     * @return true regardless of playerID
     */
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
            /* make a new subpot for this all in*/
            pots.add(new PotTracker());
            pots.get(pots.size()-1).pot.addChips(allchips);
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
