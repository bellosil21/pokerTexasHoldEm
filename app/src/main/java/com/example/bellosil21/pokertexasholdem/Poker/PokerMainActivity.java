package com.example.bellosil21.pokertexasholdem.Poker;

import com.example.bellosil21.pokertexasholdem.Game.GameMainActivity;
import com.example.bellosil21.pokertexasholdem.Game.GamePlayer;
import com.example.bellosil21.pokertexasholdem.Game.LocalGame;
import com.example.bellosil21.pokertexasholdem.Game.config.GameConfig;
import com.example.bellosil21.pokertexasholdem.Game.config.GamePlayerType;
import com.example.bellosil21.pokertexasholdem.Poker.GameState.PokerLocalGame;
import com.example.bellosil21.pokertexasholdem.Poker.Player.PokerDumbComputerPlayer;
import com.example.bellosil21.pokertexasholdem.Poker.Player.PokerHumanPlayer;
import com.example.bellosil21.pokertexasholdem.Poker.Player.PokerSmartComputerPlayer;

import java.util.ArrayList;

public class PokerMainActivity extends GameMainActivity {

    public static final int PORT_NUMBER = 4763;

    /**this should be a poker game for 4 players default is 1 human player 3 computer players*/

    @Override
    public GameConfig createDefaultConfig() {
        //define player types
        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();

        playerTypes.add(new GamePlayerType("Human Player") {
            @Override
            public GamePlayer createPlayer(String name) {
                return new PokerHumanPlayer(name);
            }
        });

        playerTypes.add(new GamePlayerType("Computer Player (Dumb)") {
            @Override
            public GamePlayer createPlayer(String name) {
                return new PokerDumbComputerPlayer(name);
            }
        });

        playerTypes.add(new GamePlayerType("Computer Player (Smart)") {
            @Override
            public GamePlayer createPlayer(String name) {
                return new PokerSmartComputerPlayer(name);
            }
        });

        //create a game configuration 'class' for our game.
        GameConfig defaultConfig = new GameConfig(playerTypes, 2, 4,
                "Texas Holdem", PORT_NUMBER);

        // add the default players.
        defaultConfig.addPlayer("Human", 0); // this index represents the index in

        // the playerTypes array list.
        defaultConfig.addPlayer("Computer 1", 1);
        defaultConfig.addPlayer("Computer 2", 1);
        defaultConfig.addPlayer("Computer 3", 1);

        // set the initial information for the remote player.
        defaultConfig.setRemoteData("Poker Guest", "",
                0);
        return defaultConfig;
    }

    /**
     External Citation
        Date:       29 March 2019
        Problem:    Needed to setup the configuration for our game.
        Resource:
            https://github.com/srvegdahl
        Solution:   Used the configuration examples from these repositories.
     */

    public LocalGame createLocalGame(int numPlayers) {
        return new PokerLocalGame(numPlayers);
    }
}
