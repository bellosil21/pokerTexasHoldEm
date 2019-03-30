package com.example.bellosil21.pokertexasholdem.Poker.GameState;

import com.example.bellosil21.pokertexasholdem.Poker.Money.PlayerChipCollection;

import java.util.ArrayList;

public class BetController {

    /** instance vars **/
    private ArrayList<PotTracker> pots; // Keeps track of all the pots.
    /* A pot at a lower index includes all players, while pots at high
     * indexes include less players. Assume we only have one pot to begin
     * with. A new pot is created under four conditions:
     *      A - a player does not have enough chips to fully match a pot
     *          contribution; thus, their call makes them have no more chips
     *      B - a player raises the maximum bet
     *
     * Case A:
     *      This player can only win the bets that match their maximum
     *      contribution. Because they do not have enough to fully call, they
     *      should not be able to win all the bets in this pot. Hence, (1) A
     *      new pot needs to added before the current one in the array that
     *      contains the contribution that match the this player's amount.
     *      (2) This new pot will have the contributors of the pot that comes
     *      after it. Finally, (3) subtract the new pot's contribution from
     *      the next pots contribution.
     *
     * Case B:
     *      When the player raises the maximum bet, we are contributing a new
     *      amount, so we keep track of this new amount with a new pot.
     *      First, contribute all we can until there's nothing more to add to
     *      all the pots. Then, place a new pot at the head of the pot array
     *      with the amount left over.
     */

    private ArrayList<PlayerChipCollection> players;
    private int maxBet;
    private int totalAmount;
    private int smallBlind;
    private int bigBlind;

    /** constants **/
    private static final int MULTIPLIER = 2; // the factor at which the
                                             // small/big blinds will be
                                             // incremented.
    private static final int DEFAULT_POT_AMOUNT = 0;

    public BetController(int numPlayers, int startingChips, int smBlind,
                         int bgBlind){
        pots = new ArrayList<>();

        players = new ArrayList<>();
        for(int i = 0; i<numPlayers; i++){
            players.add(new PlayerChipCollection(startingChips));
        }

        this.maxBet = DEFAULT_POT_AMOUNT;
        this.smallBlind = smBlind;
        this.bigBlind = bgBlind;
        this.totalAmount = DEFAULT_POT_AMOUNT;
    }

    /**
     * Copy constructor
     */
    public BetController(BetController toCopy) {
        pots = new ArrayList<>();
        pots.addAll(toCopy.pots);

        players = new ArrayList<>();
        players.addAll(toCopy.players);

        maxBet = toCopy.maxBet;
        totalAmount = toCopy.totalAmount;
        smallBlind = toCopy.smallBlind;
        bigBlind = toCopy.bigBlind;
    }

    /**
     * This method sets up the betting phase by resetting the player's last
     * bets to 0
     */
    public void startPhase() {
        for (PlayerChipCollection p : players) {
            p.setLastBet(0);
        }
    }

    /**
     * If the small blind player has enough money, make them
     * raiseBet by the small blind amount. Otherwise, make them go all in.
     *
     *
     * @param smallBlindID the player ID for the small blind
     * @return true if the player was forced to go all in
     */
    public boolean forceSmallBlinds(int smallBlindID){
        int smallPlayerChips = players.get(smallBlindID).getChips();

        if (smallPlayerChips <= smallBlind) {
            allIn(smallBlindID);
            return true;
        }

        raiseBet(smallBlindID, smallBlind);

        return false;
    }

    /**
     * Then, if the big blind player has enough money, make them raiseBet by
     * the big blind amount. Otherwise, make them go all in.
     *
     * @param bigBlindID the player ID for the big blind
     * @return true if the player was forced to go all in
     */
    public boolean forceBigBlinds(int bigBlindID){
        int bigPlayerChips = players.get(bigBlindID).getChips();

        if (bigPlayerChips <= smallBlind) {
            allIn(bigBlindID);
            return true;
        }

        raiseBet(bigBlindID, bigBlind);

        return false;
    }

