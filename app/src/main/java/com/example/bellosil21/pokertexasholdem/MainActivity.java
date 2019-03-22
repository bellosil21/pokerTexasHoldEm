package com.example.bellosil21.pokertexasholdem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.bellosil21.pokertexasholdem.GameState.*;

public class MainActivity extends AppCompatActivity {

    private EditText gameState;

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
        setContentView(R.layout.activity_main);

    }

}
