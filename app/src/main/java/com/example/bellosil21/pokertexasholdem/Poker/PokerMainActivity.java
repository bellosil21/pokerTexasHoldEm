package com.example.bellosil21.pokertexasholdem.Poker;

import com.example.bellosil21.pokertexasholdem.Game.GameMainActivity;
import com.example.bellosil21.pokertexasholdem.Game.GamePlayer;
import com.example.bellosil21.pokertexasholdem.Game.LocalGame;
import com.example.bellosil21.pokertexasholdem.Game.config.GameConfig;
import com.example.bellosil21.pokertexasholdem.Game.config.GamePlayerType;
import com.example.bellosil21.pokertexasholdem.Poker.GameState.PokerLocalGame;
import com.example.bellosil21.pokertexasholdem.Poker.Player.PokerAllInComputer;
import com.example.bellosil21.pokertexasholdem.Poker.Player.PokerDumbComputerPlayer;
import com.example.bellosil21.pokertexasholdem.Poker.Player.PokerHumanPlayer;
import com.example.bellosil21.pokertexasholdem.Poker.Player.PokerSmartComputerPlayer;

import java.util.ArrayList;

/**
 * Upon beta release, our game has met all necessary requirements for this assignment. Currently,
 * our game supports all the rules of play that we agreed to implement which includes the
 * actions, and hand ranking procedure. Likewise, the user interface functionality is working
 * masterfully in that all the functionality of all buttons has been implemented and the GUI
 * updates accordingly for each player respectively. The "smart" and "dumb" computer AI has been
 * carefully crafted in order to visually and practically notice the change in play style.
 * Moreover, our game now supports network play across Android tablets of the same model.
 * Additionally, our game is also able to run with two, three, and four players locally and
 * remotely. We have added small details to the GUI for style and neatness, and general user
 * friendly environment that includes a text showing the round standings and a text showing the
 * big and small blinds. Not to mention, we were also able to display the "help", "settings", and
 * "exit" buttons.
 *
 * The "exit" button gives the user a choice of whether to exit the game or stay in the game. The
 * "settings" button currently only supports the user's choice whether to play in Spanish or
 * English and the given state of the game will remain the same. The "help" button however, has
 * not yet been implemented.
 *
 * We have also added a new button that displays the various types of hand ranks for the user's
 * reference in learning which hands are better than others. As of the beta release, this
 * addition is functional.
 *
 * We are currently still having issues with the following bugs. On game
 * start, there is a chance that the GUI and game will not load properly.
 * This chance is less when the user waits longer in the config screen before
 * starting.
 * The spanish implementation is not finished yet as in the actions are translated yet when
 * playing the game.

 *
 * @author Gabe Marcial
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Patrick Bellosillo
 */
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

        playerTypes.add(new GamePlayerType("Computer Player (Allin)") {
            @Override
            public GamePlayer createPlayer(String name) {
                return new PokerAllInComputer(name);
            }
        });

        // create a game configuration 'class' for our game.
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
