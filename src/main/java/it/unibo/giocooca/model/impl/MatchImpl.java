package it.unibo.giocooca.model.impl;

import it.unibo.giocooca.model.Match;

import it.unibo.giocooca.model.Dice;

import it.unibo.giocooca.model.Player;

import it.unibo.giocooca.model.Board;

import java.util.List;

public class MatchImpl implements Match{

    private final List<Player> players;
    private final Board board;
    private final Dice dice;
    private int currentPlayerIndex;
    private boolean gameOver;
    private Player winner;

    /**
     * Costruttore
     * @param players lista dei giocatori
     * @param board tabellone da gioco
     * @param dice dado
     */
    public MatchImpl (List<Player> players, Board board, Dice dice){
        if(players == null || players.isEmpty()){
            throw new IllegalArgumentException("Almeno un giocatore");
        }
        this.players = List.copyOf(players);
        this.dice = dice;
        this.board = board;
        this.currentPlayerIndex = 0;
        this.gameOver = false;
        this.winner = null;
    }
    @Override
    public int rollDice(){
        return this.dice.roll();
    }

    @Override
    public void moveCurrentPlayer(int steps){
        if(this.gameOver==true){
            return;
        }
        Player current = getCurrentPlayer();
        int newPosition = current.getPosition() + steps;
        int endPosition = this.board.getSize();
        if(newPosition >= endPosition){
            newPosition = endPosition;
            this.gameOver = true;
            this.winner = current;
        }
        current.setPosition(newPosition);
    }
    @Override
    public void applyCurrentCellEffect(){
        //TO DO
    }
    @Override
    public void nextTurn(){
        if(!this.gameOver){
            this.currentPlayerIndex = (this.currentPlayerIndex + 1) % this.players.size();
        }
    }
    @Override
    public boolean isGameOver(){
        return gameOver;
    }
    @Override
    public Player getCurrentPlayer(){
        return this.players.get(this.currentPlayerIndex);
    }
    @Override
    public Player getWinner(){
        return this.winner;
    }
    @Override
    public List<Player> getPlayers(){
        return this.players;

    }
    @Override
    public Board getBoard(){
        return this.board;
    }
}
