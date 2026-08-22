package it.unibo.giocooca.model;

/**
 * Contratto che rappresenta il Giocatore
 */
public interface Player {
    int getPosition();

    void setPosition(int position);

    boolean isInPrison();

    void setInPrison(boolean imprisoned);

    String getNickName();

    Piece getPiece();

}
