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
 * 14 April 2019
 * Beta Release Notes
 *
 * =======================
 * Required Functionality:
 *
 * - All game rules according to our requirements have been implemented.
 * - A dumb and start AI are implemented. The dumb AI only calls, while the
 * smart AI calls, raises, and folds depending on how many chips they have
 * left and how many chips are in the pot.
 * - Network play is implemented.
 * - The game can play with a minimum of 2 players and a maximum of 4.
 * - We are unaware of any bugs in this version.
 *
 * ==============================================
 * Functionality Different from Our Requirements:
 *
 * - Call/Check and Fold/SitOut buttons have been separated. We thought it
 * would be more helpful to a user to separate these.
 * - Exiting prompts an option dialog to confirm the user to quit. If they
 * quit, it exits the app completely since we do not have a main menu at this
 * time.
 * - To simplify the GUI, the show and hide cards buttons were merged to the
 * same button as a toggle. This button shows and hides both cards instead of
 * being able to show and hide a card individually.
 * - We did not implement a timer that asks if the player is still there
 * after some time and pauses the game. We did not see this as a priority in
 * our work, and we did not have adequate time to implement it since the hand
 * ranking and betting algorithms took longer than expected to implement.
 * - Our "How to Play" section is not as descriptive as in the requirements.
 * It is now simpler.
 *
 * =========================
 * Additional Functionality:
 *
 * - The GUI displays the current standings after a round (the player's net
 * chip gains).
 * - The GUI displays the player's last actions during a betting phase.
 * - The hands of non-folded players are revealed at the end of a round.
 * - The GUI shows the current blind amounts.
 * - Notifications are displayed for the player's current turn and invalid
 * actions, and when the blinds are increased and a player is out of funds.
 * - The screen flashes yellow when it is the player's current turn.
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
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
