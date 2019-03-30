package com.example.bellosil21.pokertexasholdem.Poker.Player;

import com.example.bellosil21.pokertexasholdem.Game.Game;
import com.example.bellosil21.pokertexasholdem.Game.GameComputerPlayer;
import com.example.bellosil21.pokertexasholdem.Game.GameMainActivity;
import com.example.bellosil21.pokertexasholdem.Game.GamePlayer;
import com.example.bellosil21.pokertexasholdem.Game.infoMsg.GameInfo;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerCall;
import com.example.bellosil21.pokertexasholdem.Poker.GameActions.PokerShowHideCards;

public class PokerDumbComputerPlayer extends GameComputerPlayer {

    protected Game game;
    protected GamePlayer player;
    protected int playerNum;
    protected String name;

    private GameMainActivity myActivity;


    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */

    public PokerDumbComputerPlayer(String name) {
        super(name);


    }

    @Override
    protected void receiveInfo(GameInfo info) {

    }

    protected void makeMove(){
        if(Math.random() >= 0.5){
            game.sendAction(new PokerShowHideCards(this));
        }
        if(game.)
        game.sendAction(new PokerCall(this));
    }
}
