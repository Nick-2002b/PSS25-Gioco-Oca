package it.unibo.giocooca.model;

public interface Player {
    void setPosition(int position);

    int getPosition();

    boolean isInPrison();

    void setInPrison(boolean imprisoned);
    
    String getNickName();

}
