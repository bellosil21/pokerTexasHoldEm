package com.example.bellosil21.pokertexasholdem.Poker.Player;

import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.bellosil21.pokertexasholdem.Game.GameHumanPlayer;
import com.example.bellosil21.pokertexasholdem.Game.GameMainActivity;
import com.example.bellosil21.pokertexasholdem.Game.infoMsg.GameInfo;
import com.example.bellosil21.pokertexasholdem.Game.util.MessageBox;
import com.example.bellosil21.pokertexasholdem.Game.infoMsg.IllegalMoveInfo;
import com.example.bellosil21.pokertexasholdem.Game.infoMsg.NotYourTurnInfo;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerCall;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerCheck;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerFold;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerQuit;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerRaiseBet;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerShowHideCards;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerSitOut;
import com.example.bellosil21.pokertexasholdem.Poker.GameInfo.PokerEndOfRound;
import com.example.bellosil21.pokertexasholdem.Poker.GameInfo.PokerIncreasingBlinds;
import com.example.bellosil21.pokertexasholdem.Poker.GameInfo.PokerPlayerOutOfFunds;
import com.example.bellosil21.pokertexasholdem.Poker.GameState.PokerGameState;
import com.example.bellosil21.pokertexasholdem.Poker.Hand.BlankCard;
import com.example.bellosil21.pokertexasholdem.Poker.Hand.Card;
import com.example.bellosil21.pokertexasholdem.Poker.Hand.CardSlot;
import com.example.bellosil21.pokertexasholdem.Poker.Hand.Hand;
import com.example.bellosil21.pokertexasholdem.R;

import java.util.ArrayList;

