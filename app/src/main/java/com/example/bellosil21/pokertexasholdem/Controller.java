package com.example.bellosil21.pokertexasholdem;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class Controller implements OnClickListener, SeekBar.OnSeekBarChangeListener {

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
    private Button callCheckButton;
    private Button hideCardsButton;
    private Button showCardsButton;

    public Controller(TextView player1Chips, TextView player2Chips,
                      TextView player3Chips, TextView player4Chips,
                      TextView turnTracker, TextView pot,
                      EditText chipBetText, SeekBar chipBetSeekbar,
                      Button foldButton, Button callCheckButton,
                      Button betButton, Button hideCardsButton,
                      Button showCardsButton){

        // Setting all TextViews to local variables
        this.player1Chips = player1Chips;
        this.player2Chips = player2Chips;
        this.player3Chips = player3Chips;
        this.player4Chips = player4Chips;
        this.turnTracker = turnTracker;
        this.jackpot = pot;
        this.chipBetText = chipBetText;

        this.chipBetSeekbar = chipBetSeekbar;
        chipBetSeekbar.setOnSeekBarChangeListener(this);
        this.chipBetText = chipBetText;

        this.foldButton = foldButton;
        this.foldButton.setOnClickListener(this);
        this.betButton = betButton;
        this.betButton.setOnClickListener(this);
        this.callCheckButton = callCheckButton;
        this.callCheckButton.setOnClickListener(this);
        this.hideCardsButton = hideCardsButton;
        this.hideCardsButton.setOnClickListener(this);
        this.showCardsButton = showCardsButton;
        this.foldButton.setOnClickListener(this);


        player1Chips.setOnClickListener(this);


    }
    @Override
    public void onClick(View v) {
        if (v == foldButton){

        }
        else if (v == callCheckButton){

        }
        else if (v == betButton){

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
