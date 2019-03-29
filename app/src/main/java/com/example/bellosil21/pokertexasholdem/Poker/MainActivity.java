package com.example.bellosil21.pokertexasholdem.Poker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.bellosil21.pokertexasholdem.Controller;
import com.example.bellosil21.pokertexasholdem.R;

public class MainActivity extends AppCompatActivity {

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

    // Player's Seekbar for making bets
    private SeekBar chipBetSeekbar;

    // Player action buttons
    private Button foldButton;
    private Button betButton;
    private Button callCheckButton;
    private Button hideCardsButton;
    private Button showCardsButton;

    Controller gameControl;

    // amount of money each player starts with
    public static final int STARTING_CHIPS = 2000;
    // small blind entry fee
    public static final int STARTING_SMALL = 25;
    // big blind entry fee
    public static final int STARTING_BIG = 50;
    // number of players
    public static final int NUM_PLAYERS = 4;

    // max number of players is 4
    public int[] playerID = {0, 1, 2, 3};

    /**
     * Get the button and set its OnClickListener
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize all player chip amounts
        player1Chips = findViewById(R.id.currChips);
        player2Chips = findViewById(R.id.player2Chips);
        player3Chips = findViewById(R.id.player3Chips);
        player4Chips = findViewById(R.id.player4Chips);

        // Initialize the views for bet making
        chipBetSeekbar = findViewById(R.id.bettingSearch);
        chipBetText = findViewById(R.id.betInsertText);

        // Initialize pot amount
        jackpot = findViewById(R.id.totalPot);

        // Initialize turn informer text
        turnTracker = findViewById(R.id.turnText);

        // Initialize the buttons for player's actions
        foldButton = findViewById(R.id.foldButton);
        callCheckButton = findViewById(R.id.callButton);
        betButton = findViewById(R.id.betButton);
        showCardsButton = findViewById(R.id.showButton);
        hideCardsButton = findViewById(R.id.hideButton);

        gameControl = new Controller(player1Chips, player2Chips, player3Chips
                , player4Chips, turnTracker, jackpot, chipBetText,
                chipBetSeekbar, foldButton, callCheckButton, betButton,
                hideCardsButton, showCardsButton);

        setContentView(R.layout.activity_main);

    }

}
