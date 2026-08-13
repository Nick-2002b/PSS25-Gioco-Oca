package it.unibo.giocooca.model;

public interface Cell {
    String getType();
    
    int getPosition();

    void applyEffect(Player player);
}
