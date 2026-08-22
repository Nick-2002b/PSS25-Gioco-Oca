package it.unibo.giocooca.model;

public interface Player {
    int getPosition();

    void setPosition(int position);

    boolean isInPrison();

    void setInPrison(boolean imprisoned);

    String getNickName();

    Piece getPiece();

}
