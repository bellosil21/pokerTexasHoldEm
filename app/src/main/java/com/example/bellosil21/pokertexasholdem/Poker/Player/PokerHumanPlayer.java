package com.example.bellosil21.pokertexasholdem.Poker.Player;

import android.os.Build;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;


import com.example.bellosil21.pokertexasholdem.Game.GameHumanPlayer;
import com.example.bellosil21.pokertexasholdem.Game.GameMainActivity;
import com.example.bellosil21.pokertexasholdem.Game.actionMsg.GameAction;
import com.example.bellosil21.pokertexasholdem.Game.infoMsg.GameInfo;
import com.example.bellosil21.pokertexasholdem.Game.util.MessageBox;
import com.example.bellosil21.pokertexasholdem.Game.infoMsg.IllegalMoveInfo;
import com.example.bellosil21.pokertexasholdem.Game.infoMsg.NotYourTurnInfo;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerAllIn;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerCall;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerCheck;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerFold;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerRaiseBet;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerShowHideCards;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerSitOut;
import com.example.bellosil21.pokertexasholdem.Poker.GameState.PokerGameState;
import com.example.bellosil21.pokertexasholdem.Poker.Hand.BlankCard;
import com.example.bellosil21.pokertexasholdem.Poker.Hand.Card;
import com.example.bellosil21.pokertexasholdem.Poker.Hand.CardSlot;
import com.example.bellosil21.pokertexasholdem.Poker.Hand.Hand;
import com.example.bellosil21.pokertexasholdem.R;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class PokerHumanPlayer extends GameHumanPlayer implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {


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
    private int lastMaxBet = 0;

    // pot TextView
    private TextView jackpot;

    // Player's turn informer
    private TextView turnTracker;

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
    private ImageView blankCard;
    private ImageView player1;
    private ImageView player2;
    private ImageView player3;
    private ImageView player4;
    private ImageView chipStack;
    private ImageView bettingStack;

    private GameMainActivity myActivity;
    protected PokerGameState state;
    private static final int MAX_INTEGER = 2147483647;


    /**
     * constructor
     *
     * @param name the name of the player
     */
    public PokerHumanPlayer(String name) {
        super(name);
        lastMaxBet = 0;
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
        this.player1Chips = (TextView) activity.findViewById(R.id.currChips);
        this.player2Chips = (TextView) activity.findViewById(R.id.player2Chips);
        this.player3Chips = (TextView) activity.findViewById(R.id.player3Chips);
        this.player4Chips = (TextView) activity.findViewById(R.id.player4Chips);
        this.turnTracker = (TextView) activity.findViewById(R.id.turnText);
        this.jackpot = (TextView) activity.findViewById(R.id.totalPot);

        // Setting all players' names TextViews to local variables
        this.player1Name = (TextView) activity.findViewById(R.id.player1Name);
        this.player2Name = (TextView) activity.findViewById(R.id.player2Name);
        this.player3Name = (TextView) activity.findViewById(R.id.player3Name);
        this.player4Name = (TextView) activity.findViewById(R.id.player4Name);

        this.player1Action = (TextView) activity.findViewById(R.id.player1Move);
        this.player2Action = (TextView) activity.findViewById(R.id.player2Move);
        this.player3Action = (TextView) activity.findViewById(R.id.player3Move);
        this.player4Action = (TextView) activity.findViewById(R.id.player4Move);

        // Setting all editable views for betting and setting a listener for
        // the SeekBar
        this.chipBetSeekbar = (SeekBar) activity.findViewById(R.id.bettingSearch);
        chipBetSeekbar.setOnSeekBarChangeListener(this);
        this.chipBetText = (EditText) activity.findViewById(R.id.betInsertText);

        // Setting references to the variables needed for the buttons in the
        // GUI
        this.foldButton =
                (Button) activity.findViewById(R.id.foldButton);
        this.sitOutButton =
                (Button) activity.findViewById(R.id.sitoutButton);
        this.betButton = (Button) activity.findViewById(R.id.betButton);
        this.checkButton = (Button) activity.findViewById(R.id.checkButton);
        this.showHideCardsButton =
                (Button) activity.findViewById(R.id.showHideCardsButton);
        this.callButton =
                (Button) activity.findViewById(R.id.callButton);

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
        // TODO: 3/31/2019 if statement to see if round is over, if true show all cards
        this.player2Card1 = activity.findViewById(R.id.player2Card1);
        this.player2Card2 = activity.findViewById(R.id.player2Card2);
        this.player3Card1 = activity.findViewById(R.id.player3Card1);
        this.player3Card2 = activity.findViewById(R.id.player3Card2);
        this.player4Card1 = activity.findViewById(R.id.player4Card1);
        this.player4Card2 = activity.findViewById(R.id.player4Card2);

        this.playerHole1 = activity.findViewById(R.id.userFirstCard);
        this.playerHole2 = activity.findViewById(R.id.userSecCard);

        this.player1 = activity.findViewById(R.id.player1);
        this.player2 = activity.findViewById(R.id.player2);
        this.player3 = activity.findViewById(R.id.player3);
        this.player4 = activity.findViewById(R.id.player4);
        this.chipStack = activity.findViewById(R.id.chipStack);
        this.bettingStack = activity.findViewById(R.id.bettingStack);
    }

    @Override
    public void receiveInfo(GameInfo info) {

        if (info == null) {
            return;
        }

        if (info instanceof PokerGameState) {
            state = (PokerGameState) info;
            updateGui();
        } else if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
            flash(0xFFFF0000, 50);
        }

    }

    public void updateGui() {
        TextView player1TV = null;
        TextView player2TV = null;
        TextView player3TV = null;
        TextView player4TV = null;
        TextView player1Nm = null;
        TextView player2Nm = null;
        TextView player3Nm = null;
        TextView player4Nm = null;

        // Sets the TextViews to change as their are the player
        switch (this.playerNum) {
            case 0:
                player1TV = player1Chips;
                player1Nm = player1Name;
                player2TV = player2Chips;
                player2Nm = player2Name;
                player3TV = player3Chips;
                player3Nm = player3Name;
                player4TV = player4Chips;
                player4Nm = player4Name;
                break;
            case 1:
                player1TV = player2Chips;
                player1Nm = player2Name;
                player2TV = player3Chips;
                player2Nm = player3Name;
                player3TV = player4Chips;
                player3Nm = player4Name;
                player4TV = player1Chips;
                player4Nm = player1Name;
                break;
            case 2:
                player1TV = player3Chips;
                player1Nm = player3Name;
                player2TV = player4Chips;
                player2Nm = player4Name;
                player3TV = player1Chips;
                player3Nm = player1Name;
                player4TV = player2Chips;
                player4Nm = player2Name;
                break;
            case 3:
                player1TV = player4Chips;
                player1Nm = player4Name;
                player2TV = player1Chips;
                player2Nm = player1Name;
                player3TV = player2Chips;
                player3Nm = player2Name;
                player4TV = player3Chips;
                player4Nm = player3Name;
                break;
        }

        // Changing all the player's names
        int playerCount = this.playerNum;
        player1Nm.setText(this.allPlayerNames[(playerCount) % 4]);
        player2Nm.setText(this.allPlayerNames[(++playerCount) % 4]);
        player3Nm.setText(this.allPlayerNames[(++playerCount) % 4]);
        player4Nm.setText(this.allPlayerNames[(++playerCount) % 4]);

        // Changes all the chip count to how much each player has
        playerCount = this.playerNum;
        player1TV.setText("" + state.getChips((playerCount) % 4));
        player2TV.setText("" + state.getChips((++playerCount) % 4));
        player3TV.setText("" + state.getChips((++playerCount) % 4));
        player4TV.setText("" + state.getChips((++playerCount) % 4));

        playerCount = this.playerNum;
        ArrayList<GameAction> lastActions = state.getLastActions();
        updateAction(player1Action, lastActions.get(playerCount));
        updateAction(player2Action, lastActions.get((++playerCount) % 4));
        updateAction(player3Action, lastActions.get((++playerCount) % 4));
        updateAction(player4Action, lastActions.get((++playerCount) % 4));


        // Sets the current total pot amount
        jackpot.setText("" + state.getBetController().getTotalAmount());

        // Updates the Community Card fields to all existing community cards
        setCommCards(state.getCommunityCards());

        player1.setImageResource(R.drawable.player);
        player2.setImageResource(R.drawable.player);
        player3.setImageResource(R.drawable.player);
        player4.setImageResource(R.drawable.player);
        chipStack.setImageResource(R.drawable.chip_stack);
        bettingStack.setImageResource(R.drawable.chip_stack);

        // Updates the player's hole cards
        playerCount = playerNum;
        ArrayList<Hand> hands = state.getHands();
        setCard(hands.get(playerCount).getHole1(), playerHole1);
        setCard(hands.get(playerCount).getHole2(), playerHole2);
        playerCount = (playerCount + 1) % 4;
        setCard(hands.get(playerCount).getHole1(), player2Card1);
        setCard(hands.get(playerCount).getHole2(), player2Card2);
        playerCount = (playerCount + 1) % 4;
        setCard(hands.get(playerCount).getHole1(), player3Card1);
        setCard(hands.get(playerCount).getHole2(), player3Card2);
        playerCount = (playerCount + 1) % 4;
        setCard(hands.get(playerCount).getHole1(), player4Card1);
        setCard(hands.get(playerCount).getHole2(), player4Card2);

        turnTracker.setText("Turn " + (state.getTurnTracker().getActivePlayerID() + 1));
        callButton.setText("Call(" + state.getBetController().getCallAmount(playerNum) + ")");

        chipBetSeekbar.setMax(state.getChips(playerNum) - state.getBetController().getCallAmount(playerNum));

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
     * External Citation
     * Date: March 31, 2019
     * <p>
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

    //
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
            BlankCard card2 = (BlankCard) card1;
            cardImage.setImageResource(R.drawable.card_b);
        } else {
            return;
        }


    }

    private void updateAction(TextView tv, GameAction action) {
        if (action == null) {
            tv.setText("");
        }
        int nextMaxBet = state.getBetController().getMaxBet();
        if (action instanceof PokerAllIn) {
            tv.setText("All In");
        }
        else if (action instanceof PokerCall) {
            if (nextMaxBet == 0) {
                tv.setText("Check");
            } else {
                tv.setText("Call");
            }
        }
        else if (action instanceof PokerCheck) {
            tv.setText("Check");
        }
        else if (action instanceof PokerFold) {
            tv.setText("Fold");
        }
        else if (action instanceof PokerRaiseBet) {
            int amount = ((PokerRaiseBet) action).getRaiseAmount();
            tv.setText("Raised by " + amount);
        }
        lastMaxBet = nextMaxBet;
    }

    /**
     * External Citation
     * March 30, 2019
     * Problem: Unable to convert a type Editable to a type int
     * Resource: https://www.quora.com/How-would-I-get-an-int-value-from-an-
     *           edit-text-view-in-Android-studio
     * Solution: Used the method to convert to a String first then to a int
     *           suggested from this forum post
     */
    /**
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v == null) {
            return; //this should never happen lol
        }

            if (v == foldButton) {
                game.sendAction(new PokerFold(this));
            } else if (v.equals(callButton)) {
                game.sendAction(new PokerCall(this));
            } else if (v.equals(checkButton)) {
                game.sendAction(new PokerCheck(this));
            } else if (v.equals(betButton)) {
                //this is where we need to put restriction!!!
                /*So basically we need to prevent users/players from inputing anynumber they want
                 * we do this by checking to see if there bet exceeds the biggest possible integer.
                 * */
                int playerID = state.getTurnTracker().getActivePlayerID();
                int allPlayerMoney = state.getBetController().getPlayerChips(playerID);

                int bet;
                try{
                    bet = Integer.parseInt(chipBetText.getText().toString());
                }
                catch(NumberFormatException i){

                    Log.i("bet variable", "Bet variable was not in proper format.");
                    return;
                }

                /**
                 External Citation

                 */

                if (bet > allPlayerMoney) {
                    MessageBox.popUpMessage("Entry to big!", this.myActivity);
                    Log.i("PlaceBets error", "User has attempted to place a bet too big");
                }
                else if(bet < 0){
                    MessageBox.popUpMessage("Cant bet a negative amount! Try again. ", this.myActivity);
                }



                game.sendAction(new PokerRaiseBet(this, bet));
            } else if (v.equals(showHideCardsButton)) {

                if (showHideCardsButton.getText().equals("SHOW CARDS")) {
                    showHideCardsButton.setText("HIDE CARDS");
                } else {
                    showHideCardsButton.setText("SHOW CARDS");
                }
                game.sendAction(new PokerShowHideCards(this));
            } else if (v.equals(sitOutButton)) {
                game.sendAction(new PokerSitOut(this));
            }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (state == null) {
            return;
        }
        //this.state.getBetController().raiseBet(playerNum, progress);
        int betting =
                progress + state.getBetController().getCallAmount(playerNum);
        chipBetText.setText(""+ betting);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
