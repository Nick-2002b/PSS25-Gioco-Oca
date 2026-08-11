package it.unibo.giocooca.model.impl;

import it.unibo.giocooca.model.Player;
import it.unibo.giocooca.model.Piece;

public class PlayerMock implements Player{
    private final String nickname;
    private final Piece piece;
    private int position;
    private boolean inPrision;

    /**
     * Costruttore player
     * @param nickname nome del giocatore
     * @param piece pedina del giocatore
     */
    public PlayerMock(String nickname, Piece piece){
        this.nickname = nickname;
        this.piece = piece;
        this.position = 0;
        this.inPrision = false;
    }

    /**
     * Costruttore senza pedina
     * @param nickname nome del giocatore
     */
    public PlayerMock(String nickname){
        this(nickname, null);
    }
    @Override
    public String getNickName(){
        return this.nickname;
    }
    /*@Override
    public Piece getPiece(){
        return this.piece;
    }
    */
    @Override
    public int getPosition(){
        return this.position;
    }
    @Override
    public boolean isInPrison(){
        return this.inPrision;
    }
    @Override
    public void setPosition(int position){
        this.position = position;
    }
    @Override
    public void setInPrison(boolean imprisioned){
        this.inPrision = imprisioned;
    }
}

