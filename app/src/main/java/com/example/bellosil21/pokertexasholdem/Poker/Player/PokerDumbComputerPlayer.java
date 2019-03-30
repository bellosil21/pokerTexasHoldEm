package com.example.bellosil21.pokertexasholdem.Poker.Player;

import com.example.bellosil21.pokertexasholdem.Game.Game;
import com.example.bellosil21.pokertexasholdem.Game.GameComputerPlayer;
import com.example.bellosil21.pokertexasholdem.Game.GameMainActivity;
import com.example.bellosil21.pokertexasholdem.Game.GamePlayer;
import com.example.bellosil21.pokertexasholdem.Game.infoMsg.GameInfo;
import com.example.bellosil21.pokertexasholdem.Game.infoMsg.GameOverInfo;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerCall;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerShowHideCards;

import java.util.logging.Handler;

public class PokerDumbComputerPlayer extends GameComputerPlayer {

    protected Game game;
    protected GamePlayer player;
    protected int playerNum;
    protected String name;
    private Handler myHandler;
    private GameMainActivity myActivity;
    private boolean gameOver;


    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */

    public PokerDumbComputerPlayer(String name) {
        super(name);
    }

    /**
     * Connects player to the GUI.
     * @param a
     */
    public final void gameSetAsGui(GameMainActivity a){
        myActivity = a;
        setAsGui(a);
    }

    /**
     * Assigns the player to one of the players included in the GUI
     * @param activity
     */
    public void setAsGui(GameMainActivity activity){

    }

    /**
     * Initializes the player after others have been connected to the GUI
     */
    protected void initAfterReady(){

    }

    /**
     * Updates the state of the player
     * @param info
     */
    public final void sendInfo(GameInfo info){
        while(myHandler == null){
            Thread.yield();
            //myHandler.post(new MyRunnable(info));
        }
    }

    /**
     * Updates the state of the other players and the game
     * @param info
     */
    @Override
    protected void receiveInfo(GameInfo info) {

    }

    /**
     * Starts the player's actions
     */
    public final void start(){
        synchronized (this){
            /**if(){
                return;
            }**/
        }
    }
    public void run(){
        if (gameOver == true) {
            return;
        }

    }
    /**
     * method randomly shows the cards of the dumb AI player and constantly checks or calls the
     * previous game action.
     */
    protected void makeMove(){
        if(Math.random() >= 0.5){
            game.sendAction(new PokerShowHideCards(this));
        }
        //if(game.)
        game.sendAction(new PokerCall(this));
    }


}