/**
 * The human player GUI
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class PokerHumanPlayer extends GameHumanPlayer implements
        View.OnClickListener, SeekBar.OnSeekBarChangeListener, DialogInterface.OnClickListener {


    // Instance variables for player's chips TextViews
    private TextView player1Chips;
    private TextView player2Chips;
    private TextView player3Chips;
    private TextView player4Chips;

    // Instance references to each player's name
    private TextView player1Name;
    private TextView player2Name;
    private TextView player3Name;
    private TextView player4Name;

    // last actions for players
    private TextView player1Action;
    private TextView player2Action;
    private TextView player3Action;
    private TextView player4Action;

    // pot TextView
    private TextView jackpot;

    // Player's turn informer
    private TextView turnTracker;

    // Round Standings
    private TextView roundStandings;

    // Shows the current blinds
    private TextView blinds;

    // Player's Editable TextView to make bet
    private EditText chipBetText;

    // Player's SeekBar for making bets
    private SeekBar chipBetSeekbar;

    // Player action buttons
    private Button foldButton;
    private Button sitOutButton;
    private Button betButton;
    private Button callButton;
    private Button checkButton;
    private Button showHideCardsButton;

    // ImagesViews for the Cards
    private ImageView playerHole1;
    private ImageView playerHole2;
    private ImageView firstFlop;
    private ImageView secondFlop;
    private ImageView thirdFlop;
    private ImageView turnCard;
    private ImageView riverCard;
    private ImageView player2Card1;
    private ImageView player2Card2;
    private ImageView player3Card1;
    private ImageView player3Card2;
    private ImageView player4Card1;
    private ImageView player4Card2;
    private ImageView player1;
    private ImageView player2;
    private ImageView player3;
    private ImageView player4;
    private ImageView chipStack;
    private ImageView bettingStack;
    private ImageView logo;

    // ImageButtons for the help, settings, and exitGame
    private ImageButton helpButton;
    private ImageButton settings;
    private ImageButton exitGame;

    // ImageViews for the blind positions for each player
    private ImageView player1Status;
    private ImageView player2Status;
    private ImageView player3Status;
    private ImageView player4Status;

    // TextView for Round Number
    private TextView roundNum;

    private GameMainActivity myActivity;
    protected PokerGameState state;

    /** constants **/
    private static final String SHOW_CARDS = "SHOW CARDS";
    private static final String HIDE_CARDS = "HIDE CARDS";
    private static final String SIT_OUT = "SIT OUT";
    private static final String SIT_IN = "SIT IN";

    /**
     * Constructor
     *
     * @param name The name of the player.
     */
    public PokerHumanPlayer(String name) {
        super(name);
    }

    @Override
    public View getTopView() {
        return myActivity.findViewById(R.id.top_gui_layout);
    }

    /**
     * Sets all the references to GUI Buttons and Views
     *
     * @param activity - activity to be used for Game
     */
    @Override
    public void setAsGui(GameMainActivity activity) {

        myActivity = activity;

        activity.setContentView(R.layout.activity_main);

        // Setting all TextViews to local variables
        this.player1Chips = activity.findViewById(R.id.currChips);
        this.player2Chips = activity.findViewById(R.id.player2Chips);
        this.player3Chips = activity.findViewById(R.id.player3Chips);
        this.player4Chips = activity.findViewById(R.id.player4Chips);
        this.turnTracker = activity.findViewById(R.id.turnText);
        this.jackpot = activity.findViewById(R.id.totalPot);

        // Setting all players' names TextViews to local variables
        this.player1Name = activity.findViewById(R.id.player1Name);
        this.player2Name = activity.findViewById(R.id.player2Name);
        this.player3Name = activity.findViewById(R.id.player3Name);
        this.player4Name = activity.findViewById(R.id.player4Name);

        this.player1Action = activity.findViewById(R.id.player1Move);
        this.player2Action = activity.findViewById(R.id.player2Move);
        this.player3Action = activity.findViewById(R.id.player3Move);
        this.player4Action = activity.findViewById(R.id.player4Move);

        //MY BUTTON TEXTVIEW THINGS
        this.roundStandings = activity.findViewById(R.id.roundResults);
        this.roundStandings.setText("");
        this.blinds = activity.findViewById(R.id.blinds);

        // Setting all editable views for betting and setting a listener for
        // the SeekBar
        this.chipBetSeekbar = activity.findViewById(R.id.bettingSearch);
        chipBetSeekbar.setOnSeekBarChangeListener(this);
        this.chipBetText = activity.findViewById(R.id.betInsertText);

        // Setting references to the variables needed for the buttons in the
        // GUI
        this.foldButton = activity.findViewById(R.id.foldButton);
        this.sitOutButton = activity.findViewById(R.id.sitoutButton);
        this.betButton = activity.findViewById(R.id.betButton);
        this.checkButton = activity.findViewById(R.id.checkButton);
        this.showHideCardsButton = activity.findViewById(R.id.showHideCardsButton);
        this.callButton = activity.findViewById(R.id.callButton);

        // Setting listeners to all the buttons
        this.showHideCardsButton.setOnClickListener(this);
        this.foldButton.setOnClickListener(this);
        this.sitOutButton.setOnClickListener(this);
        this.betButton.setOnClickListener(this);
        this.checkButton.setOnClickListener(this);
        this.callButton.setOnClickListener(this);

        // Setting references to the ImageViews for the Cards;
        this.firstFlop = activity.findViewById(R.id.flop3);
        this.secondFlop = activity.findViewById(R.id.flop2);
        this.thirdFlop = activity.findViewById(R.id.flop1);
        this.turnCard = activity.findViewById(R.id.turn);
        this.riverCard = activity.findViewById(R.id.river);

        // Setting references ImageView for each opponents' cards
        this.player2Card1 = activity.findViewById(R.id.player2Card1);
        this.player2Card2 = activity.findViewById(R.id.player2Card2);
        this.player3Card1 = activity.findViewById(R.id.player3Card1);
        this.player3Card2 = activity.findViewById(R.id.player3Card2);
        this.player4Card1 = activity.findViewById(R.id.player4Card1);
        this.player4Card2 = activity.findViewById(R.id.player4Card2);
        this.playerHole1 = activity.findViewById(R.id.userFirstCard);
        this.playerHole2 = activity.findViewById(R.id.userSecCard);

        // Setting references to the each player's images
        this.player1 = activity.findViewById(R.id.player1);
        this.player2 = activity.findViewById(R.id.player2);
        this.player3 = activity.findViewById(R.id.player3);
        this.player4 = activity.findViewById(R.id.player4);
        this.chipStack = activity.findViewById(R.id.chipStack);
        this.bettingStack = activity.findViewById(R.id.bettingStack);
        this.logo = activity.findViewById(R.id.logo);

        // Setting references to the ImageButtons
        this.helpButton = activity.findViewById(R.id.helpButton);
        this.settings = activity.findViewById(R.id.settings);
        this.exitGame = activity.findViewById(R.id.exitGame);

        this.helpButton.setOnClickListener(this);
        this.settings.setOnClickListener(this);
        this.exitGame.setOnClickListener(this);

        // Setting references to each player's small/big blind image locations
        this.player1Status = activity.findViewById(R.id.player1Status);
        this.player2Status = activity.findViewById(R.id.player2Status);
        this.player3Status = activity.findViewById(R.id.player3Status);
        this.player4Status = activity.findViewById(R.id.player4Status);

        // Setting a reference to the TextView informing players of the
        // current round
        this.roundNum = activity.findViewById(R.id.roundNum);
    }

    /**
     * Update the GUI for a given PokerGameState
     *
     * Flash the screen if we have an invalid action
     *
     * @param info about the game
     */
    @Override
    public void receiveInfo(GameInfo info) {

        if (info == null) {
            return;
        }

        if (info instanceof PokerGameState) {
            // we do not want to update if it is the same state
            if (state != null){
                if (state.equals(info)) {
                    return;
                }
            }
            state = (PokerGameState) info;
            updateGui();
        } else if (info instanceof IllegalMoveInfo) {
                flash(0xFFFF0000, 50);
                Toast.makeText(myActivity.getApplicationContext(), "Invalid " +
                                "move.",
                        Toast.LENGTH_SHORT).show();
        } else if (info instanceof NotYourTurnInfo) {
            flash(0xFFFF0000, 50);
            Toast.makeText(myActivity.getApplicationContext(), "It is not" +
                            " your turn.",
                    Toast.LENGTH_SHORT).show();
        } else if (info instanceof PokerEndOfRound) {
            //tell the player of the new round standings

            int[] winnings = ((PokerEndOfRound) info).getWinnings();
            String toDisplay = "Round " + state.getRoundNumber() + " " +
                    "Standings:";

            /**
             * External Citation
             *  Date:     6 April 2019
             *  Problem:  Android Studio was staying to use a StringBuilder
             *  instead of concatenation.
             *  Resource: https://docs.oracle.com/javase/8/docs/api/java/lang/StringBuilder.html
             *  Solution: Read the javadoc to see how it was used.
             */
            StringBuilder toDisplayBuilder = new StringBuilder(toDisplay);

            for (int i = 0; i < allPlayerNames.length; i++) {
                toDisplayBuilder.append("\n\t");
                toDisplayBuilder.append(allPlayerNames[i]);
                toDisplayBuilder.append(":\n\t\t");
                toDisplayBuilder.append(state.getBetController().getPlayerChips(i));
                toDisplayBuilder.append(" (");

                // add a plus sign for positive numbers
                if (winnings[i] >= 0) {
                    toDisplayBuilder.append("+");
                }

                toDisplayBuilder.append(winnings[i]) ;
                toDisplayBuilder.append(")");
            }
            roundStandings.setText(toDisplayBuilder);
        } else if (info instanceof PokerIncreasingBlinds) {
            // tell the player of the new blinds

            int smallBlind = ((PokerIncreasingBlinds) info).getNewSmallBlind();
            int bigBlind = ((PokerIncreasingBlinds) info).getNewBigBlind();
            StringBuilder toDisplay = new StringBuilder();

            toDisplay.append("The blinds are increasing!\n\n");
            toDisplay.append("New Big Blind: $");
            toDisplay.append(bigBlind);
            toDisplay.append("\nNew Small Blind: $");
            toDisplay.append(smallBlind);

            MessageBox.popUpMessage(toDisplay.toString(), myActivity);
        } else if (info instanceof PokerPlayerOutOfFunds) {
            // tell this player a player is out of funds
            ArrayList<Integer> outOfFundPlayers =
                    ((PokerPlayerOutOfFunds) info).getPlayerIDs();

            StringBuilder toDisplay = new StringBuilder();

            // build the string to tell the human player
            if (outOfFundPlayers.size() == 1) {
                toDisplay.append("Player ");
                toDisplay.append(outOfFundPlayers.get(0) + 1);
                toDisplay.append(" is out of funds!\nThey are now out of the game" +
                        ".");
            } else {
                toDisplay.append("Player ");
                for (int i = 0; i < outOfFundPlayers.size() - 1; i++) {
                    toDisplay.append(outOfFundPlayers.get(i) + 1);
                    toDisplay.append(" and ");
                }
                int lastPlayer =
                        outOfFundPlayers.get(outOfFundPlayers.size() - 1);
                toDisplay.append(lastPlayer + 1);
                toDisplay.append(" are out of funds!\nThey are now out of the game" +
                        ".");
            }
            MessageBox.popUpMessage(toDisplay.toString(), myActivity);
        }
    }

    /**
     * Method updates the player's view on the game
     */
    private void updateGui() {
       // Changing all the player's names
        int playerCount = this.playerNum;
        player1Name.setText(this.allPlayerNames[(playerCount) % state.getNumPlayers()]);
        player2Name.setText(this.allPlayerNames[(++playerCount) % state.getNumPlayers()]);
        if (state.getNumPlayers() > 2) {
            player3Name.setText(this.allPlayerNames[(++playerCount) % state.getNumPlayers()]);
        }
        if (state.getNumPlayers() > 3) {
            player4Name.setText(this.allPlayerNames[(++playerCount) % state.getNumPlayers()]);
        }

        // Changes all the chip count to how much each player has
        playerCount = this.playerNum;
        player1Chips.setText("$ " + state.getChips((playerCount) % state.getNumPlayers()));
        player2Chips.setText("$ " + state.getChips((++playerCount) % state.getNumPlayers()));
        if (state.getNumPlayers() > 2) {
            player3Chips.setText("$ " + state.getChips((++playerCount) % state.getNumPlayers()));
        }
        if (state.getNumPlayers() > 3) {
            player4Chips.setText("$ " + state.getChips((++playerCount) % state.getNumPlayers()));
        }

        playerCount = this.playerNum;
        ArrayList<String> lastActions = state.getLastActions();
        updateAction(player1Action, lastActions.get(playerCount));
        updateAction(player2Action,
                lastActions.get((++playerCount) % state.getNumPlayers()));
        if (state.getNumPlayers() > 2) {
            updateAction(player3Action,
                lastActions.get((++playerCount) % state.getNumPlayers()));
        }
        if (state.getNumPlayers() > 3) {
            updateAction(player4Action,
                lastActions.get((++playerCount) % state.getNumPlayers()));
        }


        // Sets the current total pot amount
        jackpot.setText("" + state.getBetController().getTotalAmount());

        // Updates the Community Card fields to all existing community cards
        setCommCards(state.getCommunityCards());

        // sets image resources for player icons, chip stacks, etc.
        player1.setImageResource(R.drawable.player);
        player2.setImageResource(R.drawable.player);
        player3.setImageResource(R.drawable.player);
        player4.setImageResource(R.drawable.player);
        chipStack.setImageResource(R.drawable.chip_stack);
        bettingStack.setImageResource(R.drawable.chip_stack);
        logo.setImageResource(R.drawable.logo);
        helpButton.setImageResource(android.R.drawable.ic_menu_help);
        settings.setImageResource(android.R.drawable.ic_menu_manage);
        exitGame.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);

        // Updates the player's hole cards
        playerCount = playerNum;
        ArrayList<Hand> hands = state.getHands();
        setCard(hands.get(playerCount).getHole1(), playerHole1);
        setCard(hands.get(playerCount).getHole2(), playerHole2);
        playerCount = (playerCount + 1) % state.getNumPlayers();
        setCard(hands.get(playerCount).getHole1(), player2Card1);
        setCard(hands.get(playerCount).getHole2(), player2Card2);
        if (state.getNumPlayers() > 2) {
            playerCount = (playerCount + 1) % state.getNumPlayers();
            setCard(hands.get(playerCount).getHole1(), player3Card1);
            setCard(hands.get(playerCount).getHole2(), player3Card2);
        }
        if (state.getNumPlayers() > 3) {
            playerCount = (playerCount + 1) % state.getNumPlayers();
            setCard(hands.get(playerCount).getHole1(), player4Card1);
            setCard(hands.get(playerCount).getHole2(), player4Card2);
        }

        int activePlayerID = state.getTurnTracker().
                getActivePlayerID();

        // if the active player is not actually a player (ID < 0), then set
        // the turnTracker TextView to loading

        // if the active player from the turn tracker is >= 0, set the
        // turnTracker TextView to the name of the player

        if (activePlayerID < 0) {
            turnTracker.setText("Loading new round...");
        } else {
            turnTracker.setText(allPlayerNames[state.getTurnTracker().
                    getActivePlayerID()] + "'s Turn");

            // tell the player if it is their turn
            if (activePlayerID == playerNum) {
                Toast.makeText(myActivity.getApplicationContext(), "It is " +
                                "your turn.",
                        Toast.LENGTH_SHORT).show();
                flash(0x66FFFF66, 50);
            }
        }

        int callAmount = state.getBetController().getCallAmount(playerNum);

        callButton.setText("Call(" + callAmount + ")");

        if (state.getHands().get(playerNum).isShowCards()) {
            showHideCardsButton.setText(HIDE_CARDS);
        } else {
            showHideCardsButton.setText(SHOW_CARDS);
        }

        chipBetSeekbar.setMax(
                state.getChips(playerNum) - state.getBetController()
                        .getCallAmount(playerNum));

        setBlinds();

        roundNum.setText("Round: " + state.getRoundNumber());
    }

    /**
     * Sets the small blind and big blind image next to the player icon and
     * display the blinds amount in the TextView
     */
    private void setBlinds() {
        player1Status.setImageResource(0);
        player2Status.setImageResource(0);
        player3Status.setImageResource(0);
        player4Status.setImageResource(0);

        int playerSB = state.getTurnTracker().getSmallBlindID();
        int playerBB = state.getTurnTracker().getBigBlindID();

        if (playerSB == playerNum) {
            player1Status.setImageResource(R.drawable.small_blind);
        }
        else if (playerSB == (playerNum + 1) % state.getNumPlayers()) {
            player2Status.setImageResource(R.drawable.small_blind);
        }
        else if (playerSB == (playerNum + 2) % state.getNumPlayers()) {
            player3Status.setImageResource(R.drawable.small_blind);
        }
        else if (playerSB == (playerNum + 3) % state.getNumPlayers()) {
            player4Status.setImageResource(R.drawable.small_blind);
        }

        if (playerBB == playerNum) {
            player1Status.setImageResource(R.drawable.big_blind);
        }
        else if (playerBB == (playerNum + 1) % state.getNumPlayers()) {
            player2Status.setImageResource(R.drawable.big_blind);
        }
        else if (playerBB == (playerNum + 2) % state.getNumPlayers()) {
            player3Status.setImageResource(R.drawable.big_blind);
        }
        else if (playerBB == (playerNum + 3) % state.getNumPlayers()) {
            player4Status.setImageResource(R.drawable.big_blind);
        }

        // setting the blinds TextView
        int bigBlind = state.getBetController().getBigBlind();
        int smallBlind = state.getBetController().getSmallBlind();
        StringBuilder toDisplay = new StringBuilder();
        toDisplay.append("Big Blind: $");
        toDisplay.append(bigBlind);
        toDisplay.append("\nSmall Blind: $");
        toDisplay.append(smallBlind);
        blinds.setText(toDisplay);
    }

    /**
     * Sets the image of the Card to the corresponding Community card field
     *
     * @param commCards - ArrayList of all the community cards
     */
    private void setCommCards(ArrayList<Card> commCards) {
        int cardCount = 0;
        ImageView cardImage = null;
        if (commCards.size() == 0) {
            resetCommCards();
        }

        for (Card card : commCards) {
            switch (cardCount) {
                case 0:
                    cardImage = firstFlop;
                    break;
                case 1:
                    cardImage = secondFlop;
                    break;
                case 2:
                    cardImage = thirdFlop;
                    break;
                case 3:
                    cardImage = turnCard;
                    break;
                case 4:
                    cardImage = riverCard;
                    break;
            }
            setCard(card, cardImage);
            cardCount++;
        }

    }

    /**
     * Reset the community cards to clean up past rounds.
     *
     * External Citation
     * Date: March 31, 2019
     * Problem: Unable to reset cards to nothing for community cards
     * Resource: https://inducesmile.com/android-programming/how-to-remove-
     * image-or-bitmap-from-imageview-in-android/
     * Solution: Used the idea to put 0 as a the parameter for setImageResource
     * method
     */
    private void resetCommCards() {
        firstFlop.setImageResource(0);
        secondFlop.setImageResource(0);
        thirdFlop.setImageResource(0);
        turnCard.setImageResource(0);
        riverCard.setImageResource(0);
    }

    /**
     * Updates the GUI to show cards. This is a long method since there are
     * 52 cards in a deck, plus we need to check if the card is blank to show
     * a card backing.
     *
     * @param card1 the CardSlot from the PokerGameState
     * @param cardImage the ImageView to update in the GUI
     */
    private void setCard(CardSlot card1, ImageView cardImage) {
        // Checks if the card or Image view is set
        assert (cardImage != null);
        Card card;

        if (card1 instanceof Card) {
            card = (Card) card1;
            // Checks which suit the card has
            if (card.getSuit() == Card.Suit.DIAMONDS) {
                switch (card.getRank()) {
                    // Sets the corresponding card once the rank is found
                    case ACE:
                        cardImage.setImageResource(R.drawable.card_ad);
                        break;
                    case TWO:
                        cardImage.setImageResource(R.drawable.card_2d);
                        break;
                    case THREE:
                        cardImage.setImageResource(R.drawable.card_3d);
                        break;
                    case FOUR:
                        cardImage.setImageResource(R.drawable.card_4d);
                        break;
                    case FIVE:
                        cardImage.setImageResource(R.drawable.card_5d);
                        break;
                    case SIX:
                        cardImage.setImageResource(R.drawable.card_6d);
                        break;
                    case SEVEN:
                        cardImage.setImageResource(R.drawable.card_7d);
                        break;
                    case EIGHT:
                        cardImage.setImageResource(R.drawable.card_8d);
                        break;
                    case NINE:
                        cardImage.setImageResource(R.drawable.card_9d);
                        break;
                    case TEN:
                        cardImage.setImageResource(R.drawable.card_td);
                        break;
                    case JACK:
                        cardImage.setImageResource(R.drawable.card_jd);
                        break;
                    case QUEEN:
                        cardImage.setImageResource(R.drawable.card_qd);
                        break;
                    case KING:
                        cardImage.setImageResource(R.drawable.card_kd);
                        break;
                }
            } else if (card.getSuit() == Card.Suit.SPADES) {
                switch (card.getRank()) {
                    case ACE:
                        cardImage.setImageResource(R.drawable.card_as);
                        break;
                    case TWO:
                        cardImage.setImageResource(R.drawable.card_2s);
                        break;
                    case THREE:
                        cardImage.setImageResource(R.drawable.card_3s);
                        break;
                    case FOUR:
                        cardImage.setImageResource(R.drawable.card_4s);
                        break;
                    case FIVE:
                        cardImage.setImageResource(R.drawable.card_5s);
                        break;
                    case SIX:
                        cardImage.setImageResource(R.drawable.card_6s);
                        break;
                    case SEVEN:
                        cardImage.setImageResource(R.drawable.card_7s);
                        break;
                    case EIGHT:
                        cardImage.setImageResource(R.drawable.card_8s);
                        break;
                    case NINE:
                        cardImage.setImageResource(R.drawable.card_9s);
                        break;
                    case TEN:
                        cardImage.setImageResource(R.drawable.card_ts);
                        break;
                    case JACK:
                        cardImage.setImageResource(R.drawable.card_js);
                        break;
                    case QUEEN:
                        cardImage.setImageResource(R.drawable.card_qs);
                        break;
                    case KING:
                        cardImage.setImageResource(R.drawable.card_ks);
                        break;
                }
            } else if (card.getSuit() == Card.Suit.HEART) {
                switch (card.getRank()) {
                    case ACE:
                        cardImage.setImageResource(R.drawable.card_ah);
                        break;
                    case TWO:
                        cardImage.setImageResource(R.drawable.card_2h);
                        break;
                    case THREE:
                        cardImage.setImageResource(R.drawable.card_3h);
                        break;
                    case FOUR:
                        cardImage.setImageResource(R.drawable.card_4h);
                        break;
                    case FIVE:
                        cardImage.setImageResource(R.drawable.card_5h);
                        break;
                    case SIX:
                        cardImage.setImageResource(R.drawable.card_6h);
                        break;
                    case SEVEN:
                        cardImage.setImageResource(R.drawable.card_7h);
                        break;
                    case EIGHT:
                        cardImage.setImageResource(R.drawable.card_8h);
                        break;
                    case NINE:
                        cardImage.setImageResource(R.drawable.card_9h);
                        break;
                    case TEN:
                        cardImage.setImageResource(R.drawable.card_th);
                        break;
                    case JACK:
                        cardImage.setImageResource(R.drawable.card_jh);
                        break;
                    case QUEEN:
                        cardImage.setImageResource(R.drawable.card_qh);
                        break;
                    case KING:
                        cardImage.setImageResource(R.drawable.card_kh);
                        break;
                }
            } else if (card.getSuit() == Card.Suit.CLUBS) {
                switch (card.getRank()) {
                    case ACE:
                        cardImage.setImageResource(R.drawable.card_ac);
                        break;
                    case TWO:
                        cardImage.setImageResource(R.drawable.card_2c);
                        break;
                    case THREE:
                        cardImage.setImageResource(R.drawable.card_3c);
                        break;
                    case FOUR:
                        cardImage.setImageResource(R.drawable.card_4c);
                        break;
                    case FIVE:
                        cardImage.setImageResource(R.drawable.card_5c);
                        break;
                    case SIX:
                        cardImage.setImageResource(R.drawable.card_6c);
                        break;
                    case SEVEN:
                        cardImage.setImageResource(R.drawable.card_7c);
                        break;
                    case EIGHT:
                        cardImage.setImageResource(R.drawable.card_8c);
                        break;
                    case NINE:
                        cardImage.setImageResource(R.drawable.card_9c);
                        break;
                    case TEN:
                        cardImage.setImageResource(R.drawable.card_tc);
                        break;
                    case JACK:
                        cardImage.setImageResource(R.drawable.card_jc);
                        break;
                    case QUEEN:
                        cardImage.setImageResource(R.drawable.card_qc);
                        break;
                    case KING:
                        cardImage.setImageResource(R.drawable.card_kc);
                        break;
                }
            }
        } else if (card1 instanceof BlankCard) {
            cardImage.setImageResource(R.drawable.card_b);
        } else {
            return;
        }

    }

    /**
     * Updates the GUI to show each player's last action
     *
     * @param tv the text view of the player to update
     * @param action the player's last action
     */
    private void updateAction(TextView tv, String action) {
        if (action == null) {
            tv.setText("");
        } else {
            tv.setText(action);
        }
    }

    /**
     * External Citation
     *  March 30, 2019
     * Problem: Unable to convert a type Editable to a type int
     * Resource: https://www.quora.com/How-would-I-get-an-int-value-from-an-
     *           edit-text-view-in-Android-studio
     * Solution: Used the method to convert to a String first then to a int
     *           suggested from this forum post
     */

    /**
     * Determine which button was pressed and send the corresponding action
     *
     * @param v the view that was clicked
     */
    @Override
    public void onClick(View v) {
        if (v == null) {
            return;
        }

        if (v == foldButton) {
            game.sendAction(new PokerFold(this));
        } else if (v.equals(callButton)) {
            game.sendAction(new PokerCall(this));
        } else if (v.equals(checkButton)) {
            game.sendAction(new PokerCheck(this));
        } else if (v.equals(betButton)) {

            // make sure the TextEdit contains an integer and the player has
            // enough to bet the amount
            int playerID = state.getTurnTracker().getActivePlayerID();
            int allPlayerMoney = state.getBetController().getPlayerChips(playerID);

            int bet;
            try{
                bet = Integer.parseInt(chipBetText.getText().toString());
            }
            catch(NumberFormatException i){
                MessageBox.popUpMessage("That move isn't valid!", this.myActivity);
                Log.i("bet variable", "Bet variable was not in proper format.");
                return;
            }

            /**
             External Citation
                Date: 1 April 2019
                Problem:    Needed to find a way to check if a bet integer was declared.
                            because in the GUI, when they choose to type in the amount they
                            want to raise, it defaults to "place bets" when nothing is typed,
                            and the game will crash if this method tries to access an integer
                            that does not exist.
                Resource:
                    https://docs.oracle.com/javase/8/docs/api/java/lang/Integer.html#parseInt-
                        java.lang.String-
                Solution:   After inspecting the parseInt() method, we realised that it throws
                            a NumberFormatException. With this information, we used a try/catch
                            that would prevent the program from crashing.
             */

            if (bet > allPlayerMoney) {
                MessageBox.popUpMessage("Entry too big!", this.myActivity);
                Log.i("PlaceBets error", "User has attempted to raise more money than they " +
                        "have");
            }
            else if(bet < 0){
                MessageBox.popUpMessage("Cant bet a negative amount!", this.myActivity);
            }
            else if(bet == 420) {
                int duration = Toast.LENGTH_SHORT;
                Toast.makeText(myActivity.getApplicationContext(), "Pass the boof",
                        duration).show();
            }
            else if(bet == 69){
                int duration = Toast.LENGTH_SHORT;
                Toast.makeText(myActivity.getApplicationContext(), "Nice.",
                        duration).show();
            }
            int callAmount = state.getBetController().getCallAmount(playerNum);
            game.sendAction(new PokerRaiseBet(this, bet, callAmount));

        } else if (v.equals(showHideCardsButton)) {
            // toggle the display and send the action
            if (showHideCardsButton.getText().equals(SHOW_CARDS)) {
                showHideCardsButton.setText(HIDE_CARDS);
                game.sendAction(new PokerShowHideCards(this, true));
            } else {
                showHideCardsButton.setText(SHOW_CARDS);
                game.sendAction(new PokerShowHideCards(this, false ));
            }

        } else if (v.equals(sitOutButton)) {
            // toggle the display and send the action
            if (sitOutButton.getText().equals(SIT_IN)) {
                sitOutButton.setText(SIT_OUT);
            } else {
                sitOutButton.setText(SIT_IN);
            }
            game.sendAction(new PokerSitOut(this));
        } else if(v.equals(helpButton)) {
            // TODO: implement some sort of guide on the hand rankings and
            // instructions
        } else if(v.equals(settings)) {
            // TODO: potential language change and other
        } else if(v.equals(exitGame)){
            MessageBox.popUpChoice("Do you want to exit the game?", "Yes", "No",
                    this, null, myActivity);
        }
    }

    /**
     * Update the seek bar to show the betting amount with respect to the
     * amount needed to call
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (state == null) {
            return;
        }
        int betting =
                progress + state.getBetController().getCallAmount(playerNum);
        chipBetText.setText(""+ betting);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // not used
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // not used
    }

    /**
     * The onClickListener for the exit button. When pressed, send a quit
     * action and leave the app
     */
    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        // this listener is for the positive action of the quit dialog
        game.sendAction(new PokerQuit(this));
        myActivity.finish();
        System.exit(0);

        /**
         * External Citation
         *  Date:     14 April 2019
         *  Problem:  Did not know how to quit the app.
         *  Resource: https://stackoverflow.com/questions/6330200/how-to-quit-android-application-programmatically
         *  Solution: Implemented the solution in the resource.
         */
    }
}