    public void incrementBlinds(){
        this.smallBlind = this.smallBlind * MULTIPLIER;
        this.bigBlind = this.bigBlind * MULTIPLIER;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    /**
     * Returns how much the player needs to contribute in order to call. The
     * default call amount is the maximum bet minus the player's last bet.
     * However, if the player's chip amount is smaller than this difference,
     * the player's chip amount is returned. (If they cannot call the default
     * amount, they need to go all in)
     *
     * @param playerID the ID of the player
     * @return the amount needed to call
     */
    public int getCallAmount(int playerID) {
        int callAmount = maxBet - players.get(playerID).getLastBet();
        int playerChips = players.get(playerID).getChips();

        if (callAmount >= playerChips) {
            return playerChips;
        }

        return callAmount;
    }

    /**
     * Adds the player's call amount to the pot. Returns a boolean
     * representing if the player is out of funds
     *
     * @param playerID the ID of the player
     * @return true if the player used up all their funds
     */
    public boolean call(int playerID){
        PlayerChipCollection player = players.get(playerID);

        int callAmount = getCallAmount(playerID);
        addToPot(playerID, callAmount);

        int playerChips = player.getChips();
        if (playerChips == 0) {
            return true;
        }

        return false;
    }

    /**
     * A bet is valid if the player has enough funds and the betting amount
     * plus the player's last bet is greater than the maximum bet.
     *
     * If valid, we will add the bet amount to the pots
     *
     * @param playerID the ID of the player
     * @param amount the amount the player is betting; this amount should be
     *               the additional amount to the player's last bet
     * @return -1 if the player does have enough to bet or the bet isn't
     *              greater than the maximum bet
     *         0 for a successful bet and the player still has funds left over
     *         1 for a successful bet and the player has no funds left over
     */
    public int raiseBet(int playerID, int amount){
        PlayerChipCollection player = players.get(playerID);
        int playerChips = player.getChips();

        // check if bet is valid
        int accumulativeBet = player.getLastBet() + amount;
        if (accumulativeBet <= maxBet || playerChips < amount) {
            return -1;
        }

        addToPot(playerID, amount);

        // check if the player is out of funds
        playerChips = player.getChips();
        if (playerChips == 0) {
            return 1;
        }

        return 0;
    }

    /**
     * allIn
     * This is the method that will be called when a player all in's. It will take in the
     * playerID index and call methods that will extract all the chips from the given player into
     * a local variable. Than the addToPot method will be called.
     *
     * @param playerID The player that has declared an all in action.
     */
    public void allIn(int playerID){
        if(playerID < 0){
            /* invalid player ID*/
            return;
        }

        int allChips = players.get(playerID).getChips();

        /*check to see if all in is the largest bet*/
        if(allChips > maxBet){
            maxBet = allChips;
        }

        addToPot(playerID, allChips);

        /*remove all the chips from given player object.*/
        //players.get(playerID).removeChips(allChips); //is done in addToPot

        /*set isAllIn variable to true*/
        //isPlayerAllIn = true;

        /*set players last bet to this all in amount*/
        //players.get(playerID).setLastBet(allChips); //done in addToPot
    }

    /**
     * The main method to add to the pots array.
     *
     * Before we add anything to the pots, we withdraw the amount from the
     * player. Then, we determine if this is a case A of making a new pot. If
     * so, add
     *
     *
     */
    private void addToPot(int playerID, int amount) {
        PlayerChipCollection player = players.get(playerID);

        int nextPotIndex = player.getLastContributedPot() + 1;
        PotTracker nextPot = pots.get(nextPotIndex);

        int nextPotContribution = nextPot.getContribution();

        //adjust the player's last bet
        int accumulativeBet = player.getLastBet() + amount;
        player.setLastBet(accumulativeBet);

        // determine if this is the highest bet
        // if so, make a new pot
        if (accumulativeBet > maxBet) {
            int newPotContribution = amount - maxBet;
            PotTracker newPot = new PotTracker(newPotContribution);
            pots.add(newPot);
            maxBet = accumulativeBet;
        }

        // determine if we are trying to add an amount less than the next pot
        // to contribute
        else if (amount < nextPotContribution) {
            // this is a case A instance of making a new pot

            ArrayList<Integer> newPotContributors =
                    nextPot.getContributors();

            for (int p : newPotContributors) {
                players.get(p).incrementLastContributedPot();
            }

            PotTracker newPot = new PotTracker(amount, newPotContributors);
            pots.add(nextPotIndex, newPot);

            nextPot.subtractContribution(amount);
        }

        // the pot array is now ready to accept any new contributions
        addToPotHelper(playerID, amount);
    }

    /**
     * A recursive helper method to add the player to the contributors of the
     * next pot and subtract the contribution amount from their chip
     * collection, until we fully contributed the amount.
     *
     * All pots should be set up correctly, no matter the case, when this
     * method is called.
     *
     * @param playerID the ID of the player
     * @param amount the chips left to contribute to the pots
     */
    private void addToPotHelper(int playerID, int amount) {
        if (amount == 0) {
            return;
        }

        PlayerChipCollection player = players.get(playerID);

        int nextPotIndex = player.incrementLastContributedPot();
        PotTracker nextPot = pots.get(nextPotIndex);
        int potContribution = nextPot.getContribution();

        totalAmount =+ potContribution;
        player.removeChips(potContribution);
        nextPot.addContributor(playerID);

        int remaining = amount - potContribution;

        addToPotHelper(playerID, remaining);
    }

    /**
     * distributePots
     * Given the ranking of players, we iterate through all the pots in the array and reward each
     * pot to the contributor of highest rank.
     *
     * This method will take in an array of integers that symbolize the type of winning hand if
     * -1. than folded if two or more integers repeat, than split the pot.
     *
     * The best rank is 0 and the worst rank, occurring when a player has
     * folded, is Integer.MAX
     *
     * @param rankings the sorted array of winning hand rankings.
     *
     */
    public void distributePots(int[] rankings){

        for(PotTracker p: pots){
            ArrayList<Integer> winners = getHighestRankingPlayers(p.getContributors(), rankings);
            int amountInPot = p.getContribution();
            int contributorsPerPot = p.getContributors().size();
            int potTotal = amountInPot * contributorsPerPot;
            int chipsWon = potTotal/winners.size(); //how much the winner or multiple winners get.

            for(int i: winners){
                players.get(i).addChips(chipsWon);
            }
        }
        /*reset maxBet and totalAmount */
        asynchronousReset();
        /*
        int n;
        ArrayList<Integer> winners;
        for(PotTracker p : pots){
            winners = new ArrayList<>();
            n = getHighestRankingPlayers(p.getContributors(), rankings);
                for(int i = 0; i<rankings.length; i++){
                    if(rankings[i] == n){
                        winners.add(i);
                    }
                }
               int money = p.pot.getChips();
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
        */
    }



    /**
     * getHighestRankingPlayers
     * Helper method for distribute pots; this method takes in the contributors for a given
     * PotTracker object and the rankings for each one of them. The goal of this method is to return
     * the winner or winners from the list of contributors. This will help the distributePots()
     * method decide who to give the chips to.
     *
     * Side Note: rankings array returns a sorted array of each player's hand ranking.
     * This is why if there are four players: 0, 1, 2, 3, and rankings are lets say
     * 1, 0, 1, 2, they are parallel: 0-1, 1-0, 2-1, 3-2 etc.
     *
     * @return An array list of the best ranking hands from a list of contributors of a
     * potTracker object.
     */
    private ArrayList<Integer> getHighestRankingPlayers(ArrayList<Integer> contributors,
                                                    int[] rankings){
        /* 0 is the highest possible rank, which makes the for loop weird. */
        /*
        int highestRank = rankings[contributors.get(0)];
        for(int i = 0; i < contributors.size(); i++){
            if(highestRank < rankings[contributors.get(i)]) {
                highestRank = rankings[contributors.get(i)];
            }
        }
        return highestRank;
        */
        ArrayList<Integer> highestRanks = new ArrayList<Integer>();
        int highestRank = rankings[contributors.get(0)];
        for(int i = 0; i< contributors.size(); i++){
            if(highestRank < rankings[contributors.get(i)]){
                highestRanks.add(rankings[contributors.get(i)]);
            }
            else if(highestRank == rankings[contributors.get(i)]){
                highestRanks.add(rankings[contributors.get(i)]);
            }
        }
        return highestRanks; //returns an array list of the winner{s) that 'the pot' needs to
        //be split between.
    }

    public int getMaxBet(){
        return this.maxBet;
    }

    public int getSmallBlind() {
        return smallBlind;
    }

    public int getBigBlind() {
        return bigBlind;
    }

    public int getPlayerChips(int playerID){
        int allChips = players.get(playerID).getChips();
        return allChips;
    }

    /**
     * This resets the BetController at the end of a round. The pot array is
     * emptied, the maximum bet is reset, the total bet amount is reset, and
     * the player's lastContributedPot is reset.
     */
    public void asynchronousReset(){
        this.pots.clear();
        this.maxBet = DEFAULT_POT_AMOUNT;
        this.totalAmount = DEFAULT_POT_AMOUNT;
        for (PlayerChipCollection p : players) {
            p.resetLastContributedPot();
        }
    }
}
