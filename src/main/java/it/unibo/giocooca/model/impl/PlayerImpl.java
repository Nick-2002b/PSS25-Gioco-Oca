package it.unibo.giocooca.model.impl;

import it.unibo.giocooca.model.Piece;
import it.unibo.giocooca.model.Player;

/**
 * Implementazione di un giocatore reale del gioco.
 */
public final class PlayerImpl implements Player {
    private final String nickname;
    private final Piece piece;
    private int position;
    private boolean inPrison;

    /**
     * Crea un giocatore.
     *
     * @param nickname il nome del giocatore
     * @param piece    la pedina del giocatore
     */
    public PlayerImpl(final String nickname, final Piece piece) {
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
    public int getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(final int position) {
        this.position = position;
    }

    @Override
    public boolean isInPrison() {
        return this.inPrison;
    }

    @Override
    public void setInPrison(final boolean imprisoned) {
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
