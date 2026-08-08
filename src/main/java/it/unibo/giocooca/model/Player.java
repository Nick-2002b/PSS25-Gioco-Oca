package it.unibo.giocooca.model;

public interface Player {
    void setPosition();
    int getPosition();
    boolean isInPrison();
    void setInPrison();
    String getNickName();

}
