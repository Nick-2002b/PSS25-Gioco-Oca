package it.unibo.giocooca.model.impl;

import it.unibo.giocooca.model.Piece;
import it.unibo.giocooca.model.Player;

public class PlayerImpl implements Player {
    private final String nickname;
    private final Piece piece;
    private int position;
    private boolean inPrison;

    public PlayerImpl(String nickname, Piece piece) {
        if (nickname == null || nickname.isBlank()) {
            throw new IllegalArgumentException("Nickname cannot be null or blank");
        }
        if (piece == null) {
            throw new IllegalArgumentException("Piece cannot be null");
        }
        this.nickname = nickname;
        this.piece = piece;
        this.position = 0;
        this.inPrison = false;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int getPosition() {
        return this.position;
    }

    @Override
    public boolean isInPrison() {
        return this.inPrison;
    }

    @Override
    public void setInPrison(boolean imprisoned) {
        this.inPrison = imprisoned;
    }

    @Override
    public String getNickName() {
        return this.nickname;
    }

    @Override
    public Piece getPiece() {
        return this.piece;
    }
}
