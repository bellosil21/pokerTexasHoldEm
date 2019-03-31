package com.example.bellosil21.pokertexasholdem.Poker.Player;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.bellosil21.pokertexasholdem.Game.GameHumanPlayer;
import com.example.bellosil21.pokertexasholdem.Game.GameMainActivity;
import com.example.bellosil21.pokertexasholdem.Game.infoMsg.GameInfo;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerCall;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerCheck;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerFold;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerRaiseBet;
import com.example.bellosil21.pokertexasholdem.Poker.GameState.PokerGameState;
import com.example.bellosil21.pokertexasholdem.Poker.Hand.Card;
import com.example.bellosil21.pokertexasholdem.Poker.Hand.Hand;
import com.example.bellosil21.pokertexasholdem.R;

import java.util.ArrayList;

public class PokerHumanPlayer extends GameHumanPlayer
        implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {



    // Instance variables for player's chips TextViews
    private TextView player1Chips;
    private TextView player2Chips;
    private TextView player3Chips;
    private TextView player4Chips;

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

    private GameMainActivity myActivity;


    /**
     * constructor
     *
     * @param name the name of the player
     */
    public PokerHumanPlayer(String name) {
        super(name);
    }

    @Override
    public View getTopView() {
        return null;
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

        // Setting all editable views for betting and setting a listener for
        // the SeekBar
        this.chipBetSeekbar = (SeekBar) activity.findViewById(R.id.bettingSearch);
        chipBetSeekbar.setOnSeekBarChangeListener(this);
        this.chipBetText = (EditText) activity.findViewById(R.id.betInsertText);

        // Setting references to the variables needed for the buttons in the
        // GUI
        this.foldButton =
                (Button) activity.findViewById(R.id.fold_sitoutButton);
        this.betButton = (Button) activity.findViewById(R.id.betButton);
        this.checkButton = (Button) activity.findViewById(R.id.checkButton);
        this.showHideCardsButton =
                (Button) activity.findViewById(R.id.showHideCardsButton);
        this.callButton =
                (Button) activity.findViewById(R.id.callButton);

        // Setting listeners to all the buttons
        this.showHideCardsButton.setOnClickListener(this);
        this.foldButton.setOnClickListener(this);
        this.betButton.setOnClickListener(this);
        this.checkButton.setOnClickListener(this);
        this.callButton.setOnClickListener(this);

        // Setting references to the ImageViews for the Cards;
        this.firstFlop = activity.findViewById(R.id.flop3);
        this.secondFlop = activity.findViewById(R.id.flop2);
        this.thirdFlop = activity.findViewById(R.id.flop1);
        this.turnCard = activity.findViewById(R.id.turn);
        this.riverCard = activity.findViewById(R.id.river);
    }

    @Override
    public void receiveInfo(GameInfo info) {
        if (info instanceof PokerGameState){
            PokerGameState state = (PokerGameState) info;
            TextView player1TV = null;
            TextView player2TV = null;
            TextView player3TV = null;
            TextView player4TV = null;

            // Sets the TextViews to change as their are the player
            switch (this.playerNum){
                case 0:
                    player1TV = player1Chips;
                    player2TV = player2Chips;
                    player3TV = player3Chips;
                    player4TV = player4Chips;
                    break;
                case 1:
                    player1TV = player2Chips;
                    player2TV = player3Chips;
                    player3TV = player4Chips;
                    player4TV = player1Chips;
                    break;
                case 2:
                    player1TV = player3Chips;
                    player2TV = player4Chips;
                    player3TV = player1Chips;
                    player4TV = player2Chips;
                    break;
                case 3:
                    player1TV = player4Chips;
                    player2TV = player1Chips;
                    player3TV = player2Chips;
                    player4TV = player3Chips;
                    break;
            }

            // Changes all the chip count to how much each player has
            int playerCount = this.playerNum;
            player1TV.setText(""+state.getChips((playerCount)%4));
            player2TV.setText(""+state.getChips((playerCount++)%4));
            player3TV.setText(""+state.getChips((playerCount++)%4));
            player4TV.setText(""+state.getChips((playerCount++)%4));

            // Sets the current total pot amount
            jackpot.setText(""+ state.getBetController().getTotalAmount());

            // Updates the Community Card fields to all existing community cards
            setCommCards(state.getCommunityCards());

            // Updates the player's hole cards
            ArrayList<Hand> hands = state.getHands();
            setCard((Card)hands.get(this.playerNum).getHole1(), playerHole1);
            setCard((Card)hands.get(this.playerNum).getHole2(), playerHole2);

            // Updates the Player's Turn announcer
            turnTracker.setText("Turn" + state.getTurnTracker());

        }
    }

    /**
     * Sets the image of the Card to the corresponding Community card field
     *
     * @param commCards - ArrayList of all the community cards
     */
    private void setCommCards(ArrayList<Card> commCards) {
        int cardCount = 0;
        ImageView cardImage = null;
        for (Card card: commCards) {
            switch (cardCount){
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

        }

    }

    //
    private void setCard(Card card, ImageView cardImage) {
        // Checks if the card or Image view is set
        assert (card != null);
        assert (cardImage != null);

        // Checks which suit the card has
        if (card.getSuit() == Card.Suit.DIAMONDS){
            switch (card.getRank()){
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
        }
        else if (card.getSuit() == Card.Suit.SPADES){
            switch (card.getRank()){
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
        }
        else if (card.getSuit() == Card.Suit.HEART){
            switch (card.getRank()){
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
        }
        else if (card.getSuit() == Card.Suit.CLUBS){
            switch (card.getRank()){
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
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v == foldButton){
            game.sendAction(new PokerFold(this));
        }
        else if (v == callButton){
            game.sendAction(new PokerCall(this));
        }
        else if (v == checkButton){
            game.sendAction(new PokerCheck(this));
        }
        else if (v == betButton){
            int bet = Integer.parseInt(chipBetText.getText().toString());
            game.sendAction(new PokerRaiseBet(this, bet));
        }
        else if (v == showHideCardsButton){
            game.sendAction(new PokerFold(this));
        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {
        chipBetText.setText(""+progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
