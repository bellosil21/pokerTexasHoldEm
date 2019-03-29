package com.example.bellosil21.pokertexasholdem.Poker.GameState;

import com.example.bellosil21.pokertexasholdem.Poker.Money.PlayerChipCollection;

import java.util.ArrayList;

public class BetController {

    private ArrayList<PotTracker> pots; // Keeps track of all the pots.
    /* A pot at a lower index includes all players, while pots at high
     * indexes include less players. Assume we only have one pot to begin
     * with. A new pot is created under four conditions:
     *      A - a player does not have enough chips to fully match a pot
     *          contribution; thus, their call makes them have no more chips
     *      B - a player raises the maximum bet
     *      C - a new betting phase has occured
     *
     * Case A:
     *      This player can only win the bets that match their maximum
     *      contribution. Because they do not have enough to fully call, they
     *      should not be able to win all the bets in this pot. Hence, (1) A
     *      new pot needs to added before the current one in the array that
     *      contains the bets that match the this player's amount. This new
     *      pot will have all of this player's chips times the amount of
     *      contributors in the original pot plus 1 [playerID's chips *
     *      (amount original pot's contributors + 1)]. Now, the amount we
     *      took out of the original pot's amount and contribution per player
     *      needs to be (2) updated to account the transfer of chips. Also, the
     *      new pot needs to be (3) locked since this player is all in and
     *      cannot contribute any more funds.
     *
     * Case B:
     *      When the player raises the maximum bet, we are contributing a new
     *      amount, so we keep track of this new amount with a new pot.
     *      First, contribute all we can until there's nothing more to add to
     *      all the pots. Then, place a new pot at the head of the pot array
     *      with the amount left over.
     *
     * Case C:
     *      When a new betting phase has occured, we need an empty pot in
     *      order for players to "check" the pot of 0 contribution.
     */

    private ArrayList<PlayerChipCollection> players; //setting a getter method for this???
    private int maxBet; //added a getter method for this shit bois.
    private int smallBlind;
    private int bigBlind;

    public BetController(int numPlayers, int smBlind, int bgBlind){
        pots = new ArrayList<>();
        for(int i = 0; i<numPlayers; i++){
            players.add(new PlayerChipCollection(0, i)); //i will be the id of player.
        }
        this.maxBet = 0;
        this.smallBlind = smBlind;
        this.bigBlind = bgBlind;
    }

    /**
     * This method sets up the betting phase. A new betting phase does the
     * following:
     *  1. reset player's lastBet to be 0
     *  2. reset maxBet to 0
     *  3. create a new pot at the tail of the pot array
     */
    public void startPhase() {
        //TODO
    }

    /**
     * If the small blind player has enough money, make them
     * raiseBet by the small blind amount. Otherwise, make them go all in.
     * Then, if the big blind player has enough money, make them raiseBet by
     * the big blind amount. Otherwise, make them go all in.
     *
     * @param smallBlindID the player ID for the small blind
     * @param bigBlindID the player ID for the big blind
     */
    public void forceBlinds(int smallBlindID, int bigBlindID){
        //TODO
        /*add the small blind and big blind bets to the pot*/
        pots.get(MAIN_POT_INDEX).pot.addChips(smallBlind);
        pots.get(MAIN_POT_INDEX).pot.addChips(bigBlind);

        /*remove the chips from the big blind and small blind players*/
        players.get(smallBlindID).removeChips(smallBlind);
        players.get(bigBlindID).removeChips(bigBlind);

    }


    /**
     * Returns how much the player needs to contribute in order to call.
     *
     * @param playerID the ID of the player
     * @return the amount needed to call
     */
    public int getCallAmount(int playerID) {
        return maxBet - players.get(playerID).getLastBet();
    }

    /**
     * Adds the player's call amount to the pot. Returns a boolean
     * representing if the player is out of funds
     *
     * @param playerID the ID of the player
     * @return true if the player used up all their funds
     */
    public boolean call(int playerID){
        //TODO
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

    /**
     * Raises the maximum bet if valid. A bet is valid if the player has
     * enough funds and the betting amount plus the player's last bet is
     * greater than the maximum bet.
     *
     * If valid, we will update the maximum bet and add the call amount to
     * the pot.
     *
     * @param playerID the ID of the player
     * @param amount the amount the player is betting; this amount should be
     *               an additional amount to the player's last bet
     * @return -1 if the player does have enough to bet or the bet isn't
     *              greater than the maximum bet
     *         0 for a successful bet and the player still has funds left over
     *         1 for a successful bet and the player has no funds left over
     */
    public int raiseBet(int playerID, int amount){
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
     * Contribute all of the player's chips to the pot.
     *
     * If the player's chip amount amount plus their last bet is less than
     * the maximum bet
     *
     * @param playerID
     * @return
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

    /**
     * The main method to add to the pots array.
     *
     * Before we add anything to the pots, we withdraw the amount from the
     * player. Then, we determine if this is a Case A of making a new pot.
     *
     *
     */
    public void addToPot(int playerID, int amount) {

    }

    /**
     * A recursive helper method to
     *
     * @param playerID the ID of the player
     * @param amount the chips left to contribute to the pots
     */
    private void addToPotHelper(int playerID, int amount)

    /**
     * Given the ranking of players, we iterate through all the pots in the
     * array and reward each pot to the contributor of highest rank.
     *
     * The best rank is 0 and the worst rank, occurring when a player has
     * folded, is Integer.MAX
     *
     * @param rankings the ranking of each player (int)
     *
     */
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
     * Helper method for distribute pots
     *
     * @return the playerID of the contributor who has the best rank
     */
    private int getHighestRanking(ArrayList<Integer> contributors, int[] rankings){
        /* 0 is the highest possible rank, which makes the for loop weird. */
        int highestRank = rankings[contributors.get(0)];
        for(int i = 0; i < contributors.size(); i++){
            if(highestRank < rankings[contributors.get(i)]) {
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
