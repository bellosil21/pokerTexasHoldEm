package com.example.bellosil21.pokertexasholdem.Poker.Player;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.bellosil21.pokertexasholdem.Game.GameHumanPlayer;
import com.example.bellosil21.pokertexasholdem.Game.GameMainActivity;
import com.example.bellosil21.pokertexasholdem.Game.GamePlayer;
import com.example.bellosil21.pokertexasholdem.Game.infoMsg.GameInfo;
import com.example.bellosil21.pokertexasholdem.Poker.GameState.PokerGameState;
import com.example.bellosil21.pokertexasholdem.R;

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
    private Button callCheckButton;
    private Button hideCardsButton;
    private Button showCardsButton;

    /**
     * constructor
     *
     * @param name the name of the player
     */
    public PokerHumanPlayer(String name) {
        super(name);
    }

    @Override
    public void gameSetAsGui(GameMainActivity activity) {



    }

    @Override
    public void setAsGui(GameMainActivity activity) {
        // Setting all TextViews to local variables
        this.player1Chips = player1Chips;
        this.player2Chips = player2Chips;
        this.player3Chips = player3Chips;
        this.player4Chips = player4Chips;
        this.turnTracker = turnTracker;
        this.jackpot = jackpot;
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
    public void sendInfo(GameInfo info) {

    }

    @Override
    public void receiveInfo(GameInfo info) {
        if (info instanceof PokerGameState){
            TextView player1TV, player2TV, player3TV, player4TV;
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


        }
    }

    @Override
    public View getTopView() {
        return null;
    }

    @Override
    public void start() {

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
