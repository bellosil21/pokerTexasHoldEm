package com.example.bellosil21.pokertexasholdem.Poker;

import com.example.bellosil21.pokertexasholdem.Game.GameMainActivity;
import com.example.bellosil21.pokertexasholdem.Game.GamePlayer;
import com.example.bellosil21.pokertexasholdem.Game.LocalGame;
import com.example.bellosil21.pokertexasholdem.Game.config.GameConfig;
import com.example.bellosil21.pokertexasholdem.Game.config.GamePlayerType;
import com.example.bellosil21.pokertexasholdem.Poker.GameState.PokerLocalGame;
import com.example.bellosil21.pokertexasholdem.Poker.Player.PokerDumbComputerPlayer;
import com.example.bellosil21.pokertexasholdem.Poker.Player.PokerHumanPlayer;

import java.util.ArrayList;

public class PokerMainActivity extends GameMainActivity {

    public static final int PORT_NUMBER = 4763;

    /**this should be a poker game for 4 players default is 1 human player 3 computer players*/

    @Override
    public GameConfig createDefaultConfig() {
        //define player types?
        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();

        playerTypes.add(new GamePlayerType("Human player") {
            @Override
            public GamePlayer createPlayer(String name) {
                return new PokerHumanPlayer(name);
            }
        });

        playerTypes.add(new GamePlayerType("Computer player (dumb like you)") {
            @Override
            public GamePlayer createPlayer(String name) {
                return new PokerDumbComputerPlayer(name);
            }
        });

        //create a game configuration 'class' for texas boldem.
        GameConfig defaultConfig = new GameConfig(playerTypes, 2, 4,
                "Texas Holdem", PORT_NUMBER);

        //Add the default players
        defaultConfig.addPlayer("Human", 0); // this index represents the index in
        //the playerTypes array list.
        defaultConfig.addPlayer("Dumb Computer", 1);
        defaultConfig.addPlayer("Dumb Computer2", 1);
        defaultConfig.addPlayer("Dumb Computer3", 1);

        //set the initial information for the remote player?
        defaultConfig.setRemoteData("Poker Guest", "Some IP code",
                0); //not really sure what this does.
        return defaultConfig;
    }

    @Override
    public LocalGame createLocalGame() {
        return new PokerLocalGame();
    }
}
